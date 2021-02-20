package com.demeter.cloud.console.configuration;

import com.demeter.cloud.console.shiro.ConsoleAuthorizeRealm;
import com.demeter.cloud.console.shiro.ConsoleWebSessionManager;
import com.google.common.collect.Maps;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.Map;

@Configuration
public class ShiroConfig {

	@Bean
	public Realm realm() {
		return new ConsoleAuthorizeRealm();
	}

	/**
	 * 核心权限配置
	 * @param securityManager 安全权限
	 * @return 返回
	 */
	@Bean
	public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		// 统一权限配置
		shiroFilterFactoryBean.setLoginUrl("/console/authority/401");
		shiroFilterFactoryBean.setSuccessUrl("/console/authority/index");
		shiroFilterFactoryBean.setUnauthorizedUrl("/console/authority/403");
		Map<String, String> filterChainDefinitionMap = Maps.newConcurrentMap();
		filterChainDefinitionMap.put("/console/authority/login", "anon");
		filterChainDefinitionMap.put("/console/authority/logout", "logout");
		filterChainDefinitionMap.put("/console/authority/401", "anon");
		filterChainDefinitionMap.put("/console/authority/index", "anon");
		filterChainDefinitionMap.put("/console/authority/403", "anon");
		filterChainDefinitionMap.put("/console/authority/503", "anon");
		filterChainDefinitionMap.put("/console/**", "authc");
		filterChainDefinitionMap.put("/admin/**", "authc");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return shiroFilterFactoryBean;
	}

	@Bean
	public SessionManager sessionManager() {
		ConsoleWebSessionManager webSession = new ConsoleWebSessionManager();
		webSession.setSessionIdCookieEnabled(true);
		webSession.setSessionIdUrlRewritingEnabled(true);
		return webSession;
	}

	@Bean
	public DefaultWebSecurityManager securityManager() {
		DefaultWebSecurityManager security = new DefaultWebSecurityManager();
		security.setRealm(realm());
		security.setSessionManager(sessionManager());
		return security;
	}

	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}

	@Bean
	@DependsOn("lifecycleBeanPostProcessor")
	public static DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
		creator.setProxyTargetClass(true);
		creator.setUsePrefix(true);
		return creator;
	}
}
