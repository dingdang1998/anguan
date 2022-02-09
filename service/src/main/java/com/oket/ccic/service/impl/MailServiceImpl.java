package com.oket.ccic.service.impl;

import com.oket.ccic.entity.User;
import com.oket.ccic.service.MailService;
import com.oket.ccic.util.MailUtil;
import com.oket.ccic.util.base.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @program: Layer
 * @description:
 * @author: dzp
 * @create: 2021-08-17 10:41
 **/
@Service
public class MailServiceImpl implements MailService {

    private static final Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);

    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String sender;
    @Value("${customLoginUrl}")
    private String customLoginUrl;

    @Override
    public boolean sendCode(HttpServletRequest request, String to) {
        //创建发送内容
        MimeMessage message = mailSender.createMimeMessage();
        //验证码
        String code = Constants.getCode();
        //发送内容
        String registerContent = MailUtil.getSendCodeContent(code);
        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(sender);
            helper.setTo(to);
            helper.setSubject("验证码");
            helper.setText(registerContent, true);
            mailSender.send(message);
            logger.info("向" + to + "发送邮件成功：" + code);
            request.getSession().setAttribute("verifyCode", code);
            logger.info(code + "已经被记录在session中");
            return true;
        } catch (MessagingException e) {
            logger.error("向" + to + "发送" + code + "失败" + e.getMessage(), e);
        }
        return false;
    }

    @Override
    public boolean sendMessage(User user, int flag) {
        MimeMessage message = mailSender.createMimeMessage();
        String content;

        if (flag == MailUtil.ADD_FLAG) {
            content = MailUtil.getMessage1(user, customLoginUrl);
        } else {
            content = MailUtil.getMessage2(user, customLoginUrl);
        }

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(sender);
            helper.setTo(user.getMailAddress());

            if (flag == MailUtil.ADD_FLAG) {
                helper.setSubject("新建用户");
            } else if (flag == MailUtil.UPDATE_FLAG) {
                helper.setSubject("信息修改");
            } else {
                helper.setSubject("账号启用");
            }

            helper.setText(content, true);
            mailSender.send(message);

            if (flag == MailUtil.ADD_FLAG) {
                logger.info("向" + user.getMailAddress() + "发送新建用户邮件成功" + new Date());
            } else if (flag == MailUtil.UPDATE_FLAG) {
                logger.info("向" + user.getMailAddress() + "发送编辑用户邮件成功" + new Date());
            } else {
                logger.info("向" + user.getMailAddress() + "发送启用用户邮件成功" + new Date());
            }
            return true;

        } catch (MessagingException e) {
            if (flag == MailUtil.ADD_FLAG) {
                logger.error("向" + user.getMailAddress() + "发送新建用户邮件失败" + new Date());
            } else if (flag == MailUtil.UPDATE_FLAG) {
                logger.error("向" + user.getMailAddress() + "发送编辑用户邮件失败" + new Date());
            } else {
                logger.error("向" + user.getMailAddress() + "发送启用用户邮件失败" + new Date());
            }
        }
        return false;
    }
}
