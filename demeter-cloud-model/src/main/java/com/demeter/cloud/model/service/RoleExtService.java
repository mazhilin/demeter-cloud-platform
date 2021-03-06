package com.demeter.cloud.model.service;

import com.demeter.cloud.model.entity.RoleExt;

import java.util.List;

/**
 * <p>封装Dcloud项目RoleExtService类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-03-09 23:57
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
public interface RoleExtService {
    /**
     * 查询活动列表
     *
     * @return 返回列表
     */
    List<RoleExt> queryCategoryList();

    /**
     * 查询列表
     *
     * @param name  名称
     * @param page  页码
     * @param limit 条数
     * @param sort  排序
     * @param order 排序
     * @return 返回列表
     */
    List<RoleExt> queryList(String name, String code, Integer page, Integer limit, String sort, String order);


    /**
     * 查询
     *
     * @param id id
     * @return 返回文件信息
     */
    RoleExt queryById(Integer id);


    /**
     * 更新
     *
     * @param roleExt 信息
     * @return 返回文件信息
     */
    int update(RoleExt roleExt);

    /**
     * 新增
     *
     * @param roleExt 文件信息
     */
    void add(RoleExt roleExt);


    /**
     * 删除
     *
     * @param id 文件索引
     */
    void deleteById(Integer id);
}
