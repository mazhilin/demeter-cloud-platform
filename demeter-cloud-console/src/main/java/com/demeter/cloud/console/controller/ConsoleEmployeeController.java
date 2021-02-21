package com.demeter.cloud.console.controller;

import com.alibaba.fastjson.JSONObject;
import com.demeter.cloud.console.annotation.RequiresPermissionsDesc;
import com.demeter.cloud.console.utils.ConsoleWebResponseUtil;
import com.demeter.cloud.console.web.ConsoleWebResponse;
import com.demeter.cloud.core.util.CheckEmptyUtil;
import com.demeter.cloud.core.util.RegexUtil;
import com.demeter.cloud.core.util.ResponseUtil;
import com.demeter.cloud.core.util.bcrypt.BCryptPasswordEncoder;
import com.demeter.cloud.core.validator.Order;
import com.demeter.cloud.core.validator.Sort;
import com.demeter.cloud.model.entity.AdminUser;
import com.demeter.cloud.model.persistence.controller.BaseController;
import com.demeter.cloud.model.service.AdminUserService;
import com.github.pagehelper.PageInfo;
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
 * <p>封装Dcloud项目ConsoleEmployeeController类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-16 23:45
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
@RestController
@RequestMapping(value = "/admin/employee/")
@Validated
public class ConsoleEmployeeController extends BaseController {
    @Autowired
    private AdminUserService adminService;

    @RequiresPermissions("admin:employee:list")
    @RequiresPermissionsDesc(menu = {"企业中心", "员工管理"}, button = "列表")
    @GetMapping(value = "list")
    public Object list(String username, @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {
        logger.info("【请求开始】企业中心->员工管理->列表,请求参数:username:{},page:{}", username, page);

        List<AdminUser> adminList = adminService.queryEmployeeUserList(username, page, limit, sort, order);
        long total = PageInfo.of(adminList).getTotal();
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", adminList);

        logger.info("【请求结束】企业中心->员工管理->列表,响应结果:{}", JSONObject.toJSONString(data));
        return ResponseUtil.ok(data);
    }

    private Object validate(AdminUser admin) {
        String name = admin.getName();
        if (StringUtils.isEmpty(name)) {
            return ResponseUtil.badArgument();
        }
        if (!RegexUtil.isUsername(name)) {
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

    @RequiresPermissions("admin:employee:create")
    @RequiresPermissionsDesc(menu = {"企业中心", "员工管理"}, button = "新增")
    @PostMapping(value = "create")
    public Object create(@RequestBody AdminUser admin) {
        logger.info("【请求开始】企业中心->员工管理->新增,请求参数:{}", JSONObject.toJSONString(admin));

        Object error = validate(admin);
        if (error != null) {
            return error;
        }
        String account = admin.getAccount();
        List<AdminUser> adminList = adminService.queryAdminUserByAccount(account);
        if (adminList.size() > 0) {
            logger.error("企业中心->员工管理->新增 ,错误：{}", ConsoleWebResponse.ADMIN_NAME_EXIST.message());
            return ConsoleWebResponseUtil.fail(ConsoleWebResponse.ADMIN_NAME_EXIST);
        }
        String username = admin.getName();
        if (CheckEmptyUtil.isNotEmpty(username)) {
            adminList = adminService.queryAdminUserByUsername(username);
            if (adminList.size() > 0) {
                logger.error("企业中心->员工管理->新增 ,错误：{}", ConsoleWebResponse.ADMIN_NAME_EXIST.message());
                return ConsoleWebResponseUtil.fail(ConsoleWebResponse.ADMIN_NAME_EXIST);
            }
        }


        String rawPassword = admin.getPassword();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(rawPassword);
        admin.setPassword(encodedPassword);
        admin.setType((byte) 1);
        adminService.add(admin);

        logger.info("【请求结束】企业中心->员工管理->新增,响应结果:{}", JSONObject.toJSONString(admin));
        return ResponseUtil.ok(admin);
    }

    @RequiresPermissions("admin:employee:show")
    @RequiresPermissionsDesc(menu = {"企业中心", "员工管理"}, button = "详情")
    @GetMapping(value = "show")
    public Object show(@NotNull Integer id) {
        logger.info("【请求开始】企业中心->员工管理->详情,请求参数,id:{}", id);

        AdminUser admin = adminService.findById(id);

        logger.info("【请求结束】企业中心->员工管理->详情,响应结果:{}", JSONObject.toJSONString(admin));
        return ResponseUtil.ok(admin);
    }

    @RequiresPermissions("admin:employee:update")
    @RequiresPermissionsDesc(menu = {"企业中心", "员工管理"}, button = "编辑")
    @PostMapping(value = "edit")
    public Object edit(@RequestBody AdminUser admin) {
        logger.info("【请求开始】企业中心->员工管理->编辑,请求参数:{}", JSONObject.toJSONString(admin));

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

        if (adminService.updateById(admin) == 0) {
            logger.error("企业中心->员工管理-编辑 ,错误：{}", "更新数据失败！");
            return ResponseUtil.updatedDataFailed();
        }

        logger.info("【请求结束】企业中心->员工管理->编辑,响应结果:{}", JSONObject.toJSONString(admin));
        return ResponseUtil.ok(admin);
    }

    @RequiresPermissions("admin:employee:update")
    @RequiresPermissionsDesc(menu = {"企业中心", "员工管理"}, button = "更新")
    @PostMapping(value = "update")
    public Object update(@RequestBody AdminUser admin) {
        logger.info("【请求开始】企业中心->员工管理->更新,请求参数:{}", JSONObject.toJSONString(admin));

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

        if (adminService.updateById(admin) == 0) {
            logger.error("企业中心->员工管理-更新 ,错误：{}", "更新数据失败！");
            return ResponseUtil.updatedDataFailed();
        }

        logger.info("【请求结束】企业中心->员工管理->更新,响应结果:{}", JSONObject.toJSONString(admin));
        return ResponseUtil.ok(admin);
    }

    @RequiresPermissions("admin:employee:delete")
    @RequiresPermissionsDesc(menu = {"企业中心", "员工管理"}, button = "删除")
    @PostMapping(value = "delete")
    public Object delete(@RequestBody AdminUser admin) {
        logger.info("【请求开始】企业中心->员工管理->删除,请求参数:{}", JSONObject.toJSONString(admin));

        Integer anotherAdminId = admin.getId();
        if (anotherAdminId == null) {
            return ResponseUtil.badArgument();
        }

        adminService.deleteById(anotherAdminId);

        logger.info("【请求结束】企业中心->员工管理->删除 成功！");
        return ResponseUtil.ok();
    }
}
