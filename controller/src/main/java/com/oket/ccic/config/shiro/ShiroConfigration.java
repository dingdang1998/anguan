package com.oket.ccic.config.shiro;

import com.oket.ccic.config.shiro.filter.AjaxPermissionsAuthorizationFilter;
import com.oket.ccic.config.shiro.filter.CustomLogoutFilter;
import com.oket.ccic.config.shiro.filter.KickoutSessionControlFilter;
import com.oket.ccic.util.ShiroUtils;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.ExecutorServiceSessionValidationScheduler;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @program: storetank
 * @description: shiro配置类
 * @author: dzp
 * @create: 2021-05-25 11:32
 **/
@Configuration
public class ShiroConfigration {

    /**
     * Shiro的Web过滤器Factory 命名:shiroFilter
     * 注意：自定义过滤器不要注册成bean然后在集成到shiro过滤器链中，这样过滤器会被sring调用，造成shiro过滤器链调用顺序混乱
     */
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager, CacheManager cacheManager, SessionManager sessionManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //Shiro的核心安全接口,这个属性是必须的
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        Map<String, Filter> filterMap = new LinkedHashMap<>();
        //无效请求拦截
        filterMap.put("authc", new AjaxPermissionsAuthorizationFilter());
        //自定义限制同一账号登录数量filter
        KickoutSessionControlFilter kickoutSessionControlFilter = new KickoutSessionControlFilter();
        kickoutSessionControlFilter.setShiroUtils(new ShiroUtils());
        kickoutSessionControlFilter.setSessionManager(sessionManager);
        kickoutSessionControlFilter.setCacheManager(cacheManager);
        filterMap.put("kickout", kickoutSessionControlFilter);
        //自定义退出拦截器
        CustomLogoutFilter logoutFilter = new CustomLogoutFilter();
        logoutFilter.setShiroUtils(new ShiroUtils());
        filterMap.put("myLogout", logoutFilter);

        shiroFilterFactoryBean.setFilters(filterMap);
        /*定义shiro过滤链  Map结构
         * Map中key(xml中是指value值)的第一个'/'代表的路径是相对于HttpServletRequest.getContextPath()的值来的
         * anon：它对应的过滤器里面是空的,什么都没做,这里.do和.jsp后面的*表示参数,比方说login.jsp?main这种
         * authc：该过滤器下的页面必须验证后才能访问,它是Shiro内置的一个拦截器org.apache.shiro.web.filter.authc.FormAuthenticationFilter
         */
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
         /* 过滤链定义，从上向下顺序执行，一般将 / ** 放在最为下边:这是一个坑呢，一不小心代码就不好使了;
          authc:所有url都必须认证通过才可以访问; anon:所有url都可以匿名访问 */
        //swagger url过滤
        filterChainDefinitionMap.put("/swagger-ui.html", "anon");
        filterChainDefinitionMap.put("/swagger-resources/**", "anon");
        filterChainDefinitionMap.put("/v2/api-docs/**", "anon");
        filterChainDefinitionMap.put("/webjars/springfox-swagger-ui/**", "anon");
        // 其他过滤
        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/druid/**", "anon");
        filterChainDefinitionMap.put("/login/auth", "anon");
        filterChainDefinitionMap.put("/login/logout", "myLogout");
        filterChainDefinitionMap.put("/error", "anon");
        filterChainDefinitionMap.put("/user/get2", "anon");
        filterChainDefinitionMap.put("/user/get4", "anon");
        filterChainDefinitionMap.put("/user/register", "anon");
        filterChainDefinitionMap.put("/user/update2", "anon");
        filterChainDefinitionMap.put("/mail/send", "anon");
        filterChainDefinitionMap.put("/mail/send2", "anon");
        filterChainDefinitionMap.put("/**", "authc,kickout");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    @Bean(name = "sessionManager")
    public DefaultWebSessionManager getSessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        //session的失效时长，单位毫秒 60分钟失效
        sessionManager.setGlobalSessionTimeout(3600000);
        sessionManager.setSessionValidationScheduler(getExecutorServiceSessionValidationScheduler());
        sessionManager.setSessionValidationSchedulerEnabled(true);
        //删除失效的session
        sessionManager.setDeleteInvalidSessions(true);
        sessionManager.setSessionIdCookieEnabled(true);
        sessionManager.setSessionIdCookie(getSessionIdCookie());
        //去除浏览器地址栏中url中JSESSIONID参数
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        return sessionManager;
    }

    /**
     * Shiro Realm 继承自AuthorizingRealm的自定义Realm,即指定Shiro验证用户登录的类为自定义的
     */
    @Bean
    public UserRealm userRealm() {
        UserRealm userRealm = new UserRealm();
        userRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return userRealm;
    }

