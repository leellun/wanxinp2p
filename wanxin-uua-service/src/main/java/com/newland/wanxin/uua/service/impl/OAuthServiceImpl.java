package com.newland.wanxin.uua.service.impl;


import com.newland.wanxin.uua.domain.OAuthClientDetails;
import com.newland.wanxin.uua.domain.OAuthClientDetailsDto;
import com.newland.wanxin.uua.repository.OAuthRepository;
import com.newland.wanxin.uua.service.OAuthService;
import com.newland.wanxin.uua.utils.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 授权
 */
@Service("oauthService")
public class OAuthServiceImpl implements OAuthService {

    private static final Logger LOG = LoggerFactory.getLogger(OAuthServiceImpl.class);

    @Autowired
    private OAuthRepository oauthRepository;

    @Override
    @Transactional(readOnly = true)
    public OAuthClientDetails loadOauthClientDetails(String clientId) {
        return oauthRepository.findOauthClientDetails(clientId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OAuthClientDetailsDto> loadAllOauthClientDetailsDtos() {
        List<OAuthClientDetails> clientDetailses = oauthRepository.findAllOauthClientDetails();
        return OAuthClientDetailsDto.toDtos(clientDetailses);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void archiveOauthClientDetails(String clientId) {
        oauthRepository.updateOauthClientDetailsArchive(clientId, true);
        LOG.debug("{}|Update OauthClientDetails(clientId: {}) archive = true", WebUtils.getIp(), clientId);
    }

    @Override
    @Transactional(readOnly = true)
    public OAuthClientDetailsDto loadOauthClientDetailsDto(String clientId) {
        final OAuthClientDetails oauthClientDetails = oauthRepository.findOauthClientDetails(clientId);
        return oauthClientDetails != null ? new OAuthClientDetailsDto(oauthClientDetails) : null;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void registerClientDetails(OAuthClientDetailsDto formDto) {
        OAuthClientDetails clientDetails = formDto.createDomain();
        oauthRepository.saveOauthClientDetails(clientDetails);
        LOG.debug("{}|Save OauthClientDetails: {}", WebUtils.getIp(), clientDetails);
    }
}