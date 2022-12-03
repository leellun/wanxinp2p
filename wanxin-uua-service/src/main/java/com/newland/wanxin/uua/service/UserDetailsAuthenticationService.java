package com.newland.wanxin.uua.service;

import com.newland.wanxin.api.account.model.AccountDTO;
import com.newland.wanxin.api.account.model.AccountLoginDTO;
import com.newland.wanxin.domain.RestResponse;
import com.newland.wanxin.uua.agent.AccountApiAgent;
import com.newland.wanxin.uua.model.UnifiedUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 用户认证信息查询service
 * Author: leell
 * Date: 2022/12/3 16:23:51
 */
@Service
public class UserDetailsAuthenticationService {
    @Autowired
    private AccountApiAgent accountApiAgent;

    public UserDetails authentication(String domain, UsernamePasswordAuthenticationToken token) {
        String usernmae = token.getName();
        if (StringUtils.isEmpty(usernmae)) {
            throw new BadCredentialsException("账户不能为空");
        }
        if (token.getCredentials() == null) {
            throw new BadCredentialsException("密码不能为空");
        }
        String presentedPassword = token.getCredentials().toString();

        //远程调用统一账户服务，进行账户密码校验
        AccountLoginDTO accountLoginDTO = new AccountLoginDTO();
        accountLoginDTO.setUsername(usernmae);
        accountLoginDTO.setDomain(domain);
        accountLoginDTO.setPassword(presentedPassword);
        accountLoginDTO.setMobile(usernmae);
        RestResponse<AccountDTO> response = accountApiAgent.login(accountLoginDTO);

        if (response.getCode() != 0) {
            throw new BadCredentialsException("登录失败");
        }
        AccountDTO accountDTO = response.getResult();
        UnifiedUserDetails userDetails = new UnifiedUserDetails(accountDTO.getUsername(), presentedPassword, AuthorityUtils.createAuthorityList("ro","rw"));
        userDetails.setMobile(accountDTO.getMobile());
        userDetails.setStatus(accountDTO.getStatus());
        userDetails.setDomain(accountDTO.getDomain());
        return userDetails;
    }
}
