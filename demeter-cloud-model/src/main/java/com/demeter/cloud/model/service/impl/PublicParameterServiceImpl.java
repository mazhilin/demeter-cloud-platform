package com.demeter.cloud.model.service.impl;

import com.demeter.cloud.model.entity.PublicParameter;
import com.demeter.cloud.model.entity.PublicParameterExample;
import com.demeter.cloud.model.exception.BusinessException;
import com.demeter.cloud.model.mapper.PublicParameterMapper;
import com.demeter.cloud.framework.persistence.service.BaseService;
import com.demeter.cloud.model.service.PublicParameterService;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>封装Dcloud项目PublicParameterServiceImpl类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-20 00:24
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
@Service("publicParameterService")
@Transactional(rollbackFor = {BusinessException.class, RuntimeException.class, Exception.class})
public class PublicParameterServiceImpl extends BaseService implements PublicParameterService {
    @Resource
    private PublicParameterMapper publicParameterMapper;

    /**
     * 查询参数列表
     *
     * @return 返回列表
     */
    @Override
    public List<PublicParameter> queryPublicParameterList() {
        PublicParameterExample example = new PublicParameterExample();
        example.or();
        return publicParameterMapper.selectByExample(example);
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
    public List<PublicParameter> queryList(String name, String code, Integer page, Integer limit, String sort, String order) {
        PublicParameterExample example = new PublicParameterExample();
        PublicParameterExample.Criteria criteria = example.createCriteria();

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
        return publicParameterMapper.selectByExample(example);
    }

    /**
     * 查询
     *
     * @param id id
     * @return 返回文件信息
     */
    @Override
    public PublicParameter queryById(Integer id) {
        return publicParameterMapper.selectByPrimaryKey(id);
    }

    /**
     * 更新
     *
     * @param parameter 信息
     * @return 返回文件信息
     */
    @Override
    public int update(PublicParameter parameter) {
        parameter.setUpdateTime(LocalDateTime.now());
        return publicParameterMapper.updateByPrimaryKeySelective(parameter);
    }

    /**
     * 新增
     *
     * @param parameter 文件信息
     */
    @Override
    public void add(PublicParameter parameter) {
        parameter.setCreateTime(LocalDateTime.now());
        parameter.setUpdateTime(LocalDateTime.now());
        publicParameterMapper.insertSelective(parameter);
    }

    /**
     * 查询
     *
     * @param code 文件索引
     * @return
     */
    @Override
    public PublicParameter queryByCode(String code) {
        return publicParameterMapper.findByCode(code);
    }

    /**
     * 删除
     *
     * @param id 文件索引
     */
    @Override
    public void deleteById(Integer id) {
        publicParameterMapper.logicalDeleteByPrimaryKey(id);
    }
}
