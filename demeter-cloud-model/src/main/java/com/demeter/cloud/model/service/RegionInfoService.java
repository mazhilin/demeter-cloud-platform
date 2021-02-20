package com.demeter.cloud.model.service;

import com.demeter.cloud.model.entity.Dictionary;
import com.demeter.cloud.model.entity.RegionInfo;

import java.util.List;

/**
 * <p>封装Qicloud项目RegionInfoService类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-20 19:32
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
public interface RegionInfoService {

    /**
     * 查询区域列表
     * @return 返回列表
     */
    List<RegionInfo> queryRegionList();

    /**
     * 查询列表
     * @param name 名称
     * @param page 页码
     * @param limit 条数
     * @param sort 排序
     * @param order 排序
     * @return 返回列表
     */
    List<RegionInfo> queryList(String name, String code,Integer page, Integer limit, String sort, String order);


    /**
     * 查询
     * @param id id
     * @return 返回文件信息
     */
    RegionInfo queryById(Integer id);


    /**
     * 更新
     * @param region 信息
     * @return 返回文件信息
     */
    int update(RegionInfo region);

    /**
     * 新增
     * @param region 文件信息
     */
    void add(RegionInfo region);

    /**
     * 查询
     * @param code 文件索引
     * @return
     */
    RegionInfo queryByCode(String code);

    /**
     * 删除
     * @param id 文件索引
     */
    void deleteById(Integer id);
}
