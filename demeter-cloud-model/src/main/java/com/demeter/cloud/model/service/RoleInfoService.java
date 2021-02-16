package com.demeter.cloud.model.service;

import com.demeter.cloud.model.entity.RoleInfo;

import java.util.List;
import java.util.Set;

/**
 * <p>封装Qicloud项目RoleInfoService类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-17 01:22
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
public interface RoleInfoService {

    /**
     * 查询角色list
     *
     * @param roleName 角色名称
     * @param page     页码
     * @param size     条目数
     * @param sort     排序
     * @param order    排序
     * @return 返回列表
     */
    List<RoleInfo> queryRoleList(String roleName, Integer page, Integer size, String sort, String order);

    /**
     * 查询
     *
     * @param roleIds 角色ids
     * @return 返回角色集合
     */
    Set<String> queryByIds(Integer[] roleIds);

    /**
     * 查询
     *
     * @param id 角色id
     * @return
     */
    RoleInfo queryById(Integer id);

    /**
     * 新增
     *
     * @param role 角色
     */
    void add(RoleInfo role);

    /**
     * 删除
     *
     * @param id 角色id
     */
    void deleteById(Integer id);

    /**
     * 更新
     *
     * @param role 角色
     */
    void updateById(RoleInfo role);

    /**
     * 校验
     *
     * @param name 角色
     */
    boolean checkExist(String name);

    /**
     * 查询角色
     *
     * @return 返回角色列表
     */
    List<RoleInfo> queryAll();

}
