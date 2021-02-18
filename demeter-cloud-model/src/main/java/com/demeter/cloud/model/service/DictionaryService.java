package com.demeter.cloud.model.service;

import com.demeter.cloud.model.entity.Company;
import com.demeter.cloud.model.entity.Dictionary;

import java.util.List;

/**
 * <p>封装Qicloud项目DictionaryService类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-18 23:21
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
public interface DictionaryService {

    /**
     * 查询字典列表
     * @return 返回列表
     */
    List<Dictionary> queryDictionaryList();

    /**
     * 查询列表
     * @param name 名称
     * @param page 页码
     * @param limit 条数
     * @param sort 排序
     * @param order 排序
     * @return 返回列表
     */
    List<Dictionary> queryList(String name, String code,Integer page, Integer limit, String sort, String order);


    /**
     * 查询
     * @param id id
     * @return 返回文件信息
     */
    Dictionary queryById(Integer id);


    /**
     * 更新
     * @param dictionary 信息
     * @return 返回文件信息
     */
    int update(Dictionary dictionary);

    /**
     * 新增
     * @param dictionary 文件信息
     */
    void add(Dictionary dictionary);

    /**
     * 查询
     * @param code 文件索引
     * @return
     */
    Dictionary queryByCode(String code);

    /**
     * 删除
     * @param id 文件索引
     */
    void deleteById(Integer id);
}
