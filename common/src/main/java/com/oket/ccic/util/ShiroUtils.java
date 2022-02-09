package com.oket.ccic.util;

import com.oket.ccic.util.base.Constants;
import com.oket.ccic.vo.UserVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Component;

/**
 * @program: Layer
 * @description: 参数util
 * @author: dzp
 * @create: 2021-08-16 16:40
 **/
@Component
public class ShiroUtils {

    /**
     * 根据当前登录用户返回对应的companyId
     * 如果是admin和普通管理员 则为null
     *
     * @return
     */
    public Integer getCompanyId() {
        UserVo userVo = getLoginUserVo();
        Integer companyId = null;
        if (userVo.getRoleId() != Constants.ADMIN_ID && userVo.getRoleId() != Constants.N_ADMIN_ID) {
            companyId = userVo.getCompanyId();
        }
        return companyId;
    }

    /**
     * 获取当前登录的用户
     *
     * @return
     */
    public UserVo getLoginUserVo() {
        Session session = SecurityUtils.getSubject().getSession();
        return (UserVo) session.getAttribute(Constants.SESSION_USERDTO_INFO);
    }

    /**
     * 获取当前用户session
     *
     * @return
     */
    public Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }
}
