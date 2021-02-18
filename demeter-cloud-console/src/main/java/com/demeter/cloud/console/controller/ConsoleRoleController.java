package com.demeter.cloud.console.controller;

import com.alibaba.fastjson.JSONObject;
import com.demeter.cloud.console.annotation.RequiresPermissionsDesc;
import com.demeter.cloud.console.utils.ConsolePermissionUtil;
import com.demeter.cloud.console.utils.ConsoleWebResponseUtil;
import com.demeter.cloud.console.web.ConsoleWebResponse;
import com.demeter.cloud.console.web.Permission;
import com.demeter.cloud.console.web.PermissionData;
import com.demeter.cloud.core.util.JacksonUtil;
import com.demeter.cloud.core.util.ResponseUtil;
import com.demeter.cloud.core.validator.Order;
import com.demeter.cloud.core.validator.Sort;
import com.demeter.cloud.model.entity.PermissionInfo;
import com.demeter.cloud.model.entity.RoleInfo;
import com.demeter.cloud.model.persistence.controller.BaseController;
import com.demeter.cloud.model.service.PermissionInfoService;
import com.demeter.cloud.model.service.RoleInfoService;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.*;

/**
 * <p>封装Qicloud项目ConsoleRoleController类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-17 03:12
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
@RestController
@RequestMapping(value = "/api/console/role/")
@Validated
public class ConsoleRoleController extends BaseController {

    @Autowired
    private RoleInfoService roleService;
    @Autowired
    private PermissionInfoService permissionService;
    @Autowired
    private ApplicationContext context;
    private List<PermissionData> permissionData = null;
    private Set<String> permissionSet = null;

    @RequiresPermissions("admin:role:list")
    @RequiresPermissionsDesc(menu = {"系统中心", "角色管理"}, button = "角色查询")
    @GetMapping(value = "list")
    public Object list(
            @RequestParam(name = "code",required = false) String code,
            @RequestParam(name = "name",required = false) String name,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer limit,
            @Sort @RequestParam(defaultValue = "create_time") String sort,
            @Order @RequestParam(defaultValue = "desc") String order) {
        logger.info("【请求开始】系统中心->角色管理->角色查询,请求参数,name:{},page:{}", name, page);
        List<RoleInfo> roleList = roleService.queryRoleList(code, name, page, limit, sort, order);
        long total = PageInfo.of(roleList).getTotal();
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", roleList);
        logger.info("【请求结束】系统中心->角色管理->角色查询,响应结果:{}", JSONObject.toJSONString(data));
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
    @RequiresPermissionsDesc(menu = {"系统中心", "角色管理"}, button = "角色编辑")
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
    @RequiresPermissionsDesc(menu = {"系统中心", "角色管理"}, button = "角色详情")
    @GetMapping(value = "show")
    public Object show(@NotNull Integer id) {
        logger.info("【请求开始】系统中心->角色管理->角色详情,请求参数,id:{}", id);
        RoleInfo role = roleService.queryById(id);
        logger.info("【请求结束】系统中心->角色管理->角色详情,响应结果:{}", JSONObject.toJSONString(role));
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
    @RequiresPermissionsDesc(menu = {"系统中心", "角色管理"}, button = "角色添加")
    @PostMapping(value = "create")
    public Object create(@RequestBody RoleInfo role) {
        logger.info("【请求开始】系统中心->角色管理->角色添加,请求参数:{}", JSONObject.toJSONString(role));

        Object error = validate(role);
        if (error != null) {
            return error;
        }

        if (roleService.checkExist(role.getName())) {
            logger.info("系统中心->角色管理->角色添加错误:{}", ConsoleWebResponse.ROLE_NAME_EXIST.message());
            return ConsoleWebResponseUtil.fail(ConsoleWebResponse.ROLE_NAME_EXIST);
        }

        roleService.add(role);

        logger.info("【请求结束】系统中心->角色管理->角色添加,响应结果:{}", JSONObject.toJSONString(role));
        return ResponseUtil.ok(role);
    }

    @RequiresPermissions("admin:role:update")
    @RequiresPermissionsDesc(menu = {"系统中心", "角色管理"}, button = "角色更新")
    @PostMapping(value = "update")
    public Object update(@RequestBody RoleInfo role) {
        logger.info("【请求开始】系统中心->角色管理->角色更新,请求参数:{}", JSONObject.toJSONString(role));
        Object error = validate(role);
        if (error != null) {
            return error;
        }
        roleService.updateById(role);
        logger.info("【请求结束】系统中心->角色管理->角色更新,响应结果:{}", "成功!");
        return ResponseUtil.ok();
    }

    @RequiresPermissions("admin:role:delete")
    @RequiresPermissionsDesc(menu = {"系统中心", "角色管理"}, button = "角色删除")
    @PostMapping(value = "delete")
    public Object delete(@RequestBody RoleInfo role) {
        logger.info("【请求开始】系统中心->角色管理->角色删除,请求参数,id:{}", JSONObject.toJSONString(role));

        Integer id = role.getId();
        if (id == null) {
            return ResponseUtil.badArgument();
        }
        roleService.deleteById(id);

        logger.info("【请求结束】系统中心->角色管理->角色删除,响应结果:{}", "成功!");
        return ResponseUtil.ok();
    }

    private List<PermissionData> getSystemPermissions() {
        final String basicPackage = "com.mall.cloud.console";
        if (permissionData == null) {
            List<Permission> permissions = ConsolePermissionUtil.listPermission(context, basicPackage);
            permissionData = ConsolePermissionUtil.listPermVo(permissions);
            permissionSet = ConsolePermissionUtil.listPermissionString(permissions);
        }
        return permissionData;
    }

    private Set<String> getAssignedPermissions(Integer roleId) {
        // 这里需要注意的是，如果存在超级权限*，那么这里需要转化成当前所有系统权限。
        // 之所以这么做，是因为前端不能识别超级权限，所以这里需要转换一下。
        Set<String> assignedPermissions = null;
        if (permissionService.checkSuperPermission(roleId)) {
            getSystemPermissions();
            assignedPermissions = permissionSet;
        } else {
            assignedPermissions = permissionService.queryByRoleId(roleId);
        }

        return assignedPermissions;
    }

    /**
     * 管理员的权限情况
     *
     * @return 系统所有权限列表和管理员已分配权限
     */
    @RequiresPermissions("admin:role:permission:get")
    @RequiresPermissionsDesc(menu = {"系统中心", "角色管理"}, button = "权限详情")
    @GetMapping(value = "permissions")
    public Object getPermissions(Integer roleId) {
        logger.info("【请求开始】系统中心->角色管理->权限详情,请求参数,roleId:{}", roleId);

        List<PermissionData> systemPermissions = getSystemPermissions();
        Set<String> assignedPermissions = getAssignedPermissions(roleId);

        Map<String, Object> data = new HashMap<>();
        data.put("systemPermissions", systemPermissions);
        data.put("assignedPermissions", assignedPermissions);

        logger.info("【请求结束】系统中心->角色管理->权限详情,响应结果:{}", JSONObject.toJSONString(data));
        return ResponseUtil.ok(data);
    }

    /**
     * 更新管理员的权限
     *
     * @param body
     * @return
     */
    @RequiresPermissions("admin:role:permission:update")
    @RequiresPermissionsDesc(menu = {"系统中心", "角色管理"}, button = "权限变更")
    @PostMapping(value = "permissions")
    public Object updatePermissions(@RequestBody String body) {
        logger.info("【请求开始】系统中心->角色管理->权限变更,请求参数,body:{}", body);

        Integer roleId = JacksonUtil.parseInteger(body, "roleId");
        List<String> permissions = JacksonUtil.parseStringList(body, "permissions");
        if (roleId == null || permissions == null) {
            return ResponseUtil.badArgument();
        }

        // 如果修改的角色是超级权限，则拒绝修改。
        if (permissionService.checkSuperPermission(roleId)) {
            logger.error("系统中心->角色管理->权限变更 错误:{}", ConsoleWebResponse.ROLE_SUPER_SUPERMISSION.message());
            return ConsoleWebResponseUtil.fail(ConsoleWebResponse.ROLE_SUPER_SUPERMISSION);
        }

        // 先删除旧的权限，再更新新的权限
        permissionService.deleteByRoleId(roleId);
        for (String permission : permissions) {
            PermissionInfo item = new PermissionInfo();
            item.setRoleId(roleId);
            item.setPermission(permission);
            permissionService.add(item);
        }

        logger.info("【请求结束】系统中心->角色管理->权限变更,响应结果:{}", "成功!");
        return ResponseUtil.ok();
    }

}
