package com.newland.wanxin.uua.service;


import com.newland.wanxin.uua.domain.OAuthClientDetails;
import com.newland.wanxin.uua.domain.OAuthClientDetailsDto;

import java.util.List;

public interface OAuthService {

    /**
     * 加载客服端clientdetails
     * @param clientId
     * @return
     */
    OAuthClientDetails loadOauthClientDetails(String clientId);

    /**
     * 加载所有客服端
     * @return
     */
    List<OAuthClientDetailsDto> loadAllOauthClientDetailsDtos();

    void archiveOauthClientDetails(String clientId);

    OAuthClientDetailsDto loadOauthClientDetailsDto(String clientId);

    void registerClientDetails(OAuthClientDetailsDto formDto);
    
}