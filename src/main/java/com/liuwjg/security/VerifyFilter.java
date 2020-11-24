package com.liuwjg.security;

import org.springframework.security.authentication.DisabledException;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class VerifyFilter extends OncePerRequestFilter {
    private static final PathMatcher pathMatcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (isProtectedUrl(request)) {
            String verifyCode = request.getParameter("verifyCode");
            if (!validateVerify(verifyCode)) {
                //手动设置异常
                request.getSession().setAttribute("SPRING_SECURITY_LAST_EXCEPTION", new DisabledException("验证码错误!!"));
                //转发错误页面
                request.getRequestDispatcher("/login/error").forward(request, response);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    /**
     * 如果是POST请求且请求路径是"/login",则返回true
     *
     * @param request
     * @return
     */
    private boolean isProtectedUrl(HttpServletRequest request) {
        return "POST".equals(request.getMethod()) && pathMatcher.match("/login", request.getServletPath());
    }

    /**
     * 传入一个验证吗
     *
     * @param inputVerify
     * @return
     */
    private boolean validateVerify(String inputVerify) {
        ////获取当前线程绑定的request对象
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //这个validateCode是在servlet中存入session的名字，转小写
        String validateCode = ((String) request.getSession().getAttribute("validateCode")).toLowerCase();
        //不区分大小写
        inputVerify = inputVerify.toLowerCase();
        return validateCode.equals(inputVerify);
    }
}
