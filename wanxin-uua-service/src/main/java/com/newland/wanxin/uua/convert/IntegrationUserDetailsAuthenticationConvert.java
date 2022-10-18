package com.newland.wanxin.uua.convert;

import com.newland.wanxin.api.account.model.AccountDTO;
import com.newland.wanxin.api.account.model.AccountLoginDTO;
import com.newland.wanxin.domain.RestResponse;
import com.newland.wanxin.uua.agent.AccountApiAgent;
import com.newland.wanxin.uua.domain.UnifiedUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * web登录认证模式用户认证信息处理器
 * Author: leell
 * Date: 2022/10/17 22:11:47
 */
@Component
public class IntegrationUserDetailsAuthenticationConvert {
    @Autowired
    private AccountApiAgent accountApiAgent;

    public UnifiedUserDetails authentication(String domain, String authenticationType, UsernamePasswordAuthenticationToken token) {
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
        accountLoginDTO.setDomain(domain);
        accountLoginDTO.setUsername(usernmae);
        accountLoginDTO.setPassword(presentedPassword);
        accountLoginDTO.setMobile(usernmae);
        RestResponse<AccountDTO> response = accountApiAgent.login(accountLoginDTO);

        if (response.getCode() != 0) {
            throw new BadCredentialsException("登录失败");
        }
        UnifiedUserDetails unifiedUserDetails = new UnifiedUserDetails(response.getResult().getUsername(), presentedPassword, AuthorityUtils.createAuthorityList());
        return unifiedUserDetails;
    }
}
