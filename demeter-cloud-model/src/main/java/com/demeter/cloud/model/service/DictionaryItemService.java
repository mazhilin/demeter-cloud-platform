package com.demeter.cloud.model.service;

import com.demeter.cloud.model.entity.Dictionary;
import com.demeter.cloud.model.entity.DictionaryItem;

import java.util.List;

/**
 * <p>封装Dcloud项目DictionaryItemService类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-19 01:10
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
public interface DictionaryItemService {

    /**
     * 查询字典列表
     * @return 返回列表
     */
    List<DictionaryItem> queryDictionaryItemList();

    /**
     * 查询列表
     * @param name 名称
     * @param label
     * @param page 页码
     * @param limit 条数
     * @param sort 排序
     * @param order 排序
     * @return 返回列表
     */
    List<DictionaryItem> queryList(String name, String label,Integer page, Integer limit, String sort, String order);


    /**
     * 查询
     * @param id id
     * @return 返回文件信息
     */
    DictionaryItem queryById(Integer id);


    /**
     * 更新
     * @param dictionaryItem 信息
     * @return 返回文件信息
     */
    int update(DictionaryItem dictionaryItem);

    /**
     * 新增
     * @param dictionaryItem 文件信息
     */
    void add(DictionaryItem dictionaryItem);

    /**
     * 查询
     * @param dictionaryId 字典id
     * @param type 类型
     * @return 返回结果
     */
    List<DictionaryItem> findListByTypeAndId(Integer dictionaryId,String type);

    /**
     * 查询
     * @param dictionaryId 字典id
     * @return 返回结果
     */
    List<DictionaryItem> findListById(Integer dictionaryId);

    /**
     * 删除
     * @param id 文件索引
     */
    void deleteById(Integer id);

    /**
     * 删除
     * @param dictionaryItemList
     */
    void deleteAll(List<DictionaryItem> dictionaryItemList);
}
