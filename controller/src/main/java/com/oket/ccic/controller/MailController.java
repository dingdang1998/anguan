package com.oket.ccic.controller;

import com.oket.ccic.entity.User;
import com.oket.ccic.service.MailService;
import com.oket.ccic.service.UserService;
import com.oket.ccic.util.base.Message;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: Layer
 * @description: 邮件控制层
 * @author: dzp
 * @create: 2021-08-17 10:32
 **/
@RestController
@RequestMapping(value = "/mail")
@Api(tags = "邮件控制层")
public class MailController {

    @Autowired
    private MailService mailService;
    @Autowired
    private UserService userService;

    @PostMapping("/send")
    @ApiOperation(value = "用户注册邮箱发送验证码")
    public Message registerSendCode(HttpServletRequest request, @ApiParam(value = "用户实体类", name = "user") @RequestBody User user) {
        boolean result = mailService.sendCode(request, user.getMailAddress());
        if (result) {
            return Message.ok();
        }
        return Message.error();
    }

    @PostMapping("/send2")
    @ApiOperation(value = "忘记密码邮箱发送验证码")
    public Message updatePasswordSendCode(HttpServletRequest request, @ApiParam(value = "用户实体类", name = "user") @RequestBody User user) {
        String mailAddress = userService.getMailAddressByUserName(user.getUsername());
        if (mailAddress != null && !"".equals(mailAddress)) {
            boolean result = mailService.sendCode(request, mailAddress);
            if (result) {
                return Message.ok();
            } else {
                return Message.error();
            }
        } else {
            return Message.error("邮箱为空，请联系管理员");
        }
    }
}
