package com.newland.wanxin.depository.service.impl;

import com.newland.wanxin.depository.entity.BankUser;
import com.newland.wanxin.depository.mapper.BankUserMapper;
import com.newland.wanxin.depository.service.BankUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 银行用户信息表 服务实现类
 * </p>
 *
 * @author leellun
 * @since 2022-11-20
 */
@Service
public class BankUserServiceImpl extends ServiceImpl<BankUserMapper, BankUser> implements BankUserService {

}
