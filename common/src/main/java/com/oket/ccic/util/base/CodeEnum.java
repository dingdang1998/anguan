package com.oket.ccic.util.base;

/**
 * 响应码枚举
 *
 * @author dzp
 */
public enum CodeEnum {
    C_SUCCESS(20000, "成功"),
    C_FAILURE(50000, "失败"),
    KICK_OUT_TIPS(10001, "账号已经在其他地方登录，请重新登录！"),
    LOGIN_OVER_DUE(10002, "登陆已过期，请重新登录"),
    LOGIN_OUT_SUCCESS(10003, "退出登录");

    private final Integer code;
    private final String msg;

    CodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}