package com.newland.wanxin.uua.domain;


import com.newland.wanxin.utils.DateUtil;

import java.io.Serializable;
import java.time.LocalDateTime;


public class OAuthClientDetails implements Serializable {


    private static final long serialVersionUID = -6947822646185526939L;

    private LocalDateTime createTime = DateUtil.now();
    private boolean archived = false;

    private String clientId;
    private String resourceIds;

    private String clientSecret;
    /**
     * Available values: read,write
     */
    private String scope;

    /**
     * grant types include
     * "authorization_code", "password", "assertion", and "refresh_token".
     * Default value is "authorization_code,refresh_token".
     */
    private String authorizedGrantTypes = "authorization_code,refresh_token";

    /**
     * The re-direct URI(s) established during registration (optional, comma separated).
     */
    private String webServerRedirectUri;

    /**
     * Authorities that are granted to the client (comma-separated). Distinct from the authorities
     * granted to the user on behalf of whom the client is acting.
     * <p/>
     * For example: ROLE_USER
     */
    private String authorities;

    /**
     * The access token validity period in seconds (optional).
     * If unspecified a global default will be applied by the token services.
     */
    private Integer accessTokenValidity;

    /**
     * The refresh token validity period in seconds (optional).
     * If unspecified a global default will  be applied by the token services.
     */
    private Integer refreshTokenValidity;

    // optional
    private String additionalInformation;

    /**
     * The client is trusted or not. If it is trust, will skip approve step
     * default false.
     */
    private boolean trusted = false;

    /**
     * Value is 'true' or 'false',  default 'false'
     */
    private String autoApprove;

    public OAuthClientDetails() {
    }

    public String autoApprove() {
        return autoApprove;
    }

    public OAuthClientDetails autoApprove(String autoApprove) {
        this.autoApprove = autoApprove;
        return this;
    }

    public boolean trusted() {
        return trusted;
    }

    public LocalDateTime createTime() {
        return createTime;
    }

    public OAuthClientDetails createTime(LocalDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public boolean archived() {
        return archived;
    }

    public String clientId() {
        return clientId;
    }

    public String resourceIds() {
        return resourceIds;
    }

    public String clientSecret() {
        return clientSecret;
    }

    public String scope() {
        return scope;
    }

    public String authorizedGrantTypes() {
        return authorizedGrantTypes;
    }

    public String webServerRedirectUri() {
        return webServerRedirectUri;
    }

    public String authorities() {
        return authorities;
    }

    public Integer accessTokenValidity() {
        return accessTokenValidity;
    }

    public Integer refreshTokenValidity() {
        return refreshTokenValidity;
    }

    public String additionalInformation() {
        return additionalInformation;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("OauthClientDetails");
        sb.append("{createTime=").append(createTime);
        sb.append(", archived=").append(archived);
        sb.append(", clientId='").append(clientId).append('\'');
        sb.append(", resourceIds='").append(resourceIds).append('\'');
        sb.append(", clientSecret='").append(clientSecret).append('\'');
        sb.append(", scope='").append(scope).append('\'');
        sb.append(", authorizedGrantTypes='").append(authorizedGrantTypes).append('\'');
        sb.append(", webServerRedirectUri='").append(webServerRedirectUri).append('\'');
        sb.append(", authorities='").append(authorities).append('\'');
        sb.append(", accessTokenValidity=").append(accessTokenValidity);
        sb.append(", refreshTokenValidity=").append(refreshTokenValidity);
        sb.append(", additionalInformation='").append(additionalInformation).append('\'');
        sb.append(", trusted=").append(trusted);
        sb.append('}');
        return sb.toString();
    }

    public OAuthClientDetails clientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public OAuthClientDetails clientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
        return this;
    }

    public OAuthClientDetails resourceIds(String resourceIds) {
        this.resourceIds = resourceIds;
        return this;
    }

    public OAuthClientDetails authorizedGrantTypes(String authorizedGrantTypes) {
        this.authorizedGrantTypes = authorizedGrantTypes;
        return this;
    }

    public OAuthClientDetails scope(String scope) {
        this.scope = scope;
        return this;
    }

    public OAuthClientDetails webServerRedirectUri(String webServerRedirectUri) {
        this.webServerRedirectUri = webServerRedirectUri;
        return this;
    }

    public OAuthClientDetails authorities(String authorities) {
        this.authorities = authorities;
        return this;
    }

    public OAuthClientDetails accessTokenValidity(Integer accessTokenValidity) {
        this.accessTokenValidity = accessTokenValidity;
        return this;
    }

    public OAuthClientDetails refreshTokenValidity(Integer refreshTokenValidity) {
        this.refreshTokenValidity = refreshTokenValidity;
        return this;
    }

    public OAuthClientDetails trusted(boolean trusted) {
        this.trusted = trusted;
        return this;
    }

    public OAuthClientDetails additionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
        return this;
    }

    public OAuthClientDetails archived(boolean archived) {
        this.archived = archived;
        return this;
    }
}