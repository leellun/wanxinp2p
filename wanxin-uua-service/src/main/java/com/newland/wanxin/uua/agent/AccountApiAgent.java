package com.newland.wanxin.uua.agent;

import com.newland.wanxin.api.account.model.AccountDTO;
import com.newland.wanxin.api.account.model.AccountLoginDTO;
import com.newland.wanxin.domain.RestResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "wanxin-account-service")
public interface AccountApiAgent {

    @PostMapping(value = "/l/accounts/session")
    RestResponse<AccountDTO> login(@RequestBody AccountLoginDTO accountLoginDTO);
}
