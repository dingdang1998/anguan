package com.oket.ccic.config.shiro;

import com.oket.ccic.entity.Permission;
import com.oket.ccic.entity.User;
import com.oket.ccic.service.CompanyService;
import com.oket.ccic.service.UserService;
import com.oket.ccic.util.ShiroUtils;
import com.oket.ccic.util.base.Constants;
import com.oket.ccic.vo.UserVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: storetank
 * @description: 自定义Realm
 * @author: dzp
 * @create: 2021-05-25 11:41
 **/
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;
    @Autowired
    private ShiroUtils shiroUtils;
    @Autowired
    private CompanyService companyService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //当前登录用户
        UserVo userVo = shiroUtils.getLoginUserVo();
        //获取当前用户所具有的权限
        List<Permission> permissions = userService.getUserPermissionsByUserId(userVo.getId());
        //为当前用户设置权限
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        List<String> permissionCodes = permissions.stream().map(Permission::getPermissionCode).collect(Collectors.toList());
        authorizationInfo.addStringPermissions(permissionCodes);
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String userName = (String) authenticationToken.getPrincipal();
        UserVo userVo = userService.getUserVoByUsername(userName);
        //没找到帐号
        if (userVo == null) {
            throw new UnknownAccountException();
        }
        //验证账户状态和有效期  admin和普通用户管理员不需要验证
        if (userVo.getRoleId() != Constants.ADMIN_ID && userVo.getRoleId() != Constants.N_ADMIN_ID) {
            //状态
            if (userVo.getStatus() != 1) {
                throw new LockedAccountException("账号被禁用，请联系管理员");
            }
            //有效期
            Date now = new Date();
            if (now.compareTo(userVo.getEndTime()) > 0) {
                throw new LockedAccountException("账号已过期");
            }
            //用户所属组织被禁用，用户无法登录
            Byte status = companyService.getCompanyStatusByCompanyId(userVo.getCompanyId());
            if (status == 0) {
                throw new LockedAccountException("对应组织无效");
            }
            //该账户不是管理员和游客,还需要验证用户所属组织下的管理员是否有效
            if (userVo.getRoleId() != Constants.OUTER_ADMIN_ID && userVo.getRoleId() != Constants.INNER_ADMIN_ID && userVo.getRoleId() != Constants.VISITOR_ID) {
                //查找该账户所属组织下的管理员
                User admin = userService.getAdminByCompanyId(userVo.getCompanyId());
                //判断账号所属组织下的管理员是否有效，如果无效，则本账号也不能登录
                if (admin != null) {
                    if (admin.getStatus() != 1 || now.compareTo(admin.getEndTime()) > 0) {
                        throw new LockedAccountException("对应组织管理员无效");
                    }
                }
            }
        }
        //交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现
        //salt=username
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                userVo.getUsername(),
                userVo.getPassword(),
                ByteSource.Util.bytes(userVo.getUsername()),
                getName()
        );
        //将用户信息放入session中
        //session中不需要保存密码
        userVo.setPassword("");
        SecurityUtils.getSubject().getSession().setAttribute(Constants.SESSION_USERDTO_INFO, userVo);
        return authenticationInfo;
    }
}
