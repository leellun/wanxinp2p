package com.newland.wanxin.uua.config;

import com.newland.wanxin.uua.convert.ClientDefaultAccessTokenConverter;
import com.newland.wanxin.uua.convert.OAuthUserApprovalHandler;
import com.newland.wanxin.uua.convert.UnifiedUserAuthenticationConverter;
import com.newland.wanxin.uua.exception.RestOAuth2WebResponseExceptionTranslator;
import com.newland.wanxin.uua.service.OAuthService;
import com.newland.wanxin.uua.service.impl.CustomJdbcClientDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * token配置
 * Author: leell
 * Date: 2022/10/16 22:19:35
 */
@Configuration
public class AuthorizationConfig {
    /**
     * 秘钥串
     */
    private static final String SIGNING_KEY = "wanxin123";
    @Autowired
    private TokenStore tokenStore;
    @Autowired
    private JwtAccessTokenConverter accessTokenConverter;
    @Autowired
    @Qualifier("jdbcClientDetailsService")
    private ClientDetailsService clientDetailsService;
    @Autowired
    private OAuthService oauthService;

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(SIGNING_KEY);
        ClientDefaultAccessTokenConverter accessTokenConverter = new ClientDefaultAccessTokenConverter();
        accessTokenConverter.setUserTokenConverter(new UnifiedUserAuthenticationConverter());
        converter.setAccessTokenConverter(accessTokenConverter);
        return converter;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean("jdbcClientDetailsService")
    public ClientDetailsService clientDetailsService(DataSource dataSource) {
        ClientDetailsService clientDetailsService = new CustomJdbcClientDetailsService(dataSource);
        ((CustomJdbcClientDetailsService) clientDetailsService).setPasswordEncoder(passwordEncoder());
        return clientDetailsService;
    }

    @Bean
    public AuthorizationServerTokenServices tokenService() {
        DefaultTokenServices service = new DefaultTokenServices();
        service.setClientDetailsService(clientDetailsService);
        service.setSupportRefreshToken(true);
        service.setTokenStore(tokenStore);

        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), accessTokenConverter));
        service.setTokenEnhancer(tokenEnhancerChain);

        service.setAccessTokenValiditySeconds(7200); // 令牌默认有效期2小时
        service.setRefreshTokenValiditySeconds(259200); // 刷新令牌默认有效期3天
        return service;
    }


    @Bean
    public AuthorizationCodeServices authorizationCodeServices(DataSource dataSource) {
        return new JdbcAuthorizationCodeServices(dataSource);
    }

    @Bean
    public OAuth2RequestFactory oAuth2RequestFactory() {
        return new DefaultOAuth2RequestFactory(clientDetailsService);
    }


    @Bean
    public UserApprovalHandler userApprovalHandler() {
        OAuthUserApprovalHandler userApprovalHandler = new OAuthUserApprovalHandler();
        userApprovalHandler.setOauthService(oauthService);
        userApprovalHandler.setTokenStore(tokenStore);
        userApprovalHandler.setClientDetailsService(this.clientDetailsService);
        userApprovalHandler.setRequestFactory(oAuth2RequestFactory());
        return userApprovalHandler;
    }
    @Bean
    public TokenEnhancer tokenEnhancer() {
        return (accessToken, authentication) -> {
            /*if (accessToken instanceof DefaultOAuth2AccessToken){
                if( authentication.getPrincipal() instanceof UnifiedUserDetails){
                    UnifiedUserDetails unifiedUserDetails =(UnifiedUserDetails) authentication.getPrincipal();
                    DefaultOAuth2AccessToken token= (DefaultOAuth2AccessToken) accessToken;
                    Map<String, Object> additionalInformation = new LinkedHashMap<String, Object>();
                    additionalInformation.put("mobile",unifiedUserDetails.getMobile());
                    additionalInformation.put("tenant_id",unifiedUserDetails.getTenantId());
                    additionalInformation.put("department_id",unifiedUserDetails.getDepartmentId());
                    additionalInformation.put("user_authorities",unifiedUserDetails.getUserAuthorities());
                    token.setAdditionalInformation(additionalInformation);

                }

            }*/
            DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) accessToken;
            Map<String, Object> additionalInformation = new LinkedHashMap<String, Object>();
            additionalInformation.put("code", 0);
            additionalInformation.put("msg", "success");
            token.setAdditionalInformation(additionalInformation);
            return accessToken;
        };
    }
}
