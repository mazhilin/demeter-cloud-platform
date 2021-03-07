package com.demeter.cloud.model.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.demeter.cloud.model.entity.AdminUser;
import com.demeter.cloud.model.entity.AdminUserExample;
import com.demeter.cloud.model.exception.BusinessException;
import com.demeter.cloud.model.mapper.AdminUserMapper;
import com.demeter.cloud.persistence.service.BaseService;
import com.demeter.cloud.model.service.AdminUserService;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * <p>封装Dcloud项目AdminUserServiceImpl类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-16 22:32
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
@Service("adminUserService")
@Transactional(rollbackFor = {BusinessException.class, RuntimeException.class, Exception.class})
public class AdminUserServiceImpl extends BaseService implements AdminUserService {
    private final AdminUser.Column[] result = new AdminUser.Column[]{AdminUser.Column.id, AdminUser.Column.account, AdminUser.Column.name,AdminUser.Column.mobile, AdminUser.Column.type, AdminUser.Column.profilePicture, AdminUser.Column.roleIds, AdminUser.Column.status,AdminUser.Column.lastLoginIp, AdminUser.Column.lastLoginTime};

    @Resource
    private AdminUserMapper adminUserMapper;

    /**
     * 依据账户查询用户信息
     *
     * @param account 账户
     * @return 返回用户信息
     */
    @Override
    public List<AdminUser> queryAdminUserByAccount(String account) {
        logger.info("[begin to db]::数据库-查询用户信息，请求参数:账户{}", account);
        AdminUserExample example = new AdminUserExample();
        example.or().andAccountEqualTo(account);
        List<AdminUser> userList = adminUserMapper.selectByExample(example);
        logger.info("[end   to db]::数据库-查询用户信息，返回结果: \n{}", JSONObject.toJSONString(userList));
        return Objects.requireNonNull(userList);
    }

    /**
     * 依据用户名查询用户信息
     *
     * @param username 姓名
     * @return 返回用户信息
     */
    @Override
    public List<AdminUser> queryAdminUserByUsername(String username) {
        AdminUserExample example = new AdminUserExample();
        example.or().andNameEqualTo(username).andIsDeleteEqualTo((byte) 0).andStatusEqualTo((byte) 1);
        return adminUserMapper.selectByExample(example);
    }

    /**
     * 依据用户id查询用户信息
     *
     * @param id 用户id
     * @return 返回用户信息
     */
    @Override
    public AdminUser queryAdminUserById(Integer id) {
        return adminUserMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询用户列表
     *
     * @param username 用户名
     * @param page     页码数
     * @param limit    条目数
     * @param sort     排序
     * @param order    排序
     * @return 返回用户信息
     */
    @Override
    public List<AdminUser> queryAdminUserList(String username, Integer page, Integer limit, String sort, String order) {
        AdminUserExample example = new AdminUserExample();
        AdminUserExample.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(username)) {
            criteria.andNameLike("%" + username + "%");
        }
        criteria.andIsDeleteEqualTo((byte) 0);
        criteria.andTypeEqualTo((byte) 0);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }
        PageHelper.startPage(page, limit);
        return adminUserMapper.selectByExampleSelective(example, result);
    }

    /**
     * 查询员工列表
     *
     * @param username 用户名
     * @param page     页码数
     * @param limit    条目数
     * @param sort     排序
     * @param order    排序
     * @return 返回用户信息
     */
    @Override
    public List<AdminUser> queryEmployeeUserList(String username, Integer page, Integer limit, String sort, String order) {
        AdminUserExample example = new AdminUserExample();
        AdminUserExample.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(username)) {
            criteria.andNameLike("%" + username + "%");
        }
        criteria.andIsDeleteEqualTo((byte) 0);
        criteria.andTypeEqualTo((byte) 2);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }
        PageHelper.startPage(page, limit);
        return adminUserMapper.selectByExampleSelective(example, result);
    }

    /**
     * 更新用户
     *
     * @param adminUser 用户实体
     * @return 返回用户信息
     */
    @Override
    public int updateById(AdminUser adminUser) {
        logger.info("[begin to db]::数据库-更新用户信息，请求参数: \n{}", JSONObject.toJSONString(adminUser));
        adminUser.setUpdateTime(LocalDateTime.now());
        logger.info("[end   to db]::数据库-更新用户信息，返回结果: \n{}", JSONObject.toJSONString(adminUser));
        return adminUserMapper.updateByPrimaryKeySelective(adminUser);
    }

    /**
     * 删除用户
     *
     * @param id 用户id
     */
    @Override
    public void deleteById(Integer id) {
        adminUserMapper.logicalDeleteByPrimaryKey(id);
    }

    /**
     * 新增用户
     *
     * @param admin 用户实体
     */
    @Override
    public void add(AdminUser admin) {
        admin.setCreateTime(LocalDateTime.now());
        admin.setUpdateTime(LocalDateTime.now());
        adminUserMapper.insertSelective(admin);
    }

    /**
     * 查询用户
     *
     * @param id 用户id
     * @return 返回用户信息
     */
    @Override
    public AdminUser findById(Integer id) {
        return adminUserMapper.selectByPrimaryKeySelective(id, result);
    }

    /**
     * 查询员工列表
     *
     * @return 返回用户信息
     */
    @Override
    public List<AdminUser> queryEmployeeList() {
        AdminUserExample example = new AdminUserExample();
        example.or().andIsDeleteEqualTo((byte) 0).andTypeEqualTo((byte) 1);
        return adminUserMapper.selectByExample(example);
    }
}
