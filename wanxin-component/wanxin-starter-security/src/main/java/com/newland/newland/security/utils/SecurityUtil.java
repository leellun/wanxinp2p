package com.newland.newland.security.utils;

import com.newland.wanxin.api.account.model.LoginUser;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    /**
     * 获取当前登录用户
     */
    public static LoginUser getUser() {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getDetails();
        return loginUser;
    }

}