    /**
     * 相当于调用SecurityUtils.setSecurityManager(securityManager)
     *
     * @return
     */
    @Bean
    public MethodInvokingFactoryBean getMethodInvokingFactoryBean(EhCacheManagerFactoryBean ehCacheManagerFactoryBean) {
        MethodInvokingFactoryBean factoryBean = new MethodInvokingFactoryBean();
        factoryBean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
        factoryBean.setArguments(getSecurityManager(ehCacheManagerFactoryBean));
        return factoryBean;
    }

    /**
     * securityManager安全管理器
     * Shiro的核心安全接口,这个属性是必须的
     */
    @Bean(name = "securityManager")
    public SecurityManager getSecurityManager(EhCacheManagerFactoryBean ehCacheManagerFactoryBean) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //注入自定义Realm
        securityManager.setRealm(userRealm());
        //注入session管理器
        securityManager.setSessionManager(getSessionManager());
        securityManager.setCacheManager(getCacheShiroManage(ehCacheManagerFactoryBean));
        return securityManager;
    }

    /**
     * 缓存管理器 使用Ehcache实现
     *
     * @return
     */
    @Bean(name = "cacheShiroManager")
    public CacheManager getCacheShiroManage(EhCacheManagerFactoryBean ehCacheManagerFactoryBean) {
        EhCacheManager ehCacheManager = new EhCacheManager();
        ehCacheManager.setCacheManager(ehCacheManagerFactoryBean.getObject());
        return ehCacheManager;
    }

    /**
     * 凭证匹配器
     * （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了
     * 所以我们需要修改下doGetAuthenticationInfo中的代码;
     * ）
     * 可以扩展凭证匹配器，实现 输入密码错误次数后锁定等功能，下一次
     */
    @Bean(name = "credentialsMatcher")
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        //散列算法:这里使用MD5算法;
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        //散列的次数，比如散列两次，相当于 md5(md5(""));
        hashedCredentialsMatcher.setHashIterations(2);
        //storedCredentialsHexEncoded默认是true，此时用的是密码加密用的是Hex编码；false时用Base64编码
        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);
        return hashedCredentialsMatcher;
    }

    /**
     * Shiro生命周期处理器
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator(可选)和AuthorizationAttributeSourceAdvisor)即可实现此功能
     */
    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(EhCacheManagerFactoryBean ehCacheManagerFactoryBean) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(getSecurityManager(ehCacheManagerFactoryBean));
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * 会话验证调度器
     *
     * @return
     */
    @Bean(name = "sessionValidationScheduler")
    public ExecutorServiceSessionValidationScheduler getExecutorServiceSessionValidationScheduler() {
        ExecutorServiceSessionValidationScheduler scheduler = new ExecutorServiceSessionValidationScheduler();
        //每隔60分钟验证清理失效的session
        scheduler.setInterval(3600000);
        return scheduler;
    }

    /**
     * 会话Cookie模板
     *
     * @return
     */
    @Bean(name = "sessionIdCookie")
    public SimpleCookie getSessionIdCookie() {
        SimpleCookie cookie = new SimpleCookie("sid");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(-1);
        return cookie;
    }

    /**
     * 会话DAO
     *
     * @return
     */
    @Bean(name = "sessionDao")
    public EnterpriseCacheSessionDAO getSessionDao() {
        EnterpriseCacheSessionDAO sessionDAO = new EnterpriseCacheSessionDAO();
        sessionDAO.setActiveSessionsCacheName("shiro-activeSessionCache");
        sessionDAO.setSessionIdGenerator(getSessionIdGenerator());
        return sessionDAO;
    }

    /**
     * 会话ID生成器
     *
     * @return
     */
    @Bean(name = "sessionIdGenerator")
    public JavaUuidSessionIdGenerator getSessionIdGenerator() {
        return new JavaUuidSessionIdGenerator();
    }
}
