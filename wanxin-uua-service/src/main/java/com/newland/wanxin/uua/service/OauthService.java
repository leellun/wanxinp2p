package com.newland.wanxin.uua.service;


import com.newland.wanxin.uua.domain.OAuthClientDetails;
import com.newland.wanxin.uua.domain.OAuthClientDetailsDto;

import java.util.List;

public interface OauthService {

    OAuthClientDetails loadOauthClientDetails(String clientId);

    List<OAuthClientDetailsDto> loadAllOauthClientDetailsDtos();

    void archiveOauthClientDetails(String clientId);

    OAuthClientDetailsDto loadOauthClientDetailsDto(String clientId);

    void registerClientDetails(OAuthClientDetailsDto formDto);
    
}