package com.demeter.cloud.model.service.impl;

import com.demeter.cloud.model.entity.LikeInfoExample;
import com.demeter.cloud.model.entity.MenuInfo;
import com.demeter.cloud.model.entity.MenuInfoExample;
import com.demeter.cloud.model.exception.BusinessException;
import com.demeter.cloud.model.mapper.MenuInfoMapper;
import com.demeter.cloud.model.service.MenuInfoService;
import com.demeter.cloud.persistence.service.BaseService;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>封装Dcloud项目MenuInfoServiceImpl类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-03-10 00:02
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
@Service("menuInfoService")
@Transactional(rollbackFor = {BusinessException.class, RuntimeException.class, Exception.class})
public class MenuInfoServiceImpl extends BaseService implements MenuInfoService {

    @Resource
    private MenuInfoMapper menuInfoMapper;

    /**
     * 查询活动列表
     *
     * @return 返回列表
     */
    @Override
    public List<MenuInfo> queryCategoryList() {
        MenuInfoExample example = new MenuInfoExample();
        example.or().andIsDeleteEqualTo((byte) 0).andStatusEqualTo((byte) 1);
        return menuInfoMapper.selectByExample(example);
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
    public List<MenuInfo> queryList(String name, String code, Integer page, Integer limit, String sort, String order) {
        MenuInfoExample example = new MenuInfoExample();
        MenuInfoExample.Criteria criteria = example.createCriteria();

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
        return menuInfoMapper.selectByExample(example);
    }

    /**
     * 查询
     *
     * @param id id
     * @return 返回文件信息
     */
    @Override
    public MenuInfo queryById(Integer id) {
        return menuInfoMapper.selectByPrimaryKey(id);
    }

    /**
     * 更新
     *
     * @param menu 信息
     * @return 返回文件信息
     */
    @Override
    public int update(MenuInfo menu) {
        menu.setUpdateTime(LocalDateTime.now());
        return menuInfoMapper.updateByPrimaryKeySelective(menu);
    }

    /**
     * 新增
     *
     * @param menu 文件信息
     */
    @Override
    public void add(MenuInfo menu) {
        menu.setCreateTime(LocalDateTime.now());
        menu.setUpdateTime(LocalDateTime.now());
        menuInfoMapper.insertSelective(menu);
    }

    /**
     * 删除
     *
     * @param id 文件索引
     */
    @Override
    public void deleteById(Integer id) {
        menuInfoMapper.logicalDeleteByPrimaryKey(id);
    }
}
