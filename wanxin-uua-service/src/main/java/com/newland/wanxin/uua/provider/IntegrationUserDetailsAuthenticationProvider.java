package com.newland.wanxin.uua.provider;

import com.newland.wanxin.uua.service.UserDetailsAuthenticationService;
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
 * 统一用户认证处理
 * Author: leell
 * Date: 2022/10/17 20:47:51
 */
@Component
public class IntegrationUserDetailsAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
    @Autowired
    private UserDetailsAuthenticationService userDetailsAuthenticationService;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authenticationToken) throws AuthenticationException {
        Object details = authenticationToken.getDetails();
        String domain;
        if (details instanceof Map) {//password模式认证
            Map<String, String> webAuthenticationDetails = (Map) details;
            domain = webAuthenticationDetails.get("domain");
        } else {
            throw new InternalAuthenticationServiceException("WebAuthenticationDetails type is not support");
        }
        if (StringUtils.isEmpty(authenticationToken)) {
            throw new InternalAuthenticationServiceException("authenticationType is blank");
        }

        UserDetails userDetails = userDetailsAuthenticationService.authentication(domain,authenticationToken);
        if (userDetails == null) {
            throw new InternalAuthenticationServiceException("用户认证信息为空");
        }
        return userDetails;
    }
}
