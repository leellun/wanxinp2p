package com.newland.wanxin.account.service.impl;

import com.newland.wanxin.account.entity.Account;
import com.newland.wanxin.account.mapper.AccountMapper;
import com.newland.wanxin.account.service.AccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 账号信息 服务实现类
 * </p>
 *
 * @author leellun
 * @since 2022-11-19
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {

}
