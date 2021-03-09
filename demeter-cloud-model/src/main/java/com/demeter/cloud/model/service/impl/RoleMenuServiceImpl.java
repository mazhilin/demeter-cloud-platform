package com.demeter.cloud.model.service.impl;

import com.demeter.cloud.model.entity.RoleMenu;
import com.demeter.cloud.model.exception.BusinessException;
import com.demeter.cloud.model.mapper.RoleMenuMapper;
import com.demeter.cloud.model.service.RoleMenuService;
import com.demeter.cloud.persistence.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>封装Dcloud项目RoleMenuServiceImpl类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-03-09 23:54
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
@Service("roleMenuService")
@Transactional(rollbackFor = {BusinessException.class, RuntimeException.class, Exception.class})
public class RoleMenuServiceImpl extends BaseService implements RoleMenuService {

    @Resource
    private RoleMenuMapper roleMenuMapper;

    /**
     * 查询活动列表
     *
     * @return 返回列表
     */
    @Override
    public List<RoleMenu> queryCategoryList() {
        return null;
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
    public List<RoleMenu> queryList(String name, String code, Integer page, Integer limit, String sort, String order) {
        return null;
    }

    /**
     * 查询
     *
     * @param id id
     * @return 返回文件信息
     */
    @Override
    public RoleMenu queryById(Integer id) {
        return null;
    }

    /**
     * 更新
     *
     * @param roleMenu 信息
     * @return 返回文件信息
     */
    @Override
    public int update(RoleMenu roleMenu) {
        return 0;
    }

    /**
     * 新增
     *
     * @param roleMenu 文件信息
     */
    @Override
    public void add(RoleMenu roleMenu) {

    }

    /**
     * 删除
     *
     * @param id 文件索引
     */
    @Override
    public void deleteById(Integer id) {

    }
}
