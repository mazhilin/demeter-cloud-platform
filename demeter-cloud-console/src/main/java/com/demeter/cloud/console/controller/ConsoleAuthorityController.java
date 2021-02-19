package com.demeter.cloud.console.controller;

import com.alibaba.fastjson.JSONObject;
import com.demeter.cloud.console.utils.ConsolePermissionUtil;
import com.demeter.cloud.console.utils.ConsoleWebResponseUtil;
import com.demeter.cloud.console.web.ConsoleWebResponse;
import com.demeter.cloud.console.web.Permission;
import com.demeter.cloud.core.util.CheckEmptyUtil;
import com.demeter.cloud.core.util.IpAddressUtil;
import com.demeter.cloud.core.util.JacksonUtil;
import com.demeter.cloud.core.util.ResponseUtil;
import com.demeter.cloud.model.entity.AdminUser;
import com.demeter.cloud.model.persistence.controller.BaseController;
import com.demeter.cloud.model.service.AdminUserService;
import com.demeter.cloud.model.service.PermissionInfoService;
import com.demeter.cloud.model.service.RoleInfoService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.*;

/**
 * <p>封装Qicloud项目ConsoleAuthorityController类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-17 03:03
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
@RestController
@RequestMapping(value = "/api/console/authority/")
@Validated
public class ConsoleAuthorityController extends BaseController {

    @Autowired
    private AdminUserService adminUserService;
    @Autowired
    private ApplicationContext context;
    private HashMap<String, String> permissionsMap = null;

    @Autowired
    private RoleInfoService roleInfoService;
    @Autowired
    private PermissionInfoService permissionInfoService;

    @PostMapping(value = "login")
    public Object login(@RequestBody String body,
                        HttpServletRequest request) {
        logger.info("【请求开始】系统管理->用户登录,请求参数:body:{}", body);
        String account = JacksonUtil.parseString(body, "account");
        String password = JacksonUtil.parseString(body, "password");
        if (StringUtils.isEmpty(account) || StringUtils.isEmpty(password)) {
            return ResponseUtil.badArgument();
        }
        Subject currentUser = SecurityUtils.getSubject();
        try {
            currentUser.login(new UsernamePasswordToken(account, password));
            List<AdminUser> adminList = adminUserService.queryAdminUserByAccount(account);
            if (CheckEmptyUtil.isNotEmpty(adminList) && adminList.size() == 1) {
                AdminUser admin = adminList.get(0);
                admin.setLastLoginTime(LocalDateTime.now());
                admin.setLastLoginIp(IpAddressUtil.getIpAddress(request));
                adminUserService.updateById(admin);
            }
        } catch (UnknownAccountException uae) {
            logger.error("系统管理->用户登录  错误:{}", ConsoleWebResponse.ADMIN_INVALID_ACCOUNT_OR_PASSWORD.message());
            return ConsoleWebResponseUtil.fail(ConsoleWebResponse.ADMIN_INVALID_ACCOUNT_OR_PASSWORD);
        } catch (LockedAccountException lae) {
            logger.error("系统管理->用户登录 错误:{}", ConsoleWebResponse.ADMIN_LOCK_ACCOUNT.message());
            return ConsoleWebResponseUtil.fail(ConsoleWebResponse.ADMIN_LOCK_ACCOUNT);

        } catch (AuthenticationException ae) {
            logger.error("系统管理->用户登录 错误:{}", ConsoleWebResponse.ADMIN_LOCK_ACCOUNT.message());
            return ConsoleWebResponseUtil.fail(ConsoleWebResponse.ADMIN_INVALID_AUTH);
        }
        logger.info("【请求结束】系统管理->用户登录,响应结果:{}", JSONObject.toJSONString(currentUser.getSession().getId()));
        return ResponseUtil.ok(currentUser.getSession().getId());
    }


    @PostMapping("/logout")
    public Object logout() {
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
        logger.info("【请求结束】系统管理->用户注销,响应结果:{}", JSONObject.toJSONString(currentUser.getSession().getId()));
        return ResponseUtil.ok();
    }


    @RequiresAuthentication
    @GetMapping("/info")
    public Object info() {
        Subject currentUser = SecurityUtils.getSubject();
        AdminUser admin = (AdminUser) currentUser.getPrincipal();

        Map<String, Object> data = new HashMap<>();
        data.put("name", admin.getName());
        data.put("avatar", admin.getProfilePicture());

        Integer[] roleIds = admin.getRoleIds();
        Set<String> roles = roleInfoService.queryByIds(roleIds);
        Set<String> permissions = permissionInfoService.queryByRoleIds(roleIds);
        data.put("roles", roles);
        // NOTE
        // 这里需要转换perms结构，因为对于前端而已API形式的权限更容易理解
        data.put("perms", toAPI(permissions));

        logger.info("【请求结束】系统管理->用户信息获取,响应结果:{}", JSONObject.toJSONString(data));
        return ResponseUtil.ok(data);
    }

    private Collection<String> toAPI(Set<String> permissions) {
        if (permissionsMap == null) {
            permissionsMap = new HashMap<>();
            final String basicPackage = "com.demeter.cloud.console";
            List<Permission> systemPermissions = ConsolePermissionUtil.listPermission(context, basicPackage);
            for (Permission permission : systemPermissions) {
                String perm = permission.getRequiresPermissions().value()[0];
                String api = permission.getApi();
                permissionsMap.put(perm, api);
            }
        }

        Collection<String> apis = new HashSet<>();
        for (String permission : permissions) {
            String api = permissionsMap.get(permission);
            apis.add(api);

            if (permission.equals("*")) {
                apis.clear();
                apis.add("*");
                return apis;
            }
        }
        return apis;
    }
    @GetMapping("/401")
    public Object page401() {
        return ResponseUtil.unlogin();
    }

    @GetMapping("/501")
    public Object page501() {
        return ResponseUtil.unlogin();
    }

    @GetMapping("/index")
    public Object pageIndex() {
        return ResponseUtil.ok();
    }

    @GetMapping("/503")
    public Object page503() {
        return ResponseUtil.unsupport();
    }

    @GetMapping("/403")
    public Object page403() {
        return ResponseUtil.unauthz();
    }
}
