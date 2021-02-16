package com.demeter.cloud.console.controller;

import com.alibaba.fastjson.JSONObject;
import com.demeter.cloud.console.utils.ConsoleWebResponseUtil;
import com.demeter.cloud.console.web.ConsoleWebResponse;
import com.demeter.cloud.core.util.CheckEmptyUtil;
import com.demeter.cloud.core.util.IpUtil;
import com.demeter.cloud.core.util.JacksonUtil;
import com.demeter.cloud.core.util.ResponseUtil;
import com.demeter.cloud.model.entity.AdminUser;
import com.demeter.cloud.model.persistence.controller.BaseController;
import com.demeter.cloud.model.service.AdminUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>封装Qicloud项目ConsoleUserController类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-16 22:59
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
@RestController
@RequestMapping(value = "/console/user/")
@Validated
public class ConsoleUserController extends BaseController {

    @Autowired
    private AdminUserService adminUserService;

    @PostMapping(value = "/login")
    public Object login(@RequestBody String body, HttpServletRequest request) {
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
            if (CheckEmptyUtil.isNotEmpty(adminList) && adminList.size() < 2) {
                AdminUser admin = adminList.get(0);
                admin.setLastLoginTime(LocalDateTime.now());
                admin.setLastLoginIp(IpUtil.getIpAddr(request));
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

}
