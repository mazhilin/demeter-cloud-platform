package com.demeter.cloud.model.service.impl;

import com.demeter.cloud.model.entity.OrderInfo;
import com.demeter.cloud.model.entity.OrderInfoExample;
import com.demeter.cloud.model.exception.BusinessException;
import com.demeter.cloud.model.mapper.OrderInfoMapper;
import com.demeter.cloud.model.service.OrderInfoService;
import com.demeter.cloud.persistence.service.BaseService;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>封装Dcloud项目OrderInfoServiceImpl类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-03-09 23:16
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
@Service("orderInfoService")
@Transactional(rollbackFor = {BusinessException.class, RuntimeException.class, Exception.class})
public class OrderInfoServiceImpl extends BaseService implements OrderInfoService {
    @Resource
    private OrderInfoMapper orderInfoMapper;

    /**
     * 查询列表
     *
     * @return 返回列表
     */
    @Override
    public List<OrderInfo> queryOrderList() {
        OrderInfoExample example = new OrderInfoExample();
        example.or().andIsDeleteEqualTo((byte) 0).andStatusEqualTo((byte) 1);
        return orderInfoMapper.selectByExample(example);
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
    public List<OrderInfo> queryList(String name, String code, Integer page, Integer limit, String sort, String order) {
        OrderInfoExample example = new OrderInfoExample();
        OrderInfoExample.Criteria criteria = example.createCriteria();

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
        return orderInfoMapper.selectByExample(example);
    }

    /**
     * 查询
     *
     * @param id id
     * @return 返回文件信息
     */
    @Override
    public OrderInfo queryById(Integer id) {
        return orderInfoMapper.selectByPrimaryKey(id.toString());
    }

    /**
     * 更新
     *
     * @param order 信息
     * @return 返回文件信息
     */
    @Override
    public int update(OrderInfo order) {
        order.setUpdateTime(LocalDateTime.now());
        return orderInfoMapper.updateByPrimaryKeySelective(order);
    }

    /**
     * 新增
     *
     * @param order 文件信息
     */
    @Override
    public void add(OrderInfo order) {
        order.setCreateTime(LocalDateTime.now());
        order.setUpdateTime(LocalDateTime.now());
        orderInfoMapper.insertSelective(order);
    }

    /**
     * 删除
     *
     * @param id 文件索引
     */
    @Override
    public void deleteById(Integer id) {
        orderInfoMapper.logicalDeleteByPrimaryKey(id.toString());
    }
}
