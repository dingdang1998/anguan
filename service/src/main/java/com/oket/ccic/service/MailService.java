package com.oket.ccic.service;

import com.oket.ccic.entity.User;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: oketmicroservice
 * @description: 邮件服务类
 * @author: dzp
 * @create: 2021-05-26 13:48
 **/
public interface MailService {

    /**
     * 邮箱发送验证码
     *
     * @param request
     * @param to
     * @return
     */
    boolean sendCode(HttpServletRequest request, String to);

    /**
     * 发送邮件
     *
     * @param user
     * @param flag
     * @return
     */
    boolean sendMessage(User user, int flag);
}
