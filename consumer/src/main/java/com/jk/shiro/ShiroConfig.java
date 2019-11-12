package com.jk.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import ch.qos.logback.core.db.dialect.MySQLDialect;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

/**
 * author：wdd
 * create time:2019/10/12
 * email：
 * tel：
 */
@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean sfb = new ShiroFilterFactoryBean();
        sfb.setSecurityManager(securityManager);//添加securityManager，要不然没法认证
        sfb.setLoginUrl("/page/tologin");//设置登录页面的地址，默认login页面
        //sfb.setUnauthorizedUrl("/403");//定义没有权限，跳转到403页面【无效】

        //拦截的规则 K:路径 v:拦截还是不拦截
        //- anon:所有url都都可以匿名访问
        //- authc: 需要认证才能进行访问
        //logout :注销
        Map<String, String> map = new LinkedHashMap<>();
        // 配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
        // 浏览器访问的地址栏路径中以/logout结尾的路径 走logout过滤器
        // logout会清除session 退出登录
        map.put("/logout", "logout");//注销
        map.put("/page/tologin", "anon");
        map.put("/User/login", "anon");
        //放开静态资源
        map.put("/js/**", "anon");
        map.put("/css/**", "anon");
        map.put("/page/tomain", "anon");
        //其他的都拦截
        //<!-- 过滤链定义，从上向下顺序执行，一般将/**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
        map.put("/**", "authc");
        sfb.setFilterChainDefinitionMap(map);
        return sfb;
    }

    @Bean
    public SecurityManager securityManager(MyRealm myRealm,EhCacheManager ehCacheManager) {
        DefaultWebSecurityManager dwsm = new DefaultWebSecurityManager();
        dwsm.setRealm(myRealm);
        //dwsm.setCacheManager(ehCacheManager);//开启缓存
        return dwsm;
    }

    @Bean
    public MyRealm test() {
        return new MyRealm();
    }

    /**
     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator和AuthorizationAttributeSourceAdvisor)即可实现此功能
     *
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * 开启AOP注解支持
     *
     * @return
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAAP = new DefaultAdvisorAutoProxyCreator();
        defaultAAP.setProxyTargetClass(true);
        return defaultAAP;
    }


    @Bean
    public SimpleMappingExceptionResolver exceptionResolver() {
        SimpleMappingExceptionResolver smer = new SimpleMappingExceptionResolver();
        Properties p = new Properties();
        p.setProperty("org.apache.shiro.authz.UnauthorizedException", "/403");
        smer.setExceptionMappings(p);
        return smer;
    }

    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }

    @Bean
    public EhCacheManager ehCacheManager() {
        return new EhCacheManager();
    }
}