package com.demeter.cloud.model.service;

import com.demeter.cloud.model.entity.RegionInfo;

import java.util.List;

/**
 * <p>封装Dcloud项目RegionInfoService类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-20 19:32
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
public interface RegionInfoService {

    /**
     * 查询区域列表
     *
     * @return 返回列表
     */
    List<RegionInfo> queryRegionList();

    /**
     * 查询省份列表-queryProvinceList
     *
     * @return 返回省份列表
     */
    List<RegionInfo> queryProvinceList();

    /**
     * 根据父级编码查询城市列表
     *
     * @param parentId 父级编码
     * @return 返回列表
     */
    List<RegionInfo> queryCityListByParentId(Integer parentId);

    /**
     * 根据父级编码查询区县列表
     *
     * @param parentId 父级编码
     * @return 返回列表
     */
    List<RegionInfo> queryDistrictListByParentId(Integer parentId);

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
    List<RegionInfo> queryList(String name, Integer code, Integer page, Integer limit, String sort, String order);


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
     *
     * @param code 文件索引
     * @return
     */
    RegionInfo queryByCode(Integer code);

    /**
     * 删除
     * @param id 文件索引
     */
    void deleteById(Integer id);
}
