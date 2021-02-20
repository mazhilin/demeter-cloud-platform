package com.demeter.cloud.model.service;

import com.demeter.cloud.model.entity.PermissionInfo;

import java.util.Set;

/**
 * <p>封装Dcloud项目PermissionInfoService类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-17 01:45
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
public interface PermissionInfoService {

    /**
     * 查询角色列表
     *
     * @param roleIds 角色ids
     * @return 返回列表
     */
    Set<String> queryByRoleIds(Integer[] roleIds);

    /**
     * 查询角色列表
     *
     * @param roleId 角色id
     * @return 返回列表
     */
    Set<String> queryByRoleId(Integer roleId);

    /**
     * 检查权限
     *
     * @param roleId 角色id
     * @return 返回列表
     */
    boolean checkSuperPermission(Integer roleId);

    /**
     * 删除
     *
     * @param roleId 角色id
     * @return 返回列表
     */
    void deleteByRoleId(Integer roleId);

    /**
     * 新增
     *
     * @param permission 权限
     * @return 返回列表
     */
    void add(PermissionInfo permission);
}
