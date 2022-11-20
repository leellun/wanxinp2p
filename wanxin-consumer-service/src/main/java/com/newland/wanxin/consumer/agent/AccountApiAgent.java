package com.newland.wanxin.consumer.agent;

import com.newland.wanxin.api.account.model.AccountDTO;
import com.newland.wanxin.api.account.model.AccountRegisterDTO;
import com.newland.wanxin.domain.RestResponse;
import org.dromara.hmily.annotation.Hmily;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value="account-service")
public interface AccountApiAgent {

    @Hmily
    @PostMapping(value = "/account/l/accounts")
    RestResponse<AccountDTO> register(@RequestBody AccountRegisterDTO accountRegisterDTO);
}
