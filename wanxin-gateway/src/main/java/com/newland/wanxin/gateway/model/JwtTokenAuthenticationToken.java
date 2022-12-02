package com.newland.wanxin.gateway.model;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;

/**
 * Author: leell
 * Date: 2022/12/1 23:52:43
 */
public class JwtTokenAuthenticationToken extends BearerTokenAuthenticationToken {
    private OAuth2AccessToken oAuth2AccessToken;

    /**
     * Create a {@code BearerTokenAuthenticationToken} using the provided parameter(s)
     *
     * @param token - the bearer token
     */
    public JwtTokenAuthenticationToken(String token, OAuth2AccessToken oAuth2AccessToken) {
        super(token);
        this.oAuth2AccessToken = oAuth2AccessToken;
    }

    @Override
    public boolean isAuthenticated() {
        return !oAuth2AccessToken.isExpired();
    }
}
