package com.demeter.cloud.console.shiro;

import com.demeter.cloud.core.util.bcrypt.BCryptPasswordEncoder;
import com.demeter.cloud.model.entity.AdminUser;
import com.demeter.cloud.model.service.AdminUserService;
import com.demeter.cloud.model.service.PermissionInfoService;
import com.demeter.cloud.model.service.RoleInfoService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;

/**
 * 授权相关服务-shiro
 * 
 * @author qiguliuxing
 * @since 1.0.0
 */
public class ConsoleAuthorizeRealm extends AuthorizingRealm {

	private static final Logger logger = LoggerFactory.getLogger(ConsoleAuthorizeRealm.class);
	@Autowired
	private AdminUserService adminUserService;
	@Autowired
	private RoleInfoService roleInfoService;
	@Autowired
	private PermissionInfoService permissionInfoService;


	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		if (principals == null) {
			throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
		}
		AdminUser admin = (AdminUser) getAvailablePrincipal(principals);
		Integer[] roleIds = admin.getRoleIds();
		Set<String> roles = roleInfoService.queryByIds(roleIds);
		Set<String> permissions = permissionInfoService.queryByRoleIds(roleIds);
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.setRoles(roles);
		info.setStringPermissions(permissions);
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		String account = upToken.getUsername();
		String password = new String(upToken.getPassword());

		if (StringUtils.isEmpty(account)) {
			throw new AccountException("用户账户不能为空!");
		}
		if (StringUtils.isEmpty(password)) {
			throw new AccountException("用户密码不能为空!");
		}

		List<AdminUser> adminList = adminUserService.queryAdminUserByAccount(account);
		Assert.state(adminList.size() < 2, "同一个用户名存在两个账户");
		if (adminList.size() == 0) {
			logger.error("找不到用户（" + account + "）的帐号信息");
			throw new UnknownAccountException("找不到用户（" + account + "）的帐号信息");
		}
		AdminUser admin = adminList.get(0);

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		if (!encoder.matches(password, admin.getPassword())) {
			logger.error("找不到用户（" + account + "）的帐号信息");
			throw new UnknownAccountException("找不到用户（" + account + "）的帐号信息");
		}

		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo();
		info.setPrincipals(new SimplePrincipalCollection(admin, getName()));
		info.setCredentials(password);
		return info;
	}

}
