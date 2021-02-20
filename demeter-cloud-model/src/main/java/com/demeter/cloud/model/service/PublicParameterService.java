package com.demeter.cloud.model.service;

import com.demeter.cloud.model.entity.PublicParameter;

import java.util.List;

/**
 * <p>封装Dcloud项目PublicParameterService类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-20 00:23
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
public interface PublicParameterService {

    /**
     * 查询参数列表
     *
     * @return 返回列表
     */
    List<PublicParameter> queryPublicParameterList();

    /**
     * 查询列表
     *
     * @param code  编码
     * @param name  名称
     * @param page  页码
     * @param limit 条数
     * @param sort  排序
     * @param order 排序
     * @return 返回列表
     */
    List<PublicParameter> queryList(String name, String code, Integer page, Integer limit, String sort, String order);


    /**
     * 查询
     *
     * @param id id
     * @return 返回文件信息
     */
    PublicParameter queryById(Integer id);


    /**
     * 更新
     *
     * @param parameter 信息
     * @return 返回文件信息
     */
    int update(PublicParameter parameter);

    /**
     * 新增
     *
     * @param parameter 文件信息
     */
    void add(PublicParameter parameter);

    /**
     * 查询
     *
     * @param code 文件索引
     * @return
     */
    PublicParameter queryByCode(String code);

    /**
     * 删除
     *
     * @param id 文件索引
     */
    void deleteById(Integer id);
}
