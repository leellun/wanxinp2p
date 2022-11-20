package com.newland.wanxin.depository.service.impl;

import com.newland.wanxin.depository.entity.User;
import com.newland.wanxin.depository.mapper.UserMapper;
import com.newland.wanxin.depository.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 存管用户信息表 服务实现类
 * </p>
 *
 * @author leellun
 * @since 2022-11-20
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
