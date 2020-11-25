package com.liuwjg.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@Controller
public class LoginController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SessionRegistry sessionRegistry;

    @RequestMapping("/")
    public String home() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.info("当前登陆用户：" + name);
        return "home.html";
    }

    @RequestMapping("/login")
    public String showLogin() {
        logger.info("1111111111111");
        return "login.html";
    }

    @RequestMapping("/admin")
    @ResponseBody
    @PreAuthorize("hasPermission('/admin','r')")
    public String AdminR() {
        return "如果你看到这句话，说明你访问/admin路径有r权限";
    }

    @RequestMapping("/admin/c")
    @ResponseBody
    @PreAuthorize("hasPermission('/admin','c')")
    public String printAdmin() {
        return "如果你看到这句话，说明你访问/admin路径有c权限";
    }

    @RequestMapping("/user")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_USER')")
    public String printUser() {
        return "如果你看见这句话，说明你有ROLE_USER角色";
    }

    @RequestMapping("login/error")
    public void loginError(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");
        AuthenticationException exception = (AuthenticationException) request.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
        try {
            response.getWriter().write(exception.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/login/invalid")
    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String invalid() {
        return "Session 已过期，请重新登录";
    }

    @GetMapping("/kick")
    @ResponseBody
    public String removeSessionByUserName(String username) {
        int count = 0;
        //获取session中的用户信息
        List<Object> users = sessionRegistry.getAllPrincipals();
        for (Object user : users) {
            if (user instanceof User) {
                String principalName = ((User) user).getUsername();
                if (principalName.equals(username)) {
                    //获取session,参数2  false 表示不包含过期的session
                    List<SessionInformation> sessionsInfo = sessionRegistry.getAllSessions(user, false);
                    if (sessionsInfo != null && sessionsInfo.size() > 0) {
                        for (SessionInformation sessionInformation : sessionsInfo) {
                            sessionInformation.expireNow();
                            count++;
                        }
                    }
                }
            }
        }
        return "操作成功，清理session共" + count + "个";
    }
}
