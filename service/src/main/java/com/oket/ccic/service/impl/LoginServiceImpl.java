package com.oket.ccic.service.impl;


import com.oket.ccic.bo.UserInfo;
import com.oket.ccic.entity.Permission;
import com.oket.ccic.entity.User;
import com.oket.ccic.service.LoginService;
import com.oket.ccic.service.UserService;
import com.oket.ccic.util.ShiroUtils;
import com.oket.ccic.vo.UserVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @program: oketmicroservice
 * @description: 登录服务接口实现类
 * @author: dzp
 * @create: 2021-05-26 10:11
 **/
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserService userService;
    @Autowired
    private ShiroUtils shiroUtils;
    @Value("${visitorCompanyId}")
    private Integer visitorCompanyId;

    @Override
    public void authLogin(User user) {
        //shiro登录认证
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.login(usernamePasswordToken);
        //登录成功后记录登录时间
        UserVo loginUserVo = shiroUtils.getLoginUserVo();
        Date now = new Date();
        userService.updateLastLoginTime(now, loginUserVo.getId());
    }

    @Override
    public UserInfo getUserInfo() {
        //当前登录用户信息
        UserVo userVo = shiroUtils.getLoginUserVo();
        //权限
        Map<String, List<Permission>> map = new HashMap<>();
        List<Permission> permissions = userService.getUserPermissionsByUserId(userVo.getId());
        if (permissions != null && permissions.size() > 0) {
            map = permissions.stream().collect(Collectors.groupingBy(Permission::getMenuCode));
        }
        //封装返回
        return new UserInfo(userVo, map, visitorCompanyId);
    }
}
