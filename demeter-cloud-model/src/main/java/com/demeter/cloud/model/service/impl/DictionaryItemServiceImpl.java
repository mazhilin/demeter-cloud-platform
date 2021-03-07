package com.demeter.cloud.model.service.impl;

import com.demeter.cloud.model.entity.DictionaryItem;
import com.demeter.cloud.model.entity.DictionaryItemExample;
import com.demeter.cloud.model.exception.BusinessException;
import com.demeter.cloud.model.mapper.DictionaryItemMapper;
import com.demeter.cloud.persistence.service.BaseService;
import com.demeter.cloud.model.service.DictionaryItemService;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>封装Dcloud项目DictionaryItemServiceImpl类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-19 01:12
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
@Service("dictionaryItemService")
@Transactional(rollbackFor = {BusinessException.class, RuntimeException.class, Exception.class})
public class DictionaryItemServiceImpl extends BaseService implements DictionaryItemService {
    @Resource
    private DictionaryItemMapper dictionaryItemMapper;
    /**
     * 查询字典列表
     *
     * @return 返回列表
     */
    @Override
    public List<DictionaryItem> queryDictionaryItemList() {
        DictionaryItemExample example = new DictionaryItemExample();
        example.or().andIsDeleteEqualTo((byte) 0).andStatusEqualTo((byte) 1);
        return dictionaryItemMapper.selectByExample(example);
    }

    /**
     * 查询列表
     *
     * @param name  名称
     * @param label
     * @param page  页码
     * @param limit 条数
     * @param sort  排序
     * @param order 排序
     * @return 返回列表
     */
    @Override
    public List<DictionaryItem> queryList(String name, String label, Integer page, Integer limit, String sort, String order) {
        DictionaryItemExample example = new DictionaryItemExample();
        DictionaryItemExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        if (!StringUtils.isEmpty(label)) {
            criteria.andLabelLike("%" + label + "%");
        }
        criteria.andIsDeleteEqualTo((byte) 0);
        criteria.andStatusEqualTo((byte) 1);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return dictionaryItemMapper.selectByExample(example);
    }

    /**
     * 查询
     *
     * @param id id
     * @return 返回文件信息
     */
    @Override
    public DictionaryItem queryById(Integer id) {
        return dictionaryItemMapper.selectByPrimaryKey(id);
    }

    /**
     * 更新
     *
     * @param dictionaryItem 信息
     * @return 返回文件信息
     */
    @Override
    public int update(DictionaryItem dictionaryItem) {
        dictionaryItem.setUpdateTime(LocalDateTime.now());
        return dictionaryItemMapper.updateByPrimaryKeySelective(dictionaryItem);
    }

    /**
     * 新增
     *
     * @param dictionaryItem 文件信息
     */
    @Override
    public void add(DictionaryItem dictionaryItem) {
        dictionaryItem.setCreateTime(LocalDateTime.now());
        dictionaryItem.setUpdateTime(LocalDateTime.now());
        dictionaryItemMapper.insertSelective(dictionaryItem);
    }



    /**
     * 删除
     *
     * @param id 文件索引
     */
    @Override
    public void deleteById(Integer id) {
        dictionaryItemMapper.logicalDeleteByPrimaryKey(id);
    }

    /**
     * 查询
     *
     * @param dictionaryId 字典id
     * @param type         类型
     * @return 返回结果
     */
    @Override
    public List<DictionaryItem> findListByTypeAndId(Integer dictionaryId, String type) {
        DictionaryItemExample example = new DictionaryItemExample();
        example.or().andDictionaryIdEqualTo(dictionaryId).andTypeEqualTo(type).andIsDeleteEqualTo((byte) 0).andStatusEqualTo((byte) 1);
        return dictionaryItemMapper.selectByExample(example);
    }

    /**
     * 查询
     *
     * @param dictionaryId 字典id
     * @return 返回结果
     */
    @Override
    public List<DictionaryItem> findListById(Integer dictionaryId) {
        DictionaryItemExample example = new DictionaryItemExample();
        example.or().andDictionaryIdEqualTo(dictionaryId);
        return dictionaryItemMapper.selectByExample(example);
    }

    /**
     * 删除
     *
     * @param dictionaryItemList
     */
    @Override
    public void deleteAll(List<DictionaryItem> dictionaryItemList) {
        dictionaryItemList.parallelStream().forEachOrdered(item -> dictionaryItemMapper.logicalDeleteByPrimaryKey(item.getId()));
    }
}
