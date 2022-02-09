package com.oket.ccic.controller;

import com.oket.ccic.entity.Role;
import com.oket.ccic.entity.User;
import com.oket.ccic.service.UserService;
import com.oket.ccic.util.base.Message;
import com.oket.ccic.util.base.RespPageBean;
import com.oket.ccic.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @program: Layer
 * @description: 用户控制层
 * @author: dzp
 * @create: 2021-08-11 14:14
 **/
@RestController
@RequestMapping(value = "/user")
@Api(tags = "用户控制层")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @ApiOperation("用户注册")
    public Message register(HttpServletRequest request, @ApiParam(value = "用户实体类Vo", name = "userVo") @RequestBody UserVo userVo) {
        if (userVo.getVerifyCode().equals(request.getSession().getAttribute("verifyCode"))) {
            userService.register(userVo);
            return Message.ok();
        } else {
            return Message.error("验证码输入错误，请检查");
        }
    }

    @PostMapping("/add")
    @ApiOperation("添加用户")
    @RequiresPermissions("user:add")
    public Message add(@ApiParam(value = "用户Vo", name = "userVo") @RequestBody UserVo userVo) {
        userService.add(userVo);
        return Message.ok();
    }

    @GetMapping("/get")
    @ApiOperation("分页查询用户")
    @RequiresPermissions("user:search")
    public Message getPage(@ApiParam(value = "第几页", name = "pageNum") @RequestParam(value = "pageNum", required = false) Integer pageNum,
                           @ApiParam(value = "一页几条", name = "pageSize") @RequestParam(value = "pageSize", required = false) Integer pageSize,
                           @ApiParam(value = "公司表主键id", name = "companyId") @RequestParam(value = "companyId", required = false) Integer companyId,
                           @ApiParam(value = "用户名", name = "userName") @RequestParam(value = "userName", required = false) String userName) {
        RespPageBean page = userService.getPage(pageNum, pageSize, companyId, userName);
        return Message.ok(page);
    }

    @PutMapping("/update")
    @ApiOperation("编辑用户信息")
    @RequiresPermissions("user:update")
    public Message update(@ApiParam(value = "用户Vo", name = "userVo") @RequestBody UserVo userVo) {
        userService.update(userVo);
        return Message.ok();
    }

    @PutMapping("/update/{userId}/{status}")
    @ApiOperation("修改用户状态")
    @RequiresPermissions("user:updatestatus")
    public Message updateStatus(@ApiParam(value = "用户表主键id", name = "userId") @PathVariable Integer userId,
                                @ApiParam(value = "状态", name = "status") @PathVariable Byte status) {
        int result = userService.updateUserStatus(userId, status);
        if (result == 1) {
            return Message.ok();
        }
        return Message.error();
    }

    @PostMapping("/update2")
    @ApiOperation("忘记密码")
    public Message forgetPassword(HttpServletRequest request, @ApiParam(value = "用户实体类Vo", name = "userVo") @RequestBody UserVo userVo) {
        if (userVo.getVerifyCode().equals(request.getSession().getAttribute("verifyCode"))) {
            return updatePassword(userVo);
        } else {
            return Message.error("验证码输入错误，请检查");
        }
    }

    @PostMapping("/update3")
    @ApiOperation("修改密码")
    public Message updatePassword(@ApiParam(value = "用户实体类Vo", name = "userVo") @RequestBody UserVo userVo) {
        int result = userService.updatePassword(userVo);
        if (result == 1) {
            return Message.ok("修改密码成功");
        }
        return Message.error("修改密码失败");
    }

    @GetMapping("/get2")
    @ApiOperation("检查用户名是否存在")
    public Message checkUserName(@ApiParam(value = "用户名", name = "userName") @RequestParam(value = "userName") String userName) {
        int result = userService.checkUserName(userName);
        if (result > 0) {
            return Message.error("用户名已存在");
        }
        return Message.ok("用户名可用");
    }

    @GetMapping("/get3")
    @ApiOperation("获取当前用户能创建的角色")
    public Message getRoles() {
        List<Role> roles = userService.getRoles();
        return Message.ok(roles);
    }

    @GetMapping("/get4")
    @ApiOperation("检查邮箱是否存在")
    public Message checkMailAddress(@ApiParam(value = "邮箱地址", name = "mailAddress") @RequestParam(value = "mailAddress") String mailAddress) {
        int result = userService.checkMailAddress(mailAddress);
        if (result > 0) {
            return Message.error("邮箱已存在");
        }
        return Message.ok("邮箱可用");
    }

    @GetMapping("/get5")
    @ApiOperation("获取已创建账号数量和可建账号数量")
    public Message getCreateUserNums() {
        Map<String, Integer> map = userService.getCreateUserNums();
        return Message.ok(map);
    }

    @PostMapping("/get6")
    @ApiOperation("校验密码")
    public Message checkPassword(@ApiParam(value = "用户实体类Vo", name = "userVo") @RequestBody UserVo userVo) {
        if (userService.checkPassword(userVo)) {
            return Message.ok();
        }
        return Message.error();
    }

    @GetMapping("/get7")
    @ApiOperation("校验组织下是否已经存在管理员")
    public Message checkAdmin(@ApiParam(value = "公司主键id", name = "companyId") @RequestParam Integer companyId) {
        User user = userService.getAdminByCompanyId(companyId);
        if (user != null) {
            return Message.error("该公司已存在管理员");
        }
        return Message.ok();
    }

    @GetMapping("/get8")
    @ApiOperation("检查该公司能否再创建用户")
    public Message canCreateUser(@ApiParam(value = "公司主键id", name = "companyId") @RequestParam Integer companyId) {
        boolean result = userService.checkCanCreateUser(companyId);
        if (result) {
            return Message.ok();
        }
        return Message.error();
    }
}
