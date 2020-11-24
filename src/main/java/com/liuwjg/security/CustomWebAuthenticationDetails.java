package com.liuwjg.security;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;

/**
 * @author liuwjg
 * 自定义获取请求自带的额外信息
 */
public class CustomWebAuthenticationDetails extends WebAuthenticationDetails {
    private final String verifyCode;

    public CustomWebAuthenticationDetails(HttpServletRequest request) {
        super(request);
        verifyCode = request.getParameter("verifyCode");
    }

    public String getVerifyCode() {
        return this.verifyCode;
    }
}
