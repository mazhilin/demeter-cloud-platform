package com.demeter.cloud.model.service.impl;

import com.demeter.cloud.model.entity.PermissionInfo;
import com.demeter.cloud.model.entity.PermissionInfoExample;
import com.demeter.cloud.model.exception.BusinessException;
import com.demeter.cloud.model.mapper.PermissionInfoMapper;
import com.demeter.cloud.persistence.service.BaseService;
import com.demeter.cloud.model.service.PermissionInfoService;
import com.google.common.collect.Sets;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>封装Dcloud项目PermissionInfoServiceImpl类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-17 01:50
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
@Service
@Transactional(rollbackFor = {BusinessException.class, RuntimeException.class, Exception.class})
public class PermissionInfoServiceImpl extends BaseService implements PermissionInfoService {
    @Resource
    private PermissionInfoMapper permissionInfoMapper;

    /**
     * 查询角色列表
     *
     * @param roleIds 角色ids
     * @return 返回列表
     */
    @Override
    public Set<String> queryByRoleIds(Integer[] roleIds) {
        Set<String> permissions = Sets.newConcurrentHashSet();
        if (roleIds.length == 0) {
            return permissions;
        }

        PermissionInfoExample example = new PermissionInfoExample();
        example.or().andRoleIdIn(Arrays.asList(roleIds)).andIsDeleteEqualTo((byte) 0).andStatusEqualTo((byte) 1);
        List<PermissionInfo> permissionList = permissionInfoMapper.selectByExample(example);

        for (PermissionInfo permission : permissionList) {
            permissions.add(permission.getPermission());
        }

        return permissions;
    }

    /**
     * 查询角色列表
     *
     * @param roleId 角色id
     * @return 返回列表
     */
    @Override
    public Set<String> queryByRoleId(Integer roleId) {
        Set<String> permissions = new HashSet<String>();
        if (roleId == null) {
            return permissions;
        }

        PermissionInfoExample example = new PermissionInfoExample();
        example.or().andRoleIdEqualTo(roleId).andIsDeleteEqualTo((byte) 0).andStatusEqualTo((byte) 1);
        List<PermissionInfo> permissionList = permissionInfoMapper.selectByExample(example);

        for (PermissionInfo permission : permissionList) {
            permissions.add(permission.getPermission());
        }

        return permissions;
    }

    /**
     * 检查权限
     *
     * @param roleId 角色id
     * @return 返回列表
     */
    @Override
    public boolean checkSuperPermission(Integer roleId) {
        if (roleId == null) {
            return false;
        }

        PermissionInfoExample example = new PermissionInfoExample();
        example.or().andRoleIdEqualTo(roleId).andPermissionEqualTo("*").andIsDeleteEqualTo((byte) 0).andStatusEqualTo((byte) 1);
        return permissionInfoMapper.countByExample(example) != 0;
    }

    /**
     * 删除
     *
     * @param roleId 角色id
     * @return 返回列表
     */
    @Override
    public void deleteByRoleId(Integer roleId) {
        PermissionInfoExample example = new PermissionInfoExample();
        example.or().andRoleIdEqualTo(roleId).andIsDeleteEqualTo((byte) 0).andStatusEqualTo((byte) 1);
        permissionInfoMapper.logicalDeleteByExample(example);
    }

    /**
     * 新增
     *
     * @param permission 权限
     * @return 返回列表
     */
    @Override
    public void add(PermissionInfo permission) {
        permission.setCreateTime(LocalDateTime.now());
        permission.setUpdateTime(LocalDateTime.now());
        permissionInfoMapper.insertSelective(permission);
    }
}
