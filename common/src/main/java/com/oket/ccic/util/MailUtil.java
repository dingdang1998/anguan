package com.oket.ccic.util;

import com.oket.ccic.entity.User;

/**
 * @program: Layer
 * @description: 邮件工具类
 * @author: dzp
 * @create: 2021-08-17 11:04
 **/
public class MailUtil {
    /**
     * 添加用户
     */
    public static final int ADD_FLAG = 1;
    /**
     * 编辑用户
     */
    public static final int UPDATE_FLAG = 2;
    /**
     * 启用用户
     */
    public static final int UPDATE_USER_STATUS_FLAG = 3;

    /**
     * 获取验证码邮件发送内容
     *
     * @param code
     * @return
     */
    public static String getSendCodeContent(String code) {
        return "<div>" +
                "<p>验证码：<br>\n" +
                "欢迎使用【黄岛检验认证有限公司】岸罐系统：验证码：<span style='color: blue;'><b><strong>" + code + "</strong></b></span> <br>" +
                "验证码30分钟内有效，请在<span style=\"border-bottom:1px dashed #ccc;\">30分钟内</span>注册登录。<br>" +
                "</div>";
    }

    /**
     * 发送内容1
     *
     * @param user
     * @param url
     * @return
     */
    public static String getMessage1(User user, String url) {
        return "<div>" +
                "欢迎使用【黄岛检验认证有限公司】岸罐系统：<br>" +
                "用户名：<span style='color: blue;'><b><strong>" + user.getUsername() + "</strong></b></span> <br>" +
                "密码：<span style='color: blue;'><b><strong>" + user.getPassword() + "</strong></b></span> <br>" +
                "有效期至：<span style='color: blue;'><b><strong>" + CustomTimeUtils.formateToDay(user.getEndTime()) + "</strong></b></span><br>" +
                "登录网址：<span style='color: blue;'><b><strong>" + url + "</strong></b></span> <br>" +
                "</div>";
    }

    /**
     * 发送内容2
     *
     * @param user
     * @param url
     * @return
     */
    public static String getMessage2(User user, String url) {
        return "<div>" +
                "【黄岛检验认证有限公司】岸罐系统：<br>" +
                "用户名：<span style='color: blue;'><b><strong>" + user.getUsername() + "</strong></b></span> <br>" +
                "有效期至：<span style='color: blue;'><b><strong>" + CustomTimeUtils.formateToDay(user.getEndTime()) + "</strong></b></span><br>" +
                "登录网址：<span style='color: blue;'><b><strong>" + url + "</strong></b></span> <br>" +
                "</div>";
    }
}
