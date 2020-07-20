package com.player.es.Config;

import com.player.es.shiro.JwtDefaultSubjectFactory;
import com.player.es.shiro.JwtFilter;
import com.player.es.shiro.JwtRealm;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SubjectFactory;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

//springBoot整合jwt实现认证有三个不一样的地方，对应下面abc
@Configuration
public class ShiroConfig {
    @Autowired
    JwtFilter jwtFilter;
    /*
     * a. 告诉shiro不要使用默认的DefaultSubject创建对象，因为不能创建Session
     * */
    @Bean
    public SubjectFactory subjectFactory() {
        return new JwtDefaultSubjectFactory();
    }

    @Bean
    public Realm realm() {
        return new JwtRealm();
    }

    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 配置 SecurityManager，并注入Realm
        securityManager.setRealm(realm());
        // 关闭 ShiroDAO 功能
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        // 不需要将 Shiro Session 中的东西存到任何地方（包括 Http Session 中）
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);
        //禁止Subject的getSession方法
        securityManager.setSubjectFactory(subjectFactory());
        return securityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean() {
        ShiroFilterFactoryBean FilterFactoryBean  = new ShiroFilterFactoryBean();
        FilterFactoryBean .setSecurityManager(securityManager());
        FilterFactoryBean .setLoginUrl("/login");
        FilterFactoryBean .setUnauthorizedUrl("/unauthorized");
       /**
         * 也就是将jwtFilter注册到shiro的Filter中
         * 指定除了login和logout之外的请求都先经过jwtFilter
         * 常用的过滤器：
         * anon: 无需认证（登录）可以访问
         * authc: 必须认证才可以访问
         * user: 如果使用rememberMe的功能可以直接访问
         * perms： 该资源必须得到资源权限才可以访问
         * role: 该资源必须得到角色权限才可以访问
         */
        // 在 Shiro过滤器链上加入 自定义过滤器JWTFilter 并取名为jwt
        Map<String, Filter> filterMap = new HashMap<>();
        //这个地方其实另外两个filter可以不设置，默认就是
        filterMap.put("anon", new AnonymousFilter());
        filterMap.put("jwt", jwtFilter);
        filterMap.put("logout", new LogoutFilter());
        FilterFactoryBean .setFilters(filterMap);

        // 拦截器
        Map<String, String> filterRuleMap = new LinkedHashMap<>();
        filterRuleMap.put("/login", "anon");
        filterRuleMap.put("/logout", "logout");
        filterRuleMap.put("/**", "jwt");
        FilterFactoryBean .setFilterChainDefinitionMap(filterRuleMap);

        return FilterFactoryBean ;
    }
    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chain = new DefaultShiroFilterChainDefinition();
        // 哪些请求可以匿名访问
        chain.addPathDefinition("/login", "anon");      // 登录接口
        chain.addPathDefinition("/notLogin", "anon");   // 未登录错误提示接口
        chain.addPathDefinition("/403", "anon");    // 权限不足错误提示接口
        // 除了以上的请求外，其它请求都需要登录
        chain.addPathDefinition("/**", "authc");
        return chain;
    }
    // Shiro 和 Spring AOP 整合时的特殊设置
    @Bean
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }
}

