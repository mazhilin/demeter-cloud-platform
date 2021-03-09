package com.demeter.cloud.model.service.impl;

import com.demeter.cloud.model.entity.PublicParameterExample;
import com.demeter.cloud.model.entity.UsageRecord;
import com.demeter.cloud.model.entity.UsageRecordExample;
import com.demeter.cloud.model.exception.BusinessException;
import com.demeter.cloud.model.mapper.UsageRecordMapper;
import com.demeter.cloud.model.service.UsageRecordService;
import com.demeter.cloud.persistence.service.BaseService;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>封装Dcloud项目UsageRecordServiceImpl类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-03-10 00:09
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
@Service("usageRecordService")
@Transactional(rollbackFor = {BusinessException.class, RuntimeException.class, Exception.class})
public class UsageRecordServiceImpl extends BaseService implements UsageRecordService {
    @Resource
    private UsageRecordMapper usageRecordMapper;

    /**
     * 查询活动列表
     *
     * @return 返回列表
     */
    @Override
    public List<UsageRecord> queryCategoryList() {
        UsageRecordExample example = new UsageRecordExample();
        example.or().andIsDeleteEqualTo(false).andStatusEqualTo(true);
        return usageRecordMapper.selectByExample(example);
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
    public List<UsageRecord> queryList(String name, String code, Integer page, Integer limit, String sort, String order) {
        UsageRecordExample example = new UsageRecordExample();
        UsageRecordExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(name)) {

        }
        if (!StringUtils.isEmpty(code)) {
            criteria.andCodeEqualTo(code);
        }
        criteria.andIsDeleteEqualTo(false);
        criteria.andStatusEqualTo(true);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return usageRecordMapper.selectByExample(example);
    }

    /**
     * 查询
     *
     * @param id id
     * @return 返回文件信息
     */
    @Override
    public UsageRecord queryById(Integer id) {
        return usageRecordMapper.selectByPrimaryKey(id.toString());
    }

    /**
     * 更新
     *
     * @param usage 信息
     * @return 返回文件信息
     */
    @Override
    public int update(UsageRecord usage) {
        usage.setUpdateTime(LocalDateTime.now());
        return usageRecordMapper.updateByPrimaryKeySelective(usage);
    }

    /**
     * 新增
     *
     * @param usage 文件信息
     */
    @Override
    public void add(UsageRecord usage) {
        usage.setCreateTime(LocalDateTime.now());
        usage.setUpdateTime(LocalDateTime.now());
        usageRecordMapper.insertSelective(usage);
    }

    /**
     * 删除
     *
     * @param id 文件索引
     */
    @Override
    public void deleteById(Integer id) {
        usageRecordMapper.logicalDeleteByPrimaryKey(id.toString());
    }
}
