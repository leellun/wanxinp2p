package com.newland.newland.security.config;

import com.newland.newland.security.AuthenticationFilter;
import com.newland.newland.security.handler.LoginUrlAuthenticationEntryPoint;
import com.newland.newland.security.handler.WanxinAccessDeniedHandler;
import com.newland.newland.security.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 资源服务配置
 */
@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(SecurityProperties.class)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private SecurityProperties securityProperties;

    @Bean
    public AuthenticationFilter authenticationFilter() {
        return new AuthenticationFilter();
    }

    /**
     * 请求配置
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .csrf().disable();
        if (securityProperties.getPermitUrls() != null && securityProperties.getPermitUrls().length > 0) {
            http.authorizeRequests().antMatchers(securityProperties.getPermitUrls()).permitAll();
        } else {
            http.authorizeRequests().anyRequest().authenticated();
        }
        http.exceptionHandling()
                .accessDeniedHandler(new WanxinAccessDeniedHandler())
                .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint())
                .and().headers().cacheControl().disable();
    }


}
