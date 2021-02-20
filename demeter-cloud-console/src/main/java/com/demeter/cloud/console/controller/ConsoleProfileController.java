package com.demeter.cloud.console.controller;

import com.demeter.cloud.console.utils.ConsoleWebResponseUtil;
import com.demeter.cloud.core.util.JacksonUtil;
import com.demeter.cloud.core.util.ResponseUtil;
import com.demeter.cloud.core.util.bcrypt.BCryptPasswordEncoder;
import com.demeter.cloud.model.entity.AdminUser;
import com.demeter.cloud.model.persistence.controller.BaseController;
import com.demeter.cloud.model.service.AdminUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.demeter.cloud.console.web.ConsoleWebResponse.ADMIN_INVALID_OLD_PASSWORD;

/**
 * <p>封装Qicloud项目ConsoleProfileController类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-17 16:32
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
@RestController
@RequestMapping(value = "/api/console/profile")
@Validated
public class ConsoleProfileController extends BaseController {

    @Autowired
    private AdminUserService adminUserService;

    @RequiresAuthentication
    @PostMapping(value = "password")
    public Object create(@RequestBody String body) {
        logger.info("【请求开始】系统管理->修改密码,请求参数,body:{}", body);
        String oldPassword = JacksonUtil.parseString(body, "oldPassword");
        String newPassword = JacksonUtil.parseString(body, "newPassword");
        if (StringUtils.isEmpty(oldPassword)) {
            return ResponseUtil.badArgument();
        }
        if (StringUtils.isEmpty(newPassword)) {
            return ResponseUtil.badArgument();
        }

        Subject currentUser = SecurityUtils.getSubject();
        AdminUser admin = (AdminUser) currentUser.getPrincipal();

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(oldPassword, admin.getPassword())) {
            logger.info("系统管理->修改密码 错误:{}", ADMIN_INVALID_OLD_PASSWORD.message());
            return ConsoleWebResponseUtil.fail(ADMIN_INVALID_OLD_PASSWORD);
        }

        String encodedNewPassword = encoder.encode(newPassword);
        admin.setPassword(encodedNewPassword);
        admin.setUpdateBy(admin.getId().toString());
        adminUserService.updateById(admin);

        logger.info("【请求结束】系统管理->修改密码,响应结果:{}", "成功!");
        return ResponseUtil.ok();
    }
}
