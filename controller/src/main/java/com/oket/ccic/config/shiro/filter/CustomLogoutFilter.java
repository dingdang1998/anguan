package com.oket.ccic.config.shiro.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.feilong.core.util.CollectionsUtil;
import com.oket.ccic.entity.LoginSession;
import com.oket.ccic.service.impl.LoginServiceImpl;
import com.oket.ccic.util.EhCacheUtils;
import com.oket.ccic.util.ShiroUtils;
import com.oket.ccic.util.base.CodeEnum;
import com.oket.ccic.util.base.Message;
import lombok.Getter;
import lombok.Setter;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Deque;
import java.util.LinkedList;

/**
 * @packageName: com.jk.shiro.filter
 * @description: 自定义退出过滤器，实现清除缓存
 * @author: cuiP
 * @date: 2017/7/31 23:24
 * @version: V1.0.0
 */
@Getter
@Setter
public class CustomLogoutFilter extends LogoutFilter {

    private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    private ShiroUtils shiroUtils;

    /**
     * 在这里执行退出系统前需要清空的数据
     *
     * @param request
     * @param response
     * @return
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) {

        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
        Subject subject = SecurityUtils.getSubject();

        //清除账号登陆限制缓存
        if (subject.getPrincipal() != null && subject.isAuthenticated()) {
            //清除账号登录限制
            String username = shiroUtils.getLoginUserVo().getUsername();
            Serializable sessionId = shiroUtils.getSession().getId();

            Deque<LoginSession> deque = (Deque<LoginSession>) EhCacheUtils.get("shiro-kickout-session", username);

            if (deque == null) {
                deque = new LinkedList<>();
                EhCacheUtils.put("shiro-kickout-session", username, deque);
            }

            //从队列中删除当前用户的session信息
            LoginSession loginSession = CollectionsUtil.find(deque, "sessionId", sessionId);
            if (loginSession != null) {
                deque.remove(loginSession);
                logger.info(username + "--删除session信息--" + loginSession.toString() + "--" + System.currentTimeMillis());
                //更新缓存
                EhCacheUtils.put("shiro-kickout-session", username, deque);
            }
        }

        try {
            SecurityUtils.getSubject().logout();
        } catch (SessionException var6) {
            logger.debug("Encountered session exception during logout.  This can generally safely be ignored.", var6);
        }

        //向前端返回响应
        PrintWriter out = null;
        try {
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.setContentType("application/json");
            out = response.getWriter();
            out.println(new ObjectMapper().writeValueAsString(new Message(false, CodeEnum.LOGIN_OUT_SUCCESS.getCode(), CodeEnum.LOGIN_OUT_SUCCESS.getMsg())));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != out) {
                out.flush();
                out.close();
            }
        }
        return false;
    }
}
