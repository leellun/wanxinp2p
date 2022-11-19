package com.newland.wanxin.utils;

/**
 * 验证码
 * Author: leell
 * Date: 2022/11/19 23:10:49
 */
public class IdentifyCodeUtils {

    public static String getSmsCode() {
        return String.valueOf((int) ((Math.random() + 1) * 100000));
    }

}
