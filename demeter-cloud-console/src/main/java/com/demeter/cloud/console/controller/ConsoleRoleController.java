package com.demeter.cloud.console.controller;

import com.alibaba.fastjson.JSONObject;
import com.demeter.cloud.console.annotation.RequiresPermissionsDesc;
import com.demeter.cloud.console.utils.ConsoleWebResponseUtil;
import com.demeter.cloud.console.web.ConsoleWebResponse;
import com.demeter.cloud.core.utils.ResponseUtil;
import com.demeter.cloud.core.validator.Order;
import com.demeter.cloud.core.validator.Sort;
import com.demeter.cloud.model.entity.RoleInfo;
import com.demeter.cloud.framework.persistence.controller.BaseController;
import com.demeter.cloud.model.service.RoleInfoService;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>封装Dcloud项目ConsoleRoleController类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-17 03:12
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
@RestController
@RequestMapping(value = "/admin/role/")
@Validated
public class ConsoleRoleController extends BaseController {

    @Autowired
    private RoleInfoService roleService;

    @RequiresPermissions("admin:role:list")
    @RequiresPermissionsDesc(menu = {"系统中心", "角色管理"}, button = "列表")
    @GetMapping(value = "list")
    public Object list(
            @RequestParam(name = "code",required = false) String code,
            @RequestParam(name = "name",required = false) String name,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer limit,
            @Sort @RequestParam(defaultValue = "create_time") String sort,
            @Order @RequestParam(defaultValue = "desc") String order) {
        logger.info("【请求开始】系统中心->角色管理->列表,请求参数,name:{},page:{}", name, page);
        List<RoleInfo> roleList = roleService.queryRoleList(code, name, page, limit, sort, order);
        long total = PageInfo.of(roleList).getTotal();
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", roleList);
        logger.info("【请求结束】系统中心->角色管理->列表,响应结果:{}", JSONObject.toJSONString(data));
        return ResponseUtil.ok(data);
    }

    @GetMapping(value = "options")
    public Object options() {
        List<RoleInfo> roleList = roleService.queryAll();
        logger.info("【请求开始】系统中心->角色管理->查询所有角色");

        List<Map<String, Object>> options = new ArrayList<>(roleList.size());
        for (RoleInfo role : roleList) {
            Map<String, Object> option = new HashMap<>(2);
            option.put("value", role.getId());
            option.put("label", role.getName());
            options.add(option);
        }

        logger.info("【请求结束】系统中心->角色管理->查询所有角色,响应结果:{}", JSONObject.toJSONString(options));
        return ResponseUtil.ok(options);
    }

    @RequiresPermissions("admin:role:edit")
    @RequiresPermissionsDesc(menu = {"系统中心", "角色管理"}, button = "编辑")
    @GetMapping(value = "edit")
    public Object edit(@RequestBody RoleInfo role) {
        logger.info("【请求开始】系统中心->角色管理->角色编辑,请求参数,role:{}", JSONObject.toJSONString(role));
        Object error = validate(role);
        if (error != null) {
            return error;
        }
        roleService.updateById(role);
        logger.info("【请求结束】系统中心->角色管理->角色编辑,响应结果:{}", "成功!");
        return ResponseUtil.ok();
    }

    @RequiresPermissions("admin:role:show")
    @RequiresPermissionsDesc(menu = {"系统中心", "角色管理"}, button = "详情")
    @GetMapping(value = "show")
    public Object show(@NotNull Integer id) {
        logger.info("【请求开始】系统中心->角色管理->详情,请求参数,id:{}", id);
        RoleInfo role = roleService.queryById(id);
        logger.info("【请求结束】系统中心->角色管理->详情,响应结果:{}", JSONObject.toJSONString(role));
        return ResponseUtil.ok(role);
    }

    private Object validate(RoleInfo role) {
        String name = role.getName();
        if (StringUtils.isEmpty(name)) {
            return ResponseUtil.badArgument();
        }

        return null;
    }

    @RequiresPermissions("admin:role:create")
    @RequiresPermissionsDesc(menu = {"系统中心", "角色管理"}, button = "新增")
    @PostMapping(value = "create")
    public Object create(@RequestBody RoleInfo role) {
        logger.info("【请求开始】系统中心->角色管理->新增,请求参数:{}", JSONObject.toJSONString(role));

        Object error = validate(role);
        if (error != null) {
            return error;
        }

        if (roleService.checkExist(role.getName())) {
            logger.info("系统中心->角色管理->新增 错误:{}", ConsoleWebResponse.ROLE_NAME_EXIST.message());
            return ConsoleWebResponseUtil.fail(ConsoleWebResponse.ROLE_NAME_EXIST);
        }

        roleService.add(role);

        logger.info("【请求结束】系统中心->角色管理->新增,响应结果:{}", JSONObject.toJSONString(role));
        return ResponseUtil.ok(role);
    }

    @RequiresPermissions("admin:role:update")
    @RequiresPermissionsDesc(menu = {"系统中心", "角色管理"}, button = "更新")
    @PostMapping(value = "update")
    public Object update(@RequestBody RoleInfo role) {
        logger.info("【请求开始】系统中心->角色管理->更新,请求参数:{}", JSONObject.toJSONString(role));
        Object error = validate(role);
        if (error != null) {
            return error;
        }
        roleService.updateById(role);
        logger.info("【请求结束】系统中心->角色管理->更新,响应结果:{}", "成功!");
        return ResponseUtil.ok();
    }

    @RequiresPermissions("admin:role:delete")
    @RequiresPermissionsDesc(menu = {"系统中心", "角色管理"}, button = "删除")
    @PostMapping(value = "delete")
    public Object delete(@RequestBody RoleInfo role) {
        logger.info("【请求开始】系统中心->角色管理->删除,请求参数,id:{}", JSONObject.toJSONString(role));

        Integer id = role.getId();
        if (id == null) {
            return ResponseUtil.badArgument();
        }
        roleService.deleteById(id);

        logger.info("【请求结束】系统中心->角色管理->删除,响应结果:{}", "成功!");
        return ResponseUtil.ok();
    }


}
