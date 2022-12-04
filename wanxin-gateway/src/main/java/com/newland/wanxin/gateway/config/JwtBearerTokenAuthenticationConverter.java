package com.newland.wanxin.gateway.config;

import com.newland.wanxin.gateway.model.JwtTokenAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.BearerTokenError;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.springframework.security.oauth2.server.resource.BearerTokenErrors.invalidRequest;
import static org.springframework.security.oauth2.server.resource.BearerTokenErrors.invalidToken;

/**
 * token转换器
 * Author: leell
 * Date: 2022/12/1 23:46:26
 */
@Component
public class JwtBearerTokenAuthenticationConverter implements ServerAuthenticationConverter {
    private static final Pattern authorizationPattern = Pattern.compile(
            "^Bearer (?<token>[a-zA-Z0-9-._~+/]+)=*$",
            Pattern.CASE_INSENSITIVE);
    @Autowired
    private TokenStore tokenStore;
    private boolean allowUriQueryParameter = false;

    public Mono<Authentication> convert(ServerWebExchange exchange) {
        return Mono.justOrEmpty(token(exchange.getRequest()))
                .map(token -> {
                    if (token.isEmpty()) {
                        BearerTokenError error = invalidTokenError();
                        throw new OAuth2AuthenticationException(error);
                    }
                    OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(token);
                    Authentication authentication = new JwtTokenAuthenticationToken(token, oAuth2AccessToken);
                    return authentication;
                });
    }

    private String token(ServerHttpRequest request) {
        String authorizationHeaderToken = resolveFromAuthorizationHeader(request.getHeaders());
        String parameterToken = request.getQueryParams().getFirst("access_token");
        if (authorizationHeaderToken != null) {
            if (parameterToken != null) {
                BearerTokenError error = invalidRequest("Found multiple bearer tokens in the request");
                throw new OAuth2AuthenticationException(error);
            }
            return authorizationHeaderToken;
        } else if (parameterToken != null && isParameterTokenSupportedForRequest(request)) {
            return parameterToken;
        }
        return null;
    }

    /**
     * Set if transport of access token using URI query parameter is supported. Defaults to {@code false}.
     * <p>
     * The spec recommends against using this mechanism for sending bearer tokens, and even goes as far as
     * stating that it was only included for completeness.
     *
     * @param allowUriQueryParameter if the URI query parameter is supported
     */
    public void setAllowUriQueryParameter(boolean allowUriQueryParameter) {
        this.allowUriQueryParameter = allowUriQueryParameter;
    }

    private static String resolveFromAuthorizationHeader(HttpHeaders headers) {
        String authorization = headers.getFirst(HttpHeaders.AUTHORIZATION);
        if (StringUtils.startsWithIgnoreCase(authorization, "bearer")) {
            Matcher matcher = authorizationPattern.matcher(authorization);

            if (!matcher.matches()) {
                BearerTokenError error = invalidTokenError();
                throw new OAuth2AuthenticationException(error);
            }

            return matcher.group("token");
        }
        return null;
    }

    private static BearerTokenError invalidTokenError() {
        return invalidToken("Bearer token is malformed");
    }

    private boolean isParameterTokenSupportedForRequest(ServerHttpRequest request) {
        return this.allowUriQueryParameter && HttpMethod.GET.equals(request.getMethod());
    }
}
