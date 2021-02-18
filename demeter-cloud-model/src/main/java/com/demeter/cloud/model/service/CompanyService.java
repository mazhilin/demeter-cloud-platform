package com.demeter.cloud.model.service;

import com.demeter.cloud.model.entity.Company;

import java.util.List;

/**
 * <p>封装Qicloud项目CompanyService类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-18 13:15
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
public interface CompanyService {

    /**
     * 查询列表
     * @param name 名称
     * @param page 页码
     * @param limit 条数
     * @param sort 排序
     * @param order 排序
     * @return 返回列表
     */
    List<Company> queryList(String name, Integer page, Integer limit, String sort, String order);


    /**
     * 查询
     * @param id id
     * @return 返回文件信息
     */
    Company queryById(Integer id);


    /**
     * 更新
     * @param company 信息
     * @return 返回文件信息
     */
    int update(Company company);

    /**
     * 新增
     * @param company 文件信息
     */
    void add(Company company);

    /**
     * 查询
     * @param code 文件索引
     * @return
     */
    Company queryByCode(String code);

    /**
     * 删除
     * @param id 文件索引
     */
    void deleteById(Integer id);
}
