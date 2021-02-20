package com.demeter.cloud.model.service;


import com.demeter.cloud.model.entity.WorksInfo;

import java.util.List;

/**
 * <p>封装Dcloud项目WorksInfoService类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-19 01:38
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
public interface WorksInfoService {

    /**
     * 查询字典列表
     *
     * @return 返回列表
     */
    List<WorksInfo> queryCustomerUserList();

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
    List<WorksInfo> queryList(String name, String code, Integer page, Integer limit, String sort, String order);


    /**
     * 查询
     *
     * @param id id
     * @return 返回文件信息
     */
    WorksInfo queryById(Integer id);


    /**
     * 更新
     *
     * @param worksInfo 信息
     * @return 返回文件信息
     */
    int update(WorksInfo worksInfo);

    /**
     * 新增
     *
     * @param worksInfo 文件信息
     */
    void add(WorksInfo worksInfo);

    /**
     * 删除
     *
     * @param id 文件索引
     */
    void deleteById(Integer id);

    /**
     * 统计
     * @return 返回
     */
    int count();
}
