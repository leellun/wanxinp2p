package com.newland.wanxin.uua.repository;


import com.newland.wanxin.uua.domain.OAuthClientDetails;

import java.util.List;

public interface OAuthRepository {

    OAuthClientDetails findOAuthClientDetails(String clientId);

    List<OAuthClientDetails> findAllOAuthClientDetails();

    void updateOAuthClientDetailsArchive(String clientId, boolean archive);

    void saveOAuthClientDetails(OAuthClientDetails clientDetails);

}