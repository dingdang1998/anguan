package com.oket.ccic.service;


import com.oket.ccic.bo.UserInfo;
import com.oket.ccic.entity.User;

/**
 * 登录服务接口
 *
 * @author dzp
 */
public interface LoginService {
    /**
     * 用户登录
     *
     * @param user
     */
    void authLogin(User user);

    /**
     * 获取当前登陆用户的全部信息
     *
     * @return
     */
    UserInfo getUserInfo();
}
