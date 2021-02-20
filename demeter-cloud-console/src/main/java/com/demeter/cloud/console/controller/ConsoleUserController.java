package com.demeter.cloud.console.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.demeter.cloud.console.annotation.RequiresPermissionsDesc;
import com.demeter.cloud.console.utils.ConsoleWebResponseUtil;
import com.demeter.cloud.console.web.ConsoleWebResponse;
import com.demeter.cloud.core.constant.Constants;
import com.demeter.cloud.core.util.CheckEmptyUtil;
import com.demeter.cloud.core.util.ResponseUtil;
import com.demeter.cloud.core.util.bcrypt.BCryptPasswordEncoder;
import com.demeter.cloud.core.validator.Order;
import com.demeter.cloud.core.validator.Sort;
import com.demeter.cloud.model.entity.AdminUser;
import com.demeter.cloud.model.persistence.controller.BaseController;
import com.demeter.cloud.model.service.AdminUserService;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>封装Qicloud项目ConsoleUserController类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-16 22:59
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
@RestController
@RequestMapping(value = "/api/console/user/")
@Validated
public class ConsoleUserController extends BaseController {

    @Autowired
    private AdminUserService adminUserService;

    @RequiresPermissions("admin:user:list")
    @RequiresPermissionsDesc(menu = {"系统中心", "用户管理"}, button = "查询")
    @GetMapping(value = "list")
    public Object list(String username, @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "create_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {
        logger.info("【请求开始】系统中心->用户管理->查询,请求参数:username:{},page:{}", username, page);

        List<AdminUser> adminList = adminUserService.queryAdminUserList(username, page, limit, sort, order);
        long total = PageInfo.of(adminList).getTotal();
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", adminList);
        logger.info("【请求结束】系统中心->用户管理->查询,响应结果:{}", JSONObject.toJSONString(data));
        return ResponseUtil.ok(data);
    }

    private Object validate(AdminUser admin) {
        String account = admin.getAccount();
        if (StringUtils.isEmpty(account)) {
            return ResponseUtil.badArgument();
        }
        if (account.length() <= 6) {
            logger.error("校验错误：{}", ConsoleWebResponse.ADMIN_INVALID_ACCOUNT.message());
            return ConsoleWebResponseUtil.fail(ConsoleWebResponse.ADMIN_INVALID_ACCOUNT);
        }
        String username = admin.getName();
        if (StringUtils.isEmpty(username)) {
            return ResponseUtil.badArgument();
        }
        if (username.length() <= 1) {
            logger.error("校验错误：{}", ConsoleWebResponse.ADMIN_INVALID_NAME.message());
            return ConsoleWebResponseUtil.fail(ConsoleWebResponse.ADMIN_INVALID_NAME);
        }

        String password = admin.getPassword();
        if (StringUtils.isEmpty(password) || password.length() < 6) {
            logger.error("校验错误：{}", ConsoleWebResponse.ADMIN_INVALID_PASSWORD.message());
            return ConsoleWebResponseUtil.fail(ConsoleWebResponse.ADMIN_INVALID_PASSWORD);
        }
        return null;
    }

