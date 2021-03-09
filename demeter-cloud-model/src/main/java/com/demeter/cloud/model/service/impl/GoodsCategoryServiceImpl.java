package com.demeter.cloud.model.service.impl;

import com.demeter.cloud.model.entity.GoodsCategory;
import com.demeter.cloud.model.entity.GoodsCategoryExample;
import com.demeter.cloud.model.exception.BusinessException;
import com.demeter.cloud.model.mapper.GoodsCategoryMapper;
import com.demeter.cloud.model.service.GoodsCategoryService;
import com.demeter.cloud.persistence.service.BaseService;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>封装Dcloud项目GoodsCategoryServiceImpl类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-03-09 23:09
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
@Service("goodsCategoryService")
@Transactional(rollbackFor = {BusinessException.class, RuntimeException.class, Exception.class})
public class GoodsCategoryServiceImpl extends BaseService implements GoodsCategoryService {
    @Resource
    private GoodsCategoryMapper goodsCategoryMapper;
    /**
     * 查询活动列表
     *
     * @return 返回列表
     */
    @Override
    public List<GoodsCategory> queryCategoryList() {
        GoodsCategoryExample example = new GoodsCategoryExample();
        example.or().andIsDeleteEqualTo((byte) 0).andStatusEqualTo((byte) 1);
        return goodsCategoryMapper.selectByExample(example);
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
    public List<GoodsCategory> queryList(String name, String code, Integer page, Integer limit, String sort, String order) {
        GoodsCategoryExample example = new GoodsCategoryExample();
        GoodsCategoryExample.Criteria criteria = example.createCriteria();

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
        return goodsCategoryMapper.selectByExample(example);
    }

    /**
     * 查询
     *
     * @param id id
     * @return 返回文件信息
     */
    @Override
    public GoodsCategory queryById(Integer id) {
        return goodsCategoryMapper.selectByPrimaryKey(id);
    }

    /**
     * 更新
     *
     * @param category 信息
     * @return 返回文件信息
     */
    @Override
    public int update(GoodsCategory category) {
        category.setUpdateTime(LocalDateTime.now());
        return goodsCategoryMapper.updateByPrimaryKeySelective(category);
    }

    /**
     * 新增
     *
     * @param category 文件信息
     */
    @Override
    public void add(GoodsCategory category) {
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        goodsCategoryMapper.insertSelective(category);
    }

    /**
     * 删除
     *
     * @param id 文件索引
     */
    @Override
    public void deleteById(Integer id) {
        goodsCategoryMapper.logicalDeleteByPrimaryKey(id);
    }
}
