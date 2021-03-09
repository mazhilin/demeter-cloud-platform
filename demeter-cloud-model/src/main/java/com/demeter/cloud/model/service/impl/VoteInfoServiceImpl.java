package com.demeter.cloud.model.service.impl;

import com.demeter.cloud.model.entity.VoteInfo;
import com.demeter.cloud.model.entity.VoteInfoExample;
import com.demeter.cloud.model.exception.BusinessException;
import com.demeter.cloud.model.mapper.VoteInfoMapper;
import com.demeter.cloud.model.service.VoteInfoService;
import com.demeter.cloud.persistence.service.BaseService;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>封装Dcloud项目VoteInfoServiceImpl类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-03-10 00:12
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
@Service("menuInfoService")
@Transactional(rollbackFor = {BusinessException.class, RuntimeException.class, Exception.class})
public class VoteInfoServiceImpl extends BaseService implements VoteInfoService {

    @Resource
    private VoteInfoMapper voteInfoMapper;

    /**
     * 查询活动列表
     *
     * @return 返回列表
     */
    @Override
    public List<VoteInfo> queryCategoryList() {
        VoteInfoExample example = new VoteInfoExample();
        example.or().andIsDeleteEqualTo(false).andStatusEqualTo(true);
        return voteInfoMapper.selectByExample(example);
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
    public List<VoteInfo> queryList(String name, String code, Integer page, Integer limit, String sort, String order) {
        VoteInfoExample example = new VoteInfoExample();
        VoteInfoExample.Criteria criteria = example.createCriteria();

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
        return voteInfoMapper.selectByExample(example);
    }

    /**
     * 查询
     *
     * @param id id
     * @return 返回文件信息
     */
    @Override
    public VoteInfo queryById(Integer id) {
        return voteInfoMapper.selectByPrimaryKey(id);
    }

    /**
     * 更新
     *
     * @param vote 信息
     * @return 返回文件信息
     */
    @Override
    public int update(VoteInfo vote) {
        vote.setUpdateTime(LocalDateTime.now());
        return voteInfoMapper.updateByPrimaryKeySelective(vote);
    }

    /**
     * 新增
     *
     * @param vote 文件信息
     */
    @Override
    public void add(VoteInfo vote) {
        vote.setCreateTime(LocalDateTime.now());
        vote.setUpdateTime(LocalDateTime.now());
        voteInfoMapper.insertSelective(vote);
    }

    /**
     * 删除
     *
     * @param id 文件索引
     */
    @Override
    public void deleteById(Integer id) {
        voteInfoMapper.logicalDeleteByPrimaryKey(id);
    }
}