    @RequiresPermissions("admin:user:create")
    @RequiresPermissionsDesc(menu = {"系统中心", "用户管理"}, button = "添加")
    @PostMapping(value = "create")
    public Object create(@RequestBody AdminUser admin) {
        logger.info("【请求开始】系统中心->用户管理->添加,请求参数:{}", JSONObject.toJSONString(admin));

        Object error = validate(admin);
        if (error != null) {
            return error;
        }
        String account = admin.getAccount();
        List<AdminUser> adminList = adminUserService.queryAdminUserByAccount(account);
        if (adminList.size() > 0) {
            logger.error("系统中心->用户管理->添加 ,错误：{}", ConsoleWebResponse.ADMIN_NAME_EXIST.message());
            return ConsoleWebResponseUtil.fail(ConsoleWebResponse.ADMIN_NAME_EXIST);
        }
        String username = admin.getName();
        if (CheckEmptyUtil.isNotEmpty(username)) {
            adminList = adminUserService.queryAdminUserByUsername(username);
            if (adminList.size() > 0) {
                logger.error("系统中心->用户管理->添加 ,错误：{}", ConsoleWebResponse.ADMIN_NAME_EXIST.message());
                return ConsoleWebResponseUtil.fail(ConsoleWebResponse.ADMIN_NAME_EXIST);
            }
        }
        AdminUser adminUser = (AdminUser) SecurityUtils.getSubject().getPrincipal();
        logger.info("系统中心->用户管理->获取当前登录用户 ,结果：\n{}", JSON.toJSON(adminUser));
        String rawPassword = admin.getPassword();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(rawPassword);
        admin.setPassword(encodedPassword);
        admin.setType((byte) 0);
        admin.setNickname(CheckEmptyUtil.isNotEmpty(admin.getNickname()) ? admin.getNickname() : admin.getName());
        admin.setEmail(CheckEmptyUtil.isNotEmpty(admin.getEmail()) ? admin.getEmail() : adminUser.getAccount() + Constants.DEFAULT_EMAIL);
        admin.setCreateBy(adminUser.getId().toString());
        admin.setUpdateBy(adminUser.getId().toString());
        adminUserService.add(admin);

        logger.info("【请求结束】系统中心->用户管理->添加,响应结果:{}", JSONObject.toJSONString(admin));
        return ResponseUtil.ok(admin);
    }

    @RequiresPermissions("admin:user:edit")
    @RequiresPermissionsDesc(menu = {"系统中心", "用户管理"}, button = "详情")
    @GetMapping(value = "edit")
    public Object edit(@NotNull Integer id) {
        logger.info("【请求开始】系统中心->用户管理->详情,请求参数,id:{}", id);

        AdminUser admin = adminUserService.findById(id);

        logger.info("【请求结束】系统中心->用户管理->详情,响应结果:{}", JSONObject.toJSONString(admin));
        return ResponseUtil.ok(admin);
    }

    @RequiresPermissions("admin:user:update")
    @RequiresPermissionsDesc(menu = {"系统中心", "用户管理"}, button = "编辑")
    @PostMapping(value = "update")
    public Object update(@RequestBody AdminUser admin) {
        logger.info("【请求开始】系统中心->用户管理->编辑,请求参数:{}", JSONObject.toJSONString(admin));

        Object error = validate(admin);
        if (error != null) {
            return error;
        }

        Integer anotherAdminId = admin.getId();
        if (anotherAdminId == null) {
            return ResponseUtil.badArgument();
        }

        String rawPassword = admin.getPassword();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(rawPassword);
        admin.setPassword(encodedPassword);

        if (adminUserService.updateById(admin) == 0) {
            logger.error("系统中心->用户管理-编辑 ,错误：{}", "更新数据失败！");
            return ResponseUtil.updatedDataFailed();
        }

        logger.info("【请求结束】系统中心->用户管理->编辑,响应结果:{}", JSONObject.toJSONString(admin));
        return ResponseUtil.ok(admin);
    }

    @RequiresPermissions("admin:user:delete")
    @RequiresPermissionsDesc(menu = {"系统中心", "用户管理"}, button = "删除")
    @PostMapping(value = "delete")
    public Object delete(@RequestBody AdminUser admin) {
        logger.info("【请求开始】系统中心->用户管理->删除,请求参数:{}", JSONObject.toJSONString(admin));

        Integer anotherAdminId = admin.getId();
        if (anotherAdminId == null) {
            return ResponseUtil.badArgument();
        }

        adminUserService.deleteById(anotherAdminId);

        logger.info("【请求结束】系统中心->用户管理->删除 成功！");
        return ResponseUtil.ok();
    }

}
