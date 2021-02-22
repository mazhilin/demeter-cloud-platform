package com.demeter.cloud.console.configuration;

import com.demeter.cloud.console.shiro.ConsoleAuthorizeRealm;
import com.demeter.cloud.console.shiro.ConsoleWebSessionManager;
import com.google.common.collect.Maps;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.Map;

/**
 * @author marklin
 */
@Configuration
public class ConsoleSecurityConfiguration {

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
		// 配置SecurityManager
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		Map<String, String> filterChainDefinitionMap = Maps.newConcurrentMap();
		// 配置静态资源URL
		//TODO...

		// 配置公共资源URL
		filterChainDefinitionMap.put("/admin/authority/login", "anon");
		filterChainDefinitionMap.put("/admin/authority/401", "anon");
		filterChainDefinitionMap.put("/admin/authority/index", "anon");
		filterChainDefinitionMap.put("/admin/authority/403", "anon");
		// everything else requires authentication:
		filterChainDefinitionMap.put("/admin/**", "authc");
		// 统一权限配置
		// 配置登录的URL
		shiroFilterFactoryBean.setLoginUrl("/admin/authority/401");
		// 配置成功后的URL
		shiroFilterFactoryBean.setSuccessUrl("/admin/authority/index");
		// 配置无权限路径
		shiroFilterFactoryBean.setUnauthorizedUrl("/admin/authority/403");
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
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
		return new LifecycleBeanPostProcessor();
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
