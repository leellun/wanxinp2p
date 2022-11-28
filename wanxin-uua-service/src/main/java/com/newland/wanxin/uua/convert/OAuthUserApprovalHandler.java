package com.newland.wanxin.uua.convert;


import com.newland.wanxin.uua.domain.OAuthClientDetails;
import com.newland.wanxin.uua.service.OAuthService;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.approval.TokenStoreUserApprovalHandler;

/**
 * 通过查询现有令牌来记住批准决定的用户 批准处理程序。
 */
public class OAuthUserApprovalHandler extends TokenStoreUserApprovalHandler {

    private OAuthService oauthService;

    public OAuthUserApprovalHandler() {
    }

    public boolean isApproved(AuthorizationRequest authorizationRequest, Authentication userAuthentication) {
        if (super.isApproved(authorizationRequest, userAuthentication)) {
            return true;
        }
        if (!userAuthentication.isAuthenticated()) {
            return false;
        }

        OAuthClientDetails clientDetails = oauthService.loadOauthClientDetails(authorizationRequest.getClientId());
        return clientDetails != null && clientDetails.trusted();
    }

    public void setOauthService(OAuthService oauthService) {
        this.oauthService = oauthService;
    }
}
