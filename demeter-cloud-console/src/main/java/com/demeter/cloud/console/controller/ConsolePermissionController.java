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
import com.demeter.cloud.model.entity.PermissionInfo;
import com.demeter.cloud.model.persistence.controller.BaseController;
import com.demeter.cloud.model.service.PermissionInfoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>封装Qicloud项目ConsolePermissionController类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-17 03:09
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
@RestController
@RequestMapping(value = "/api/console/permission")
@Validated
public class ConsolePermissionController extends BaseController {
    @Autowired
    private PermissionInfoService permissionService;
    @Autowired
    private ApplicationContext context;
    private List<PermissionData> permissionData = null;
    private Set<String> permissionSet = null;

    private List<PermissionData> getSystemPermissions() {
        final String basicPackage = "com.mall.cloud.console";
        if (permissionData == null) {
            List<Permission> permissions = ConsolePermissionUtil.permissionList(context, basicPackage);
            permissionData = ConsolePermissionUtil.permissionDataList(permissions);
            permissionSet = ConsolePermissionUtil.permissionSet(permissions);
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
    @RequiresPermissions("admin:permission:list")
    @RequiresPermissionsDesc(menu = {"系统中心", "权限管理"}, button = "权限详情")
    @GetMapping(value = "list")
    public Object list(Integer roleId) {
        logger.info("【请求开始】系统中心->权限管理->权限详情,请求参数,roleId:{}", roleId);

        List<PermissionData> systemPermissions = getSystemPermissions();
        Set<String> assignedPermissions = getAssignedPermissions(roleId);

        Map<String, Object> data = new HashMap<>();
        data.put("systemPermissions", systemPermissions);
        data.put("assignedPermissions", assignedPermissions);

        logger.info("【请求结束】系统中心->权限管理->权限详情,响应结果:{}", JSONObject.toJSONString(data));
        return ResponseUtil.ok(data);
    }

    /**
     * 更新管理员的权限
     *
     * @param body
     * @return
     */
    @RequiresPermissions("admin:permission:update")
    @RequiresPermissionsDesc(menu = {"系统中心", "权限管理"}, button = "权限变更")
    @PostMapping(value = "update")
    public Object update(@RequestBody String body) {
        logger.info("【请求开始】系统中心->权限管理->权限变更,请求参数,body:{}", body);

        Integer roleId = JacksonUtil.parseInteger(body, "roleId");
        List<String> permissions = JacksonUtil.parseStringList(body, "permissions");
        if (roleId == null || permissions == null) {
            return ResponseUtil.badArgument();
        }

        // 如果修改的角色是超级权限，则拒绝修改。
        if (permissionService.checkSuperPermission(roleId)) {
            logger.error("系统中心->权限管理->权限变更 错误:{}", ConsoleWebResponse.ROLE_SUPER_SUPERMISSION.message());
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

        logger.info("【请求结束】系统中心->权限管理->权限变更,响应结果:{}", "成功!");
        return ResponseUtil.ok();
    }
}
