package com.newland.wanxin.uua.repository;


import com.newland.wanxin.uua.domain.OAuthClientDetails;

import java.util.List;

public interface OAuthRepository {

    OAuthClientDetails findOauthClientDetails(String clientId);

    List<OAuthClientDetails> findAllOauthClientDetails();

    void updateOauthClientDetailsArchive(String clientId, boolean archive);

    void saveOauthClientDetails(OAuthClientDetails clientDetails);

}