package com.oket.ccic.service.util;

import com.oket.ccic.entity.User;
import com.oket.ccic.service.MailService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @program: Layer
 * @description: 发送邮件线程
 * @author: dzp
 * @create: 2021-08-26 14:43
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SendMailRunnable implements Runnable {
    /**
     * 邮件service
     */
    private MailService mailService;
    /**
     * 用户信息
     */
    private User user;
    /**
     * 标志位
     */
    private Integer flag;

    @Override
    public void run() {
        mailService.sendMessage(user, flag);
    }
}
