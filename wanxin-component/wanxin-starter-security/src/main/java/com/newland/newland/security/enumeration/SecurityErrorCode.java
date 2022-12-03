package com.newland.newland.security.enumeration;

import com.newland.wanxin.enumeration.ErrorCode;

public enum SecurityErrorCode implements ErrorCode {
    NOT_LOGIN(801,"未登录"),
    ACCESS_DENIED(802,"没有权限");

    private int code;
    private String desc;

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    private SecurityErrorCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
