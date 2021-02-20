package com.demeter.cloud.model.service.impl;

import com.demeter.cloud.model.entity.RegionInfo;
import com.demeter.cloud.model.exception.BusinessException;
import com.demeter.cloud.model.persistence.service.BaseService;
import com.demeter.cloud.model.service.RegionInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>封装Qicloud项目RegionInfoServiceImpl类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-20 19:37
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
@Service("regionInfoService")
@Transactional(rollbackFor = {BusinessException.class, RuntimeException.class, Exception.class})
public class RegionInfoServiceImpl extends BaseService implements RegionInfoService {
    /**
     * 查询区域列表
     *
     * @return 返回列表
     */
    @Override
    public List<RegionInfo> queryRegionList() {
        return null;
    }

    /**
     * 查询列表
     *
     * @param name  名称
     * @param code
     * @param page  页码
     * @param limit 条数
     * @param sort  排序
     * @param order 排序
     * @return 返回列表
     */
    @Override
    public List<RegionInfo> queryList(String name, String code, Integer page, Integer limit, String sort, String order) {
        return null;
    }

    /**
     * 查询
     *
     * @param id id
     * @return 返回文件信息
     */
    @Override
    public RegionInfo queryById(Integer id) {
        return null;
    }

    /**
     * 更新
     *
     * @param region 信息
     * @return 返回文件信息
     */
    @Override
    public int update(RegionInfo region) {
        return 0;
    }

    /**
     * 新增
     *
     * @param region 文件信息
     */
    @Override
    public void add(RegionInfo region) {

    }

    /**
     * 查询
     *
     * @param code 文件索引
     * @return
     */
    @Override
    public RegionInfo queryByCode(String code) {
        return null;
    }

    /**
     * 删除
     *
     * @param id 文件索引
     */
    @Override
    public void deleteById(Integer id) {

    }
}
