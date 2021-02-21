package com.demeter.cloud.console.web;

import com.demeter.cloud.console.annotation.RequiresPermissionsDesc;
import org.apache.shiro.authz.annotation.RequiresPermissions;

/**
 * <p>封装Dcloud项目Permission类.<br></p>
 * <p>权限对象实体<br></p>
 *
 * @author Powered by marklin 2021-02-17 16:01
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
public class Permission {
    private RequiresPermissions requiresPermissions;
    private RequiresPermissionsDesc requiresPermissionsDesc;
    private String api;

    public RequiresPermissions getRequiresPermissions() {
        return requiresPermissions;
    }

    public RequiresPermissionsDesc getRequiresPermissionsDesc() {
        return requiresPermissionsDesc;
    }

    public void setRequiresPermissions(RequiresPermissions requiresPermissions) {
        this.requiresPermissions = requiresPermissions;
    }

    public void setRequiresPermissionsDesc(RequiresPermissionsDesc requiresPermissionsDesc) {
        this.requiresPermissionsDesc = requiresPermissionsDesc;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }
}
