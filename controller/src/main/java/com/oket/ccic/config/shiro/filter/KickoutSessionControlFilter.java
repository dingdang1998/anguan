package com.oket.ccic.config.shiro.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.feilong.core.util.CollectionsUtil;
import com.oket.ccic.entity.LoginSession;
import com.oket.ccic.util.ShiroUtils;
import com.oket.ccic.util.base.CodeEnum;
import com.oket.ccic.util.base.Message;
import com.xiaoleilu.hutool.http.HttpUtil;
import lombok.Getter;
import lombok.Setter;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Date;
import java.util.Deque;
import java.util.LinkedList;

/**
 * @program: Layer
 * @description: 并发登录控制
 * @author: dzp
 * @create: 2021-08-20 13:49
 **/
@Getter
@Setter
public class KickoutSessionControlFilter extends AccessControlFilter {


    private static final Logger logger = LoggerFactory.getLogger(KickoutSessionControlFilter.class);

    private static final String KICKOUT_STR = "kickout";
    private static final String SESSION_ID_STR = "sessionId";
    /**
     * 踢出之前登录的/之后登录的用户，默认踢出之前登录的用户
     */
    private boolean kickoutAfter = false;
    /**
     * 同一个帐号最大会话数 默认1
     */
    private int maxSession = 1;
    /**
     * shiro工具类
     */
    private ShiroUtils shiroUtils;
    /**
     * session管理
     */
    private SessionManager sessionManager;
    /**
     * 缓存
     */
    private Cache<String, Deque<LoginSession>> cache;

    /**
     * 获取缓存
     *
     * @param cacheManager
     */
    public void setCacheManager(CacheManager cacheManager) {
        this.cache = cacheManager.getCache("shiro-kickout-session");
    }

    /**
     * 是否允许访问，返回true表示允许
     *
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) {
        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);

        Subject subject = getSubject(request, response);

        if (!subject.isAuthenticated() && !subject.isRemembered()) {
            //如果没有登录，直接进行之后的流程
            return true;
        }

        //获取当前登录信息
        Session session = subject.getSession();
        Serializable sessionId = session.getId();
        String username = shiroUtils.getLoginUserVo().getUsername();

        //读取缓存没有就存入
        Deque<LoginSession> deque = cache.get(username);
        if (deque == null) {
            deque = new LinkedList<>();
            cache.put(username, deque);
        }

        //如果队列里没有此sessionId，且用户没有被踢出；放入队列
        //这里给这部分代码块加了锁，因为在测试的时候会有一个用户登录同时给队列放两次相同用户信息的情况，不知道问题出现在哪里，后续有知道的可以修改此部分（可能是ehcache的问题）
        synchronized (this) {
            if (CollectionsUtil.find(deque, SESSION_ID_STR, sessionId) == null && session.getAttribute(KICKOUT_STR) == null) {
                //将sessionId存入队列
                LoginSession loginSession = new LoginSession();
                loginSession.setSessionId(sessionId);
                loginSession.setLoginTime(new Date());
                loginSession.setLoginIp(HttpUtil.getClientIP(httpServletRequest));
                deque.push(loginSession);

                logger.info(username + "--存放的session--" + loginSession.toString() + "---" + System.currentTimeMillis());

                //将用户的sessionId队列缓存
                cache.put(username, deque);
            }

            //如果队列里的sessionId数超出最大会话数，开始踢人
            while (deque.size() > maxSession) {
                //要踢出的登录用户信息
                LoginSession kickoutLoginSession;
                if (kickoutAfter) {
                    //如果踢出后者
                    kickoutLoginSession = deque.removeFirst();
                } else {
                    //否则踢出前者
                    kickoutLoginSession = deque.removeLast();
                }

                //踢出后再更新下缓存队列
                cache.put(username, deque);

                try {
                    //获取被踢出的sessionId的session对象
                    Session kickoutSession = sessionManager.getSession(new DefaultSessionKey(kickoutLoginSession.getSessionId()));
                    if (kickoutSession != null) {
                        //设置会话的kickout属性表示踢出了
                        kickoutSession.setAttribute(KICKOUT_STR, true);
                        logger.info(username + "--被设置踢出属性--" + kickoutLoginSession.toString() + System.currentTimeMillis());
                    }
                } catch (Exception e) {
                    logger.error("踢出用户:{},失败" + e.getMessage(), username);
                }
            }
        }

        //校验当前登录用户是否已经被顶掉
        if (Boolean.valueOf(true).equals(session.getAttribute(KICKOUT_STR))) {
            PrintWriter out = null;
            try {
                httpServletResponse.setCharacterEncoding("UTF-8");
                httpServletResponse.setContentType("application/json");
                out = response.getWriter();
                out.println(new ObjectMapper().writeValueAsString(new Message(false, CodeEnum.KICK_OUT_TIPS.getCode(), CodeEnum.KICK_OUT_TIPS.getMsg())));
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

        return true;
    }
}

