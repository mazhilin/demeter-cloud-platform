package com.demeter.cloud.model.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.demeter.cloud.model.entity.RoleInfo;
import com.demeter.cloud.model.entity.RoleInfoExample;
import com.demeter.cloud.model.exception.BusinessException;
import com.demeter.cloud.model.mapper.RoleInfoMapper;
import com.demeter.cloud.model.service.RoleInfoService;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>封装Dcloud项目RoleInfoServiceImpl类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-17 01:32
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
@Service
@Transactional(rollbackFor = {BusinessException.class, RuntimeException.class, Exception.class})
public class RoleInfoServiceImpl implements RoleInfoService {

    @Resource
    private RoleInfoMapper roleInfoMapper;

    /**
     * 查询角色list
     *
     * @param code  角色编码
     * @param name  角色名称
     * @param page  页码
     * @param size  条目数
     * @param sort  排序
     * @param order 排序
     * @return 返回列表
     */
    @Override
    public List<RoleInfo> queryRoleList(String code,String name, Integer page, Integer size, String sort, String order) {
        RoleInfoExample example = new RoleInfoExample();
        RoleInfoExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(code)) {
            criteria.andCodeEqualTo(code);
        }
        if (!StringUtils.isEmpty(name)) {
            criteria.andNameEqualTo("%" + name + "%");
        }
        criteria.andIsDeleteEqualTo((byte) 0);
        criteria.andStatusEqualTo((byte) 1);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, size);
        return roleInfoMapper.selectByExample(example);
    }

    /**
     * 查询
     *
     * @param roleIds 角色ids
     * @return 返回角色集合
     */
    @Override
    public Set<String> queryByIds(Integer[] roleIds) {
        Set<String> roles = new HashSet<String>();
        if (roleIds.length == 0) {
            return roles;
        }

        RoleInfoExample example = new RoleInfoExample();
        example.or().andIdIn(Arrays.asList(roleIds)).andIsDeleteEqualTo((byte) 0).andStatusEqualTo((byte) 1);
        List<RoleInfo> roleList = roleInfoMapper.selectByExample(example);

        for (RoleInfo role : roleList) {
            roles.add(role.getName());
        }

        return roles;
    }

    /**
     * 查询
     *
     * @param id 角色id
     * @return
     */
    @Override
    public RoleInfo queryById(Integer id) {
        return roleInfoMapper.selectByPrimaryKey(id);
    }

    /**
     * 新增
     *
     * @param role 角色
     */
    @Override
    public void add(RoleInfo role) {
        role.setCreateTime(LocalDateTime.now());
        role.setUpdateTime(LocalDateTime.now());
        roleInfoMapper.insertSelective(role);
    }

    /**
     * 删除
     *
     * @param id 角色id
     */
    @Override
    public void deleteById(Integer id) {
        roleInfoMapper.logicalDeleteByPrimaryKey(id);
    }

    /**
     * 更新
     *
     * @param role 角色
     */
    @Override
    public void updateById(RoleInfo role) {
        role.setUpdateTime(LocalDateTime.now());
        roleInfoMapper.updateByPrimaryKeySelective(role);
    }

    /**
     * 校验
     *
     * @param name 角色
     */
    @Override
    public boolean checkExist(String name) {
        RoleInfoExample example = new RoleInfoExample();
        example.or().andNameEqualTo(name).andIsDeleteEqualTo((byte) 0).andStatusEqualTo((byte) 1);
        return roleInfoMapper.countByExample(example) != 0;
    }

    /**
     * 查询角色
     *
     * @return 返回角色列表
     */
    @Override
    public List<RoleInfo> queryAll() {
        RoleInfoExample example = new RoleInfoExample();
        example.or().andIsDeleteEqualTo((byte) 0).andStatusEqualTo((byte) 1);
        return roleInfoMapper.selectByExample(example);
    }
}
