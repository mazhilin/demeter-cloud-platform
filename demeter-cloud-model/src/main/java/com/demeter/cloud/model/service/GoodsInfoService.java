package com.demeter.cloud.model.service;

import com.demeter.cloud.model.entity.GoodsCategory;
import com.demeter.cloud.model.entity.GoodsInfo;

import java.util.List;

/**
 * <p>封装Dcloud项目GoodsInfoService类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-03-09 23:11
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
public interface GoodsInfoService {
    /**
     * 查询活动列表
     *
     * @return 返回列表
     */
    List<GoodsInfo> queryGoodsList();

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
    List<GoodsInfo> queryList(String name, String code, Integer page, Integer limit, String sort, String order);


    /**
     * 查询
     *
     * @param id id
     * @return 返回文件信息
     */
    GoodsInfo queryById(Integer id);


    /**
     * 更新
     *
     * @param goods 信息
     * @return 返回文件信息
     */
    int update(GoodsInfo goods);

    /**
     * 新增
     *
     * @param goods 文件信息
     */
    void add(GoodsInfo goods);


    /**
     * 删除
     *
     * @param id 文件索引
     */
    void deleteById(Integer id);
}
