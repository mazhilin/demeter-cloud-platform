package com.demeter.cloud.model.service;

import com.demeter.cloud.model.entity.ActivityTemplate;

import java.util.List;

/**
 * <p>封装Dcloud项目ActivityTemplateService类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-20 19:35
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
public interface ActivityTemplateService {

    /**
     * 查询活动列表
     *
     * @return 返回列表
     */
    List<ActivityTemplate> queryActivityInfoList();

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
    List<ActivityTemplate> queryList(String name, String code, Integer page, Integer limit, String sort, String order);


    /**
     * 查询
     *
     * @param id id
     * @return 返回文件信息
     */
    ActivityTemplate queryById(Integer id);


    /**
     * 更新
     *
     * @param template 信息
     * @return 返回文件信息
     */
    int update(ActivityTemplate template);

    /**
     * 新增
     *
     * @param template 文件信息
     */
    void add(ActivityTemplate template);

    /**
     * 查询
     *
     * @param code 文件索引
     * @return
     */
    ActivityTemplate queryByCode(String code);

    /**
     * 删除
     *
     * @param id 文件索引
     */
    void deleteById(Integer id);

    /**
     * 删除
     *
     * @param  模板IDi
     */
    ActivityTemplate queryActivityTemplateById(int d);
}
