package com.newland.wanxin.gateway.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 *
 * @author liulun
 * @date 2022/12/1 12:49
 */
public class GatewayServerAuthenticationConverter implements ServerAuthenticationConverter {
    @Override
    public Mono<Authentication> convert(ServerWebExchange serverWebExchange) {
//        OAuth2Authentication oAuth2Authentication=new OAuth2Authentication();
        return null;
    }
}
