package com.demeter.cloud.model.service.impl;

import com.demeter.cloud.model.entity.GoodsInfoExample;
import com.demeter.cloud.model.entity.LikeInfo;
import com.demeter.cloud.model.entity.LikeInfoExample;
import com.demeter.cloud.model.exception.BusinessException;
import com.demeter.cloud.model.mapper.LikeInfoMapper;
import com.demeter.cloud.model.service.LikeInfoService;
import com.demeter.cloud.persistence.service.BaseService;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>封装Dcloud项目LikeInfoServiceImpl类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-03-10 00:05
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
@Service("likeInfoService")
@Transactional(rollbackFor = {BusinessException.class, RuntimeException.class, Exception.class})
public class LikeInfoServiceImpl extends BaseService implements LikeInfoService {
    @Resource
    private LikeInfoMapper likeInfoMapper;

    /**
     * 查询活动列表
     *
     * @return 返回列表
     */
    @Override
    public List<LikeInfo> queryCategoryList() {
        LikeInfoExample example = new LikeInfoExample();
        example.or().andIsDeleteEqualTo((byte) 0).andStatusEqualTo((byte) 1);
        return likeInfoMapper.selectByExample(example);
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
    public List<LikeInfo> queryList(String name, String code, Integer page, Integer limit, String sort, String order) {
        LikeInfoExample example = new LikeInfoExample();
        LikeInfoExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(name)) {

        }
        if (!StringUtils.isEmpty(code)) {

        }
        criteria.andIsDeleteEqualTo((byte) 0);
        criteria.andStatusEqualTo((byte) 1);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return likeInfoMapper.selectByExample(example);
    }

    /**
     * 查询
     *
     * @param id id
     * @return 返回文件信息
     */
    @Override
    public LikeInfo queryById(Integer id) {
        return likeInfoMapper.selectByPrimaryKey(id);
    }

    /**
     * 更新
     *
     * @param like 信息
     * @return 返回文件信息
     */
    @Override
    public int update(LikeInfo like) {
        like.setUpdateTime(LocalDateTime.now());
        return likeInfoMapper.updateByPrimaryKeySelective(like);
    }

    /**
     * 新增
     *
     * @param like 文件信息
     */
    @Override
    public void add(LikeInfo like) {
        like.setCreateTime(LocalDateTime.now());
        like.setUpdateTime(LocalDateTime.now());
        likeInfoMapper.insertSelective(like);
    }

    /**
     * 删除
     *
     * @param id 文件索引
     */
    @Override
    public void deleteById(Integer id) {
        likeInfoMapper.logicalDeleteByPrimaryKey(id);
    }
}
