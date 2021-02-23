package com.demeter.cloud.model.service.impl;

import com.demeter.cloud.model.entity.EnrollInfo;
import com.demeter.cloud.model.exception.BusinessException;
import com.demeter.cloud.model.persistence.service.BaseService;
import com.demeter.cloud.model.service.EnrollInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>封装Dcloud项目EnrollInfoServiceImpl类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-23 19:44
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
@Service("enrollInfoService")
@Transactional(rollbackFor = {BusinessException.class, RuntimeException.class, Exception.class})
public class EnrollInfoServiceImpl extends BaseService implements EnrollInfoService {

    @Resource
    private EnrollInfoService enrollInfoService;

    /**
     * 查询报名列表
     *
     * @return 返回列表
     */
    @Override
    public List<EnrollInfo> queryEnrollList() {
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
    public List<EnrollInfo> queryList(String name, String code, Integer page, Integer limit, String sort, String order) {
        return null;
    }

    /**
     * 查询
     *
     * @param id id
     * @return 返回文件信息
     */
    @Override
    public EnrollInfo queryById(Integer id) {
        return null;
    }

    /**
     * 更新
     *
     * @param enroll 信息
     * @return 返回文件信息
     */
    @Override
    public int update(EnrollInfo enroll) {
        return 0;
    }

    /**
     * 新增
     *
     * @param enroll 文件信息
     */
    @Override
    public void add(EnrollInfo enroll) {

    }

    /**
     * 查询
     *
     * @param code 文件索引
     * @return
     */
    @Override
    public EnrollInfo queryByCode(String code) {
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
