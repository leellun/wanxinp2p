package com.newland.wanxin.openfeign.filter;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class JwtFeignInterceptor implements RequestInterceptor {

    private final String key = "json-token";


    @Override
    public void apply(RequestTemplate template) {
        if (!template.headers().containsKey(key)) {
            template.header(key, getHeaderValue(key));
            template.header("Authorization", getHeaderValue("Authorization"));
        }
    }

    private String getHeaderValue(String key) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes.getRequest().getHeader(key);
    }
}
