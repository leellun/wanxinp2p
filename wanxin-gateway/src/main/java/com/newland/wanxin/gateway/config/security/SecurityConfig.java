package com.newland.wanxin.gateway.config.security;

import com.newland.wanxin.gateway.config.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.server.resource.web.server.ServerBearerTokenAuthenticationConverter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.util.matcher.NegatedServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;

/**
 * security支持
 */
@EnableWebFluxSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain webFluxSecurityFilterChain(ServerHttpSecurity http) {
        return http
                .authorizeExchange()
                .pathMatchers("/uaa/druid/**", "/consumer/l/**").denyAll()
                .pathMatchers("/uaa/**").permitAll()
                .pathMatchers("/consumer/my/**").access(new GatewayAuthorizationManager("read", "ROLE_CONSUMER"))
                .pathMatchers("/consumer/m/**").access(new GatewayAuthorizationManager("read", "ROLE_ADMIN"))
                .pathMatchers("/consumer/**").permitAll()
                .anyExchange().authenticated()
                .and().addFilterAt(webFilter(),SecurityWebFiltersOrder.AUTHENTICATION)
                .exceptionHandling().accessDeniedHandler(new RestAccessDeniedHandler()).authenticationEntryPoint(new RestOAuth2AuthExceptionEntryPoint())
                .and().csrf().disable().build();
    }
    public AuthenticationWebFilter webFilter() {
        AuthenticationWebFilter authenticationWebFilter = new AuthenticationWebFilter(new JwtAuthenticationManager());
        authenticationWebFilter.setServerAuthenticationConverter(new ServerBearerTokenAuthenticationConverter());
//        authenticationWebFilter.setRequiresAuthenticationMatcher(new NegatedServerWebExchangeMatcher(ServerWebExchangeMatchers.pathMatchers(excludedAuthPages)));
//        authenticationWebFilter.setSecurityContextRepository(new NoOpServerSecurityContextAutoRepository(tokenProvider));
        return authenticationWebFilter;
    }
}
