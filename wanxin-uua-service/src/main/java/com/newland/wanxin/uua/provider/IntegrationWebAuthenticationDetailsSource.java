package com.newland.wanxin.uua.provider;

import com.newland.wanxin.uua.domain.IntegrationWebAuthenticationDetails;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * web认证信息生成资源
 * Author: leell
 * Date: 2022/10/17 22:33:15
 */
@Component
public class IntegrationWebAuthenticationDetailsSource implements AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> {
    @Override
    public WebAuthenticationDetails buildDetails(HttpServletRequest context) {
        return new IntegrationWebAuthenticationDetails(context);
    }
}
