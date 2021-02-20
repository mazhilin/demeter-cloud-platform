package com.demeter.cloud.model.service.impl;

import com.demeter.cloud.model.entity.CustomerUser;
import com.demeter.cloud.model.entity.CustomerUserExample;
import com.demeter.cloud.model.exception.BusinessException;
import com.demeter.cloud.model.mapper.CustomerUserMapper;
import com.demeter.cloud.model.persistence.service.BaseService;
import com.demeter.cloud.model.service.CustomerUserService;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>封装Dcloud项目CustomerUserServiceImpl类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-19 01:26
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
@Service("customerUserService")
@Transactional(rollbackFor = {BusinessException.class, RuntimeException.class, Exception.class})
public class CustomerUserServiceImpl extends BaseService implements CustomerUserService {

    @Resource
    private CustomerUserMapper customerUserMapper;


    /**
     * 查询字典列表
     *
     * @return 返回列表
     */
    @Override
    public List<CustomerUser> queryCustomerUserList() {
        CustomerUserExample example = new CustomerUserExample();
        example.or().andIsDeleteEqualTo((byte) 0).andStatusEqualTo((byte) 1);
        return customerUserMapper.selectByExample(example);
    }

    /**
     * 查询列表
     *
     * @param name   名称
     * @param mobile
     * @param page   页码
     * @param limit  条数
     * @param sort   排序
     * @param order  排序
     * @return 返回列表
     */
    @Override
    public List<CustomerUser> queryList(String name, String mobile, Integer page, Integer limit, String sort, String order) {
        CustomerUserExample example = new CustomerUserExample();
        CustomerUserExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(name)) {
            criteria.andUsernameLike("%" + name + "%");
        }
        if (!StringUtils.isEmpty(mobile)) {
            criteria.andMobileEqualTo(mobile);
        }
        criteria.andIsDeleteEqualTo((byte) 0);
        criteria.andStatusEqualTo((byte) 1);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return customerUserMapper.selectByExample(example);
    }

    /**
     * 查询
     *
     * @param id id
     * @return 返回文件信息
     */
    @Override
    public CustomerUser queryById(Integer id) {
        return customerUserMapper.selectByPrimaryKey(id);
    }

    /**
     * 更新
     *
     * @param customer 信息
     * @return 返回文件信息
     */
    @Override
    public int update(CustomerUser customer) {
        customer.setUpdateTime(LocalDateTime.now());
        return customerUserMapper.updateByPrimaryKeySelective(customer);
    }

    /**
     * 新增
     *
     * @param customer 文件信息
     */
    @Override
    public void add(CustomerUser customer) {
        customer.setCreateTime(LocalDateTime.now());
        customer.setUpdateTime(LocalDateTime.now());
        customerUserMapper.insertSelective(customer);
    }

    /**
     * 删除
     *
     * @param id 文件索引
     */
    @Override
    public void deleteById(Integer id) {
        customerUserMapper.logicalDeleteByPrimaryKey(id);
    }

    /**
     * 统计
     *
     * @return 返回
     */
    @Override
    public int count() {
        CustomerUserExample example = new CustomerUserExample();
        example.or().andIsDeleteEqualTo((byte) 0).andStatusEqualTo((byte) 1);
        return (int) customerUserMapper.countByExample(example);
    }
}
