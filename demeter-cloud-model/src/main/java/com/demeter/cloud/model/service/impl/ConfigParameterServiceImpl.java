package com.demeter.cloud.model.service.impl;

import com.demeter.cloud.model.entity.ConfigParameter;
import com.demeter.cloud.model.entity.ConfigParameterExample;
import com.demeter.cloud.model.exception.BusinessException;
import com.demeter.cloud.model.mapper.ConfigParameterMapper;
import com.demeter.cloud.persistence.service.BaseService;
import com.demeter.cloud.model.service.ConfigParameterService;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>封装Dcloud项目ConfigParameterServiceImpl类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-19 23:21
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
@Service("configParameterService")
@Transactional(rollbackFor = {BusinessException.class, RuntimeException.class, Exception.class})
public class ConfigParameterServiceImpl extends BaseService implements ConfigParameterService {
    @Resource
    private ConfigParameterMapper configParameterMapper;

    /**
     * 查询参数列表
     *
     * @return 返回列表
     */
    @Override
    public List<ConfigParameter> queryConfigParameterList() {
        ConfigParameterExample example = new ConfigParameterExample();
        example.or();
        return configParameterMapper.selectByExample(example);
    }

    /**
     * 查询列表
     *
     * @param name  名称
     * @param code  编码
     * @param page  页码
     * @param limit 条数
     * @param sort  排序
     * @param order 排序
     * @return 返回列表
     */
    @Override
    public List<ConfigParameter> queryList(String name, String code, Integer page, Integer limit, String sort, String order) {
        ConfigParameterExample example = new ConfigParameterExample();
        ConfigParameterExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        if (!StringUtils.isEmpty(code)) {
            criteria.andCodeEqualTo(code);
        }
        criteria.andIsDeleteEqualTo((byte) 0);
        criteria.andStatusEqualTo((byte) 1);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return configParameterMapper.selectByExample(example);
    }

    /**
     * 查询
     *
     * @param id id
     * @return 返回文件信息
     */
    @Override
    public ConfigParameter queryById(Integer id) {
        return configParameterMapper.selectByPrimaryKey(id);
    }

    /**
     * 更新
     *
     * @param parameter 信息
     * @return 返回文件信息
     */
    @Override
    public int update(ConfigParameter parameter) {
        parameter.setUpdateTime(LocalDateTime.now());
        return configParameterMapper.updateByPrimaryKeySelective(parameter);
    }

    /**
     * 新增
     *
     * @param parameter 文件信息
     */
    @Override
    public void add(ConfigParameter parameter) {
        parameter.setCreateTime(LocalDateTime.now());
        parameter.setUpdateTime(LocalDateTime.now());
        configParameterMapper.insertSelective(parameter);
    }

    /**
     * 查询
     *
     * @param code 文件索引
     * @return
     */
    @Override
    public ConfigParameter queryByCode(String code) {
        return configParameterMapper.findByCode(code);
    }

    /**
     * 删除
     *
     * @param id 文件索引
     */
    @Override
    public void deleteById(Integer id) {
        configParameterMapper.logicalDeleteByPrimaryKey(id);
    }
}
