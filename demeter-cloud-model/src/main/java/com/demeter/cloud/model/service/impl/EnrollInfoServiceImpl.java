package com.demeter.cloud.model.service.impl;

import com.demeter.cloud.model.entity.*;
import com.demeter.cloud.model.exception.BusinessException;
import com.demeter.cloud.model.mapper.EnrollInfoMapper;
import com.demeter.cloud.persistence.service.BaseService;
import com.demeter.cloud.model.service.EnrollInfoService;
import com.demeter.cloud.utils.CheckEmptyUtil;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
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
    private EnrollInfoMapper enrollInfoMapper;
    /**
     * 查询报名列表
     *
     * @return 返回列表
     */
    @Override
    public List<EnrollInfo> queryEnrollList() {
        EnrollInfoExample example = new EnrollInfoExample();
        EnrollInfoExample.Criteria criteria = example.createCriteria();
        criteria.andIsDeleteEqualTo(false);
        criteria.andStatusEqualTo(true);
        return enrollInfoMapper.selectByExample(example);
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
        EnrollInfoExample example = new EnrollInfoExample();
        EnrollInfoExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(name)) {

        }
        if (!StringUtils.isEmpty(code)) {

        }
        criteria.andIsDeleteEqualTo(false);
        criteria.andStatusEqualTo(true);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return enrollInfoMapper.selectByExample(example);
    }

    /**
     * 查询
     *
     * @param id id
     * @return 返回文件信息
     */
    @Override
    public EnrollInfo queryById(Integer id) {
        return enrollInfoMapper.selectByPrimaryKey(id);
    }

    /**
     * 更新
     *
     * @param enroll 信息
     * @return 返回文件信息
     */
    @Override
    public int update(EnrollInfo enroll) {
        enroll.setUpdateTime(LocalDateTime.now());
        return enrollInfoMapper.updateByPrimaryKeySelective(enroll);
    }

    /**
     * 新增
     *
     * @param enroll 文件信息
     */
    @Override
    public void add(EnrollInfo enroll) {
        enrollInfoMapper.insert(enroll);
    }

    /**
     * 查询
     *
     * @param code 文件索引
     * @return
     */
    @Override
    public EnrollInfo queryByCode(String code) {
        EnrollInfoExample example = new EnrollInfoExample();
        EnrollInfoExample.Criteria criteria = example.createCriteria();
        criteria.andIsDeleteEqualTo(false);
        criteria.andStatusEqualTo(true);
        if (CheckEmptyUtil.isNotEmpty(code)) {
        }
        return enrollInfoMapper.selectOneByExample(example);
    }

    /**
     * 删除
     *
     * @param id 文件索引
     */
    @Override
    public void deleteById(Integer id) {
        enrollInfoMapper.logicalDeleteByPrimaryKey(id);
    }
}
