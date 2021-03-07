package com.demeter.cloud.model.service.impl;

import com.demeter.cloud.model.entity.WorksInfo;
import com.demeter.cloud.model.entity.WorksInfoExample;
import com.demeter.cloud.model.exception.BusinessException;
import com.demeter.cloud.model.mapper.WorksInfoMapper;
import com.demeter.cloud.framework.persistence.service.BaseService;
import com.demeter.cloud.model.service.WorksInfoService;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>封装Dcloud项目WorksInfoServiceImpl类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-19 01:39
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
@Service("worksInfoService")
@Transactional(rollbackFor = {BusinessException.class, RuntimeException.class, Exception.class})
public class WorksInfoServiceImpl extends BaseService implements WorksInfoService {

    @Resource
    private WorksInfoMapper worksInfoMapper;

    /**
     * 查询字典列表
     *
     * @return 返回列表
     */
    @Override
    public List<WorksInfo> queryCustomerUserList() {
        WorksInfoExample example = new WorksInfoExample();
        example.or().andIsDeleteEqualTo((byte) 0).andStatusEqualTo((byte) 1);
        return worksInfoMapper.selectByExample(example);
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
    public List<WorksInfo> queryList(String name, String code, Integer page, Integer limit, String sort, String order) {
        WorksInfoExample example = new WorksInfoExample();
        WorksInfoExample.Criteria criteria = example.createCriteria();

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
        return worksInfoMapper.selectByExample(example);
    }

    /**
     * 查询
     *
     * @param id id
     * @return 返回文件信息
     */
    @Override
    public WorksInfo queryById(Integer id) {
        return worksInfoMapper.selectByPrimaryKey(id);
    }

    /**
     * 更新
     *
     * @param worksInfo 信息
     * @return 返回文件信息
     */
    @Override
    public int update(WorksInfo worksInfo) {
        worksInfo.setUpdateTime(LocalDateTime.now());
        return worksInfoMapper.updateByPrimaryKeySelective(worksInfo);
    }

    /**
     * 新增
     *
     * @param worksInfo 文件信息
     */
    @Override
    public void add(WorksInfo worksInfo) {
        worksInfo.setCreateTime(LocalDateTime.now());
        worksInfo.setUpdateTime(LocalDateTime.now());
        worksInfoMapper.insertSelective(worksInfo);
    }

    /**
     * 删除
     *
     * @param id 文件索引
     */
    @Override
    public void deleteById(Integer id) {
        worksInfoMapper.logicalDeleteByPrimaryKey(id);
    }

    /**
     * 统计
     *
     * @return 返回
     */
    @Override
    public int count() {
        WorksInfoExample example = new WorksInfoExample();
        example.or().andIsDeleteEqualTo((byte) 0).andStatusEqualTo((byte) 1);
        return (int) worksInfoMapper.countByExample(example);
    }
}
