package com.demeter.cloud.model.service;

import com.demeter.cloud.model.entity.RoleMenu;
import com.demeter.cloud.model.entity.UsageRecord;

import java.util.List;

/**
 * <p>封装Dcloud项目UsageRecordService类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-03-10 00:07
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
public interface UsageRecordService{
    /**
     * 查询活动列表
     *
     * @return 返回列表
     */
    List<UsageRecord> queryCategoryList();

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
    List<UsageRecord> queryList(String name, String code, Integer page, Integer limit, String sort, String order);


    /**
     * 查询
     *
     * @param id id
     * @return 返回文件信息
     */
    UsageRecord queryById(Integer id);


    /**
     * 更新
     *
     * @param usage 信息
     * @return 返回文件信息
     */
    int update(UsageRecord usage);

    /**
     * 新增
     *
     * @param usage 文件信息
     */
    void add(UsageRecord usage);


    /**
     * 删除
     *
     * @param id 文件索引
     */
    void deleteById(Integer id);
}
