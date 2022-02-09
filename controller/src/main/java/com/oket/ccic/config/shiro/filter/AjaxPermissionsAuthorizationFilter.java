package com.oket.ccic.config.shiro.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oket.ccic.util.base.CodeEnum;
import com.oket.ccic.util.base.Message;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author ysh
 * @description: 对没有登录的请求进行拦截, 全部返回json信息,覆盖掉shiro原本的跳转login.jsp的拦截方式
 */
public class AjaxPermissionsAuthorizationFilter extends FormAuthenticationFilter {

    private static final Logger logger = LoggerFactory.getLogger(AjaxPermissionsAuthorizationFilter.class);

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) {
        PrintWriter out = null;
        HttpServletResponse res = (HttpServletResponse) response;
        try {
            res.setCharacterEncoding("UTF-8");
            res.setContentType("application/json");
            out = response.getWriter();
            out.println(new ObjectMapper().writeValueAsString(new Message(false, CodeEnum.LOGIN_OVER_DUE.getCode(), CodeEnum.LOGIN_OVER_DUE.getMsg())));
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

    @Bean
    public FilterRegistrationBean registration(AjaxPermissionsAuthorizationFilter filter) {
        FilterRegistrationBean registration = new FilterRegistrationBean(filter);
        registration.setEnabled(false);
        return registration;
    }
}
