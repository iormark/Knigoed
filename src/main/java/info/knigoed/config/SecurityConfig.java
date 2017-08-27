package info.knigoed.config;

import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class SecurityConfig {

    @Bean
    public Authorizing customSecurityRealm() {
        return new Authorizing();
    }

    @Bean
    public WebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(customSecurityRealm());
        return securityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterBean() {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        Map<String, String> definitionMap = new HashMap<>();
        definitionMap.put("/shop-list", "authc, roles[admin]");
        definitionMap.put("/shop/**", "authc, roles[admin]");
        //definitionsMap.put("/**", "authc");
        shiroFilter.setFilterChainDefinitionMap(definitionMap);

        //Map<String, Filter> filters = new HashMap<>();
        //filters.put("anon", new AnonymousFilter());
        //filters.put("authc", new FormAuthenticationFilter());
        //filters.put("logout", new LogoutFilter());
        //filters.put("roles", new RolesAuthorizationFilter());
        //filters.put("user", new UserFilter());
        //shiroFilter.setFilters(filters);

        shiroFilter.setLoginUrl("/signin");
        shiroFilter.setSuccessUrl("/");
        //shiroFilter.setUnauthorizedUrl("/logout");
        shiroFilter.setSecurityManager(securityManager());
        return shiroFilter;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    @DependsOn(value = "lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        return new DefaultAdvisorAutoProxyCreator();
    }

    /*@Bean
    public MethodInvokingFactoryBean methodInvokingFactoryBean() {
        MethodInvokingFactoryBean methodInvokingFactoryBean = new MethodInvokingFactoryBean();
        methodInvokingFactoryBean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
        methodInvokingFactoryBean.setArguments(new Object[]{securityManager()});
        return methodInvokingFactoryBean;
    }*/


    //

/*
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
        return authorizationAttributeSourceAdvisor;
    }*/

}

