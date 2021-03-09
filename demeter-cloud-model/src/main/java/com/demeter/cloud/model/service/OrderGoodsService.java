package com.demeter.cloud.model.service;

import com.demeter.cloud.model.entity.OrderGoods;

import java.util.List;

/**
 * <p>封装Dcloud项目OrderGoodsService类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-03-09 23:19
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
public interface OrderGoodsService {
    /**
     * 查询列表
     *
     * @return 返回列表
     */
    List<OrderGoods> queryOrderList();

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
    List<OrderGoods> queryList(String name, String code, Integer page, Integer limit, String sort, String order);


    /**
     * 查询
     *
     * @param id id
     * @return 返回文件信息
     */
    OrderGoods queryById(Integer id);


    /**
     * 更新
     *
     * @param orderGoods 信息
     * @return 返回文件信息
     */
    int update(OrderGoods orderGoods);

    /**
     * 新增
     *
     * @param orderGoods 文件信息
     */
    void add(OrderGoods orderGoods);


    /**
     * 删除
     *
     * @param id 文件索引
     */
    void deleteById(Integer id);
}
