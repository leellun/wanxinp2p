package com.newland.wanxin.uua.provider;

import com.newland.wanxin.uua.model.UnifiedUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 认证转换器
 * Author: leell
 * Date: 2022/12/3 17:50:49
 */
public class ClientUserAuthenticationConverter extends DefaultUserAuthenticationConverter {
    @Override
    public Map<String, ?> convertUserAuthentication(Authentication authentication) {
        Map<String, Object> response = new LinkedHashMap<String, Object>();
        response.put(USERNAME, authentication.getName());
        if (authentication.getPrincipal() instanceof UnifiedUserDetails) {
            UnifiedUserDetails unifiedUserDetails = (UnifiedUserDetails) authentication.getPrincipal();
            response.put("mobile", unifiedUserDetails.getMobile());
            response.put("status", unifiedUserDetails.getStatus());
            response.put("domain", unifiedUserDetails.getDomain());
        }
        if (authentication.getAuthorities() != null && !authentication.getAuthorities().isEmpty()) {
            response.put(AUTHORITIES, AuthorityUtils.authorityListToSet(authentication.getAuthorities()));
        }
        return response;
    }

}
