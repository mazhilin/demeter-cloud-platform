package com.demeter.cloud.model.service.impl;

import com.demeter.cloud.model.entity.GoodsCategoryExample;
import com.demeter.cloud.model.entity.GoodsInfo;
import com.demeter.cloud.model.entity.GoodsInfoExample;
import com.demeter.cloud.model.exception.BusinessException;
import com.demeter.cloud.model.mapper.GoodsInfoMapper;
import com.demeter.cloud.model.service.GoodsInfoService;
import com.demeter.cloud.persistence.service.BaseService;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>封装Dcloud项目GoodsInfoServiceImpl类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-03-09 23:13
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
@Service("goodsInfoService")
@Transactional(rollbackFor = {BusinessException.class, RuntimeException.class, Exception.class})
public class GoodsInfoServiceImpl extends BaseService implements GoodsInfoService {

    @Resource
    private GoodsInfoMapper goodsInfoMapper;

    /**
     * 查询活动列表
     *
     * @return 返回列表
     */
    @Override
    public List<GoodsInfo> queryGoodsList() {
        GoodsInfoExample example = new GoodsInfoExample();
        example.or().andIsDeleteEqualTo((byte) 0).andStatusEqualTo((byte) 1);
        return goodsInfoMapper.selectByExample(example);
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
    public List<GoodsInfo> queryList(String name, String code, Integer page, Integer limit, String sort, String order) {
        GoodsInfoExample example = new GoodsInfoExample();
        GoodsInfoExample.Criteria criteria = example.createCriteria();

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
        return goodsInfoMapper.selectByExample(example);
    }

    /**
     * 查询
     *
     * @param id id
     * @return 返回文件信息
     */
    @Override
    public GoodsInfo queryById(Integer id) {
        return goodsInfoMapper.selectByPrimaryKey(id);
    }

    /**
     * 更新
     *
     * @param goods 信息
     * @return 返回文件信息
     */
    @Override
    public int update(GoodsInfo goods) {
        goods.setUpdateTime(LocalDateTime.now());
        return goodsInfoMapper.updateByPrimaryKeySelective(goods);
    }

    /**
     * 新增
     *
     * @param goods 文件信息
     */
    @Override
    public void add(GoodsInfo goods) {
        goods.setCreateTime(LocalDateTime.now());
        goods.setUpdateTime(LocalDateTime.now());
        goodsInfoMapper.insertSelective(goods);
    }

    /**
     * 删除
     *
     * @param id 文件索引
     */
    @Override
    public void deleteById(Integer id){
        goodsInfoMapper.logicalDeleteByPrimaryKey(id);
    }
}
