package com.newland.wanxin.account.service;

import com.newland.wanxin.account.entity.Account;
import com.baomidou.mybatisplus.extension.service.IService;
import com.newland.wanxin.api.account.model.AccountDTO;
import com.newland.wanxin.api.account.model.AccountLoginDTO;
import com.newland.wanxin.api.account.model.AccountRegisterDTO;
import com.newland.wanxin.domain.RestResponse;

/**
 * <p>
 * 账号信息 服务类
 * </p>
 *
 * @author leellun
 * @since 2022-11-19
 */
public interface AccountService extends IService<Account> {
    void getSMSCode(String mobile);

    Integer checkMobile(String mobile, String key, String code);

    AccountDTO register(AccountRegisterDTO accountRegisterDTO);

    AccountDTO login(AccountLoginDTO accountLoginDTO);
}
