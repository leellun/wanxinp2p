package com.newland.wanxin.account.controller;


import com.newland.wanxin.account.service.AccountService;
import com.newland.wanxin.api.account.AccountAPI;
import com.newland.wanxin.api.account.model.AccountDTO;
import com.newland.wanxin.api.account.model.AccountLoginDTO;
import com.newland.wanxin.api.account.model.AccountRegisterDTO;
import com.newland.wanxin.domain.RestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 账号信息 前端控制器
 * </p>
 *
 * @author leellun
 * @since 2022-11-19
 */
@RestController
@Api(value = "统一账户服务的API")
public class AccountController implements AccountAPI {
    @Autowired
    private AccountService accountService;

    @Override
    @ApiOperation("获取手机验证码")
    @ApiImplicitParam(name = "mobile", value = "手机号", dataType = "String")
    @GetMapping("/sms/{mobile}")
    public RestResponse getSMSCode(String mobile) {
        return accountService.getSMSCode(mobile);
    }

    @Override
    public RestResponse<Integer> checkMobile(String mobile, String key, String code) {
        return null;
    }

    @Override
    public RestResponse<AccountDTO> register(AccountRegisterDTO accountRegisterDTO) {
        return null;
    }

    @Override
    public RestResponse<AccountDTO> login(AccountLoginDTO accountLoginDTO) {
        return null;
    }
}

