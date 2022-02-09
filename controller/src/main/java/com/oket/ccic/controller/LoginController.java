package com.oket.ccic.controller;


import com.oket.ccic.bo.UserInfo;
import com.oket.ccic.entity.User;
import com.oket.ccic.service.LoginService;
import com.oket.ccic.util.base.Message;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: oketmicroservice
 * @description: 登录控制层
 * @author: dzp
 * @create: 2021-05-26 10:05
 **/
@RestController
@RequestMapping(value = "/login")
@Api(tags = "登录控制层")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/auth")
    @ApiOperation(value = "登录")
    public Message authLogin(@ApiParam(value = "用户Vo", name = "userVo") @RequestBody User user) {
        loginService.authLogin(user);
        return Message.ok();
    }

    /**
     * 获取当前登录用户的信息
     */
    @GetMapping("/get")
    @ApiOperation(value = "查询当前登录用户的信息")
    public Message getInfo() {
        UserInfo userInfo = loginService.getUserInfo();
        return Message.ok(userInfo);
    }
}
