package com.newland.wanxin.account.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.newland.wanxin.account.agent.SmsSendApiAgent;
import com.newland.wanxin.account.common.AccountErrorCode;
import com.newland.wanxin.account.entity.Account;
import com.newland.wanxin.account.mapper.AccountMapper;
import com.newland.wanxin.account.service.AccountService;
import com.newland.wanxin.api.account.model.AccountDTO;
import com.newland.wanxin.api.account.model.AccountLoginDTO;
import com.newland.wanxin.api.account.model.AccountRegisterDTO;
import com.newland.wanxin.constant.WanxinConstant;
import com.newland.wanxin.exception.BusinessException;
import com.newland.wanxin.redis.cache.Cache;
import com.newland.wanxin.utils.IdentifyCodeUtils;
import com.newland.wanxin.utils.PasswordUtil;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hmily.annotation.HmilyTCC;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
@Slf4j
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {
    @Autowired
    private SmsSendApiAgent smsSendApiAgent;
    @Autowired
    private Cache cache;
    @Value("${sms.enable}")
    private Boolean smsEnable;

    @Override
    public void getSMSCode(String mobile) {
        String code = IdentifyCodeUtils.getSmsCode();
        cache.set(mobile, code, WanxinConstant.CODE_EXPIRE_TIME);
    }

    @Override
    public Integer checkMobile(String mobile, String key, String code) {
        String codeStr = cache.get(mobile);
        return code.equals(codeStr) ? 1 : 0;
    }

    @Override
    @HmilyTCC(confirmMethod = "confirmRegister", cancelMethod = "cancelRegister")
    public AccountDTO register(AccountRegisterDTO accountRegisterDTO) {
        Account account = new Account();
        account.setUsername(accountRegisterDTO.getUsername());
        account.setMobile(accountRegisterDTO.getMobile());
        account.setPassword(PasswordUtil.generate(accountRegisterDTO.getPassword()));
        if (smsEnable) {
            account.setPassword(PasswordUtil.generate(accountRegisterDTO.getMobile()));
        }
        account.setDomain("c");
        if (accountRegisterDTO.getMobile().equals("110")) {  //测试用
            throw new RuntimeException("我是故意的");
        }
        save(account);
        return convertAccountEntityToDTO(account);
    }

    public void confirmRegister(AccountRegisterDTO registerDTO) {
        log.info("execute confirmRegister");
    }

    public void cancelRegister(AccountRegisterDTO registerDTO) {
        log.info("execute cancelRegister");
        //删除账号
        remove(Wrappers.<Account>lambdaQuery().eq(Account::getUsername,
                registerDTO.getUsername()));
    }

    @Override
    public AccountDTO login(AccountLoginDTO accountLoginDTO) {
        //1.根据用户名和密码进行一次查询
        //2.先根据用户名进行查询，然后再比对密码
        Account account = null;
        if (accountLoginDTO.getDomain().equalsIgnoreCase("c")) {
            //如果是c端用户，用户名就是手机号
            account = getAccountByMobile(accountLoginDTO.getMobile());
        } else {
            //如果是b端用户，用户名就是账号
            account = getAccountByUsername(accountLoginDTO.getUsername());
        }
        if (account == null) {
            throw new BusinessException(AccountErrorCode.E_130104);
        }

        AccountDTO accountDTO = convertAccountEntityToDTO(account);
        if (smsEnable) { //如果为true,表示采用短信验证码登录，无需比较密码
            return accountDTO;
        }

        if (PasswordUtil.verify(accountLoginDTO.getPassword(), account.getPassword())) {
            return accountDTO;
        }

        throw new BusinessException(AccountErrorCode.E_130105);
    }

    private Account getAccountByMobile(String mobile) {
        return getOne(new QueryWrapper<Account>().lambda().eq(Account::getMobile, mobile));
    }

    private Account getAccountByUsername(String username) {
        return getOne(new QueryWrapper<Account>().lambda().eq(Account::getUsername, username));
    }

    /**
     * entity转为dto
     *
     * @param entity
     * @return
     */
    private AccountDTO convertAccountEntityToDTO(Account entity) {
        if (entity == null) {
            return null;
        }
        AccountDTO dto = new AccountDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }
}
