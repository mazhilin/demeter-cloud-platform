package com.demeter.cloud.model.service;

import com.demeter.cloud.model.entity.AdminUser;

import java.util.List;

/**
 * <p>封装Qicloud项目AdminUserService类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-16 22:17
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
public interface AdminUserService {

    /**
     * 依据账户查询用户信息
     *
     * @param account 账户
     * @return 返回用户信息
     */
    List<AdminUser> queryAdminUserByAccount(String account);

    /**
     * 依据用户名查询用户信息
     *
     * @param username 姓名
     * @return 返回用户信息
     */
    List<AdminUser> queryAdminUserByUsername(String username);

    /**
     * 依据用户id查询用户信息
     *
     * @param id 用户id
     * @return 返回用户信息
     */
    AdminUser queryAdminUserById(Integer id);

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
    List<AdminUser> queryAdminUserList(String username, Integer page, Integer limit, String sort, String order);

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
    List<AdminUser> queryEmployeeUserList(String username, Integer page, Integer limit, String sort, String order);

    /**
     * 更新用户
     *
     * @param adminUser 用户实体
     * @return 返回用户信息
     */
    int updateById(AdminUser adminUser);

    /**
     * 删除用户
     *
     * @param id 用户id
     */
    void deleteById(Integer id);

    /**
     * 新增用户
     *
     * @param admin 用户实体
     */
    void add(AdminUser admin);

    /**
     * 查询用户
     *
     * @param id 用户id
     * @return 返回用户信息
     */
    AdminUser findById(Integer id);

    /**
     * 查询员工列表
     *
     * @return 返回用户信息
     */
    List<AdminUser> queryEmployeeList();
}
