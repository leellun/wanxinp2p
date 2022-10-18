package com.newland.wanxin.uua.domain;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;

/**
 * web AuthenticationDetails 自定义，针对web网页用户认证，由于网页端用户认证(认证码模式、简化模式)
 * 会走UsernamePasswordAuthenticationFilter，把request中的额外信息增加至WebAuthenticationDetails。
 */
public class IntegrationWebAuthenticationDetails extends WebAuthenticationDetails {
    /**
     * 域名
     */
    private String domain;
    /**
     * key
     */
    private String key;
    /**
     * 认证类型
     */
    private String authenticationType;
    public IntegrationWebAuthenticationDetails(HttpServletRequest request) {
        super(request);
        this.domain=request.getParameter("domain");
        this.key=request.getParameter("key");
        this.authenticationType=request.getParameter("authenticationType");
    }

    public String getAuthenticationType() {
        return authenticationType;
    }

    public String getDomain() {
        return domain;
    }

    public String getKey() {
        return key;
    }
}
