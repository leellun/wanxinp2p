package com.newland.wanxin.gateway.config.security;

import com.newland.wanxin.gateway.config.GatewayAuthorizationManager;
import com.newland.wanxin.gateway.config.RestAccessDeniedHandler;
import com.newland.wanxin.gateway.config.RestOAuth2AuthExceptionEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * security支持
 */
@EnableWebFluxSecurity
@Configuration
public class SecurityConfig {
    @Bean
    public SecurityWebFilterChain webFluxSecurityFilterChain(ServerHttpSecurity http) {
        return http.authorizeExchange()
                .pathMatchers("/uaa/druid/**", "/consumer/l/**").denyAll()
                .pathMatchers("/uaa/**").permitAll()
                .pathMatchers("/consumer/my/**").access(new GatewayAuthorizationManager("read", "ROLE_CONSUMER"))
                .pathMatchers("/consumer/m/**").access(new GatewayAuthorizationManager("read", "ROLE_ADMIN"))
                .pathMatchers("/consumer/**").permitAll()
                .anyExchange().authenticated()
                .and().exceptionHandling().accessDeniedHandler(new RestAccessDeniedHandler()).authenticationEntryPoint(new RestOAuth2AuthExceptionEntryPoint())
                .and().csrf().disable().build();
    }

}
