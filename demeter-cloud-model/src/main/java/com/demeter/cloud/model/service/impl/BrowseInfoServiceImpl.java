package com.demeter.cloud.model.service.impl;

import com.demeter.cloud.model.entity.ActivityInfoExample;
import com.demeter.cloud.model.entity.BrowseInfo;
import com.demeter.cloud.model.entity.BrowseInfoExample;
import com.demeter.cloud.model.exception.BusinessException;
import com.demeter.cloud.model.mapper.BrowseInfoMapper;
import com.demeter.cloud.model.service.BrowseInfoService;
import com.demeter.cloud.persistence.service.BaseService;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>封装Dcloud项目BrowseInfoServiceImpl类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-03-08 14:18
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
@Service("browseInfoService")
@Transactional(rollbackFor = {BusinessException.class, RuntimeException.class, Exception.class})
public class BrowseInfoServiceImpl extends BaseService implements BrowseInfoService {

    @Resource
    private BrowseInfoMapper browseInfoMapper;

    /**
     * 查询活动列表
     *
     * @return 返回列表
     */
    @Override
    public List<BrowseInfo> queryBrowseInfoList() {
        BrowseInfoExample example = new BrowseInfoExample();
        example.or().andIsDeleteEqualTo((byte)0).andStatusEqualTo((byte)1);
        return browseInfoMapper.selectByExample(example);
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
    public List<BrowseInfo> queryList(String name, String code, Integer page, Integer limit, String sort, String order) {
        BrowseInfoExample example = new BrowseInfoExample();
        BrowseInfoExample.Criteria criteria = example.createCriteria();

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
        return browseInfoMapper.selectByExample(example);
    }

    /**
     * 查询
     *
     * @param id id
     * @return 返回文件信息
     */
    @Override
    public BrowseInfo queryById(Integer id) {
        return null;
    }

    /**
     * 更新
     *
     * @param browseInfo 信息
     * @return 返回文件信息
     */
    @Override
    public int update(BrowseInfo browseInfo) {
        return 0;
    }

    /**
     * 新增
     *
     * @param browseInfo 文件信息
     */
    @Override
    public void add(BrowseInfo browseInfo) {

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
