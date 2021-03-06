package com.demeter.cloud.model.service;

import com.demeter.cloud.model.entity.EnrollInfo;

import java.util.List;

/**
 * <p>封装Dcloud项目EnrollInfoService类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-23 19:42
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
public interface EnrollInfoService {

    /**
     * 查询报名列表
     *
     * @return 返回列表
     */
    List<EnrollInfo> queryEnrollList();

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
    List<EnrollInfo> queryList(String name, String code, Integer page, Integer limit, String sort, String order);


    /**
     * 查询
     *
     * @param id id
     * @return 返回文件信息
     */
    EnrollInfo queryById(Integer id);


    /**
     * 更新
     *
     * @param enroll 信息
     * @return 返回文件信息
     */
    int update(EnrollInfo enroll);

    /**
     * 新增
     *
     * @param enroll 文件信息
     */
    void add(EnrollInfo enroll);

    /**
     * 查询
     *
     * @param code 文件索引
     * @return
     */
    EnrollInfo queryByCode(String code);

    /**
     * 删除
     *
     * @param id 文件索引
     */
    void deleteById(Integer id);
}
