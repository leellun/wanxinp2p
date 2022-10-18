package com.newland.wanxin.uua.provider;

import com.newland.wanxin.uua.domain.IntegrationWebAuthenticationDetails;
import com.newland.wanxin.uua.convert.IntegrationUserDetailsAuthenticationConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * 统一用户认证处理，集成了网页(简化模式、授权码模式用户登录)认证  与  password模式认证
 * Author: leell
 * Date: 2022/10/17 20:47:51
 */
@Component
public class IntegrationUserDetailsAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
    @Autowired
    private IntegrationUserDetailsAuthenticationConvert integrationUserDetailsAuthenticationHandler;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        UserDetails userDetails = authenticationUser(authentication);
        if (userDetails == null) {
            throw new InternalAuthenticationServiceException("用户认证信息为空");
        }
        return userDetails;
    }

    /**
     * 认证用户
     *
     * @param authenticationToken
     * @return
     */
    private UserDetails authenticationUser(UsernamePasswordAuthenticationToken authenticationToken) {
        Object details = authenticationToken.getDetails();
        String domain = null;
        String authenticationType = null;
        if (details instanceof IntegrationWebAuthenticationDetails) {//web认证模式
            IntegrationWebAuthenticationDetails webAuthenticationDetails = (IntegrationWebAuthenticationDetails) details;
            domain = webAuthenticationDetails.getDomain();
            authenticationType = webAuthenticationDetails.getAuthenticationType();
        } else if (details instanceof Map) {//password模式认证
            Map<String, String> webAuthenticationDetails = (Map) details;
            domain = webAuthenticationDetails.get("domain");
            authenticationType = webAuthenticationDetails.get("authenticationType");
        } else {
            throw new InternalAuthenticationServiceException("WebAuthenticationDetails type is not support");
        }
        if (StringUtils.isEmpty(domain)) {
            throw new InternalAuthenticationServiceException("domain is blank");
        }
        if (StringUtils.isEmpty(authenticationToken)) {
            throw new InternalAuthenticationServiceException("authenticationType is blank");
        }
        return integrationUserDetailsAuthenticationHandler.authentication(domain, authenticationType, authenticationToken);
    }
}
