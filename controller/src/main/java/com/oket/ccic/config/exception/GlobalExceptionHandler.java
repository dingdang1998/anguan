package com.oket.ccic.config.exception;

import com.oket.ccic.util.base.CustomException;
import com.oket.ccic.util.base.Message;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author dzp
 * @description: 异常拦截
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 自定义错误的拦截器
     */
    @ExceptionHandler(CustomException.class)
    public Message commonJsonExceptionHandler(CustomException customException) {
        return (Message) customException.getInfo();
    }

    /**
     * 权限不足报错拦截
     */
    @ExceptionHandler(UnauthorizedException.class)
    public Message unauthorizedExceptionHandler() {
        return Message.error("权限不足");
    }

    /**
     * 未登录报错拦截
     * 在请求需要权限的接口,而连登录都还没登录的时候,会报此错
     */
    @ExceptionHandler(UnauthenticatedException.class)
    public Message unauthenticatedException() {
        return Message.error("登录已过期,请重新登录");
    }

    /**
     * 用户名错误，根据用户名找不到用户
     */
    @ExceptionHandler(UnknownAccountException.class)
    public Message unknownAccountException() {
        return Message.error("用户名或密码错误");
    }

    /**
     * 密码错误
     */
    @ExceptionHandler(IncorrectCredentialsException.class)
    public Message incorrectCredentialsException() {
        return Message.error("用户名或密码错误");
    }

    /**
     * 账号过期或被禁用
     */
    @ExceptionHandler(LockedAccountException.class)
    public Message lockedAccountException(LockedAccountException lockedAccountException) {
        return Message.error(lockedAccountException.getMessage());
    }
}
