package com.newland.wanxin.uua.controller;


import com.newland.wanxin.uua.utils.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * 统一认证
 */
@Controller
public class UUAController {

    private static final Logger LOG = LoggerFactory.getLogger(UUAController.class);

    @Autowired
    private AuthorizationServerTokenServices tokenService;

    @Autowired
    private AccessTokenConverter accessTokenConverter;

    /**
     * @param model
     * @return
     */
    //@GetMapping(value = {"/login"})
    @RequestMapping(value = {"/login"})
    public String login(Model model) {
        LOG.info("Go to login, IP: {}", WebUtils.getIp());
        return "login";
    }

    /**
     * @return
     */
    @RequestMapping("/confirm_access")
    public String confirmAccess() {
        return "/oauth_approval";
    }

    /**
     * @return
     */
    @RequestMapping("/oauth_error")
    public String oauthError() {
        return "/oauth_error";
    }

    /**
     * @param value
     * @return
     */
    //@RequestMapping(value = "/oauth/check_token", method = RequestMethod.POST)
    //@RequestMapping(value = "/oauth/token", method = RequestMethod.POST)
    @RequestMapping("/oauth/token")
    @ResponseBody
    public Map<String, ?> checkToken(@RequestParam("token") String value) {
        DefaultTokenServices tokenServices = (DefaultTokenServices) tokenService;
        OAuth2AccessToken token = tokenServices.readAccessToken(value);
        if (token == null) {
            throw new InvalidTokenException("Token was not recognised");
        }
        if (token.isExpired()) {
            throw new InvalidTokenException("Token has expired");
        }
        OAuth2Authentication authentication = tokenServices.loadAuthentication(token.getValue());
        Map<String, ?> rst = accessTokenConverter.convertAccessToken(token, authentication);
        return rst;
    }
}
