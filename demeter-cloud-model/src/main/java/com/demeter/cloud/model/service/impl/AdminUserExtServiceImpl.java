package com.demeter.cloud.model.service.impl;

import com.demeter.cloud.model.entity.AdminUserExt;
import com.demeter.cloud.model.entity.AdminUserExtExample;
import com.demeter.cloud.model.exception.BusinessException;
import com.demeter.cloud.model.mapper.AdminUserExtMapper;
import com.demeter.cloud.model.service.AdminUserExtService;
import com.demeter.cloud.persistence.service.BaseService;
import com.demeter.cloud.utils.CheckEmptyUtil;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>封装Dcloud项目AdminUserExtServiceImpl类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-03-08 10:35
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
@Service("adminUserExtService")
@Transactional(rollbackFor = {BusinessException.class, RuntimeException.class, Exception.class})
public class AdminUserExtServiceImpl extends BaseService implements AdminUserExtService {

    @Resource
    private AdminUserExtMapper adminUserExtMapper;

    /**
     * 查询活动列表
     *
     * @return 返回列表
     */
    @Override
    public List<AdminUserExt> queryAdminUserExtList() {
        AdminUserExtExample example = new AdminUserExtExample();
        AdminUserExtExample.Criteria criteria = example.createCriteria();
        criteria.andIsDeleteEqualTo((byte) 0);
        criteria.andStatusEqualTo((byte) 1);
        return adminUserExtMapper.selectByExample(example);
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
    public List<AdminUserExt> queryList(String name, String code, Integer page, Integer limit, String sort, String order) {
        AdminUserExtExample example = new AdminUserExtExample();
        AdminUserExtExample.Criteria criteria = example.createCriteria();
        criteria.andIsDeleteEqualTo((byte) 0);
        criteria.andStatusEqualTo((byte) 1);
        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }
        PageHelper.startPage(page, limit);
        return adminUserExtMapper.selectByExample(example);
    }

    /**
     * 查询
     *
     * @param id id
     * @return 返回文件信息
     */
    @Override
    public AdminUserExt queryById(Integer id) {
        AdminUserExtExample example = new AdminUserExtExample();
        AdminUserExtExample.Criteria criteria = example.createCriteria();
        if (CheckEmptyUtil.isNotEmpty(id)) {
            criteria.andIdEqualTo(id);
        }
        criteria.andIsDeleteEqualTo((byte) 0);
        criteria.andStatusEqualTo((byte) 1);
        return adminUserExtMapper.selectOneByExample(example);
    }

    /**
     * 更新
     *
     * @param adminUserExt 信息
     * @return 返回文件信息
     */
    @Override
    public int update(AdminUserExt adminUserExt) {
        adminUserExt.setUpdateTime(LocalDateTime.now());
        return adminUserExtMapper.updateByPrimaryKeySelective(adminUserExt);
    }

    /**
     * 新增
     *
     * @param adminUserExt 文件信息
     */
    @Override
    public void add(AdminUserExt adminUserExt) {
        adminUserExt.setCreateTime(LocalDateTime.now());
        adminUserExt.setUpdateTime(LocalDateTime.now());
        adminUserExtMapper.insertSelective(adminUserExt);
    }


    /**
     * 删除
     *
     * @param id 文件索引
     */
    @Override
    public void deleteById(Integer id) {
        adminUserExtMapper.logicalDeleteByPrimaryKey(id);
    }
}
