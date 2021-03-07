package com.demeter.cloud.model.service.impl;

import com.demeter.cloud.model.entity.Dictionary;
import com.demeter.cloud.model.entity.DictionaryExample;
import com.demeter.cloud.model.exception.BusinessException;
import com.demeter.cloud.model.mapper.DictionaryMapper;
import com.demeter.cloud.persistence.service.BaseService;
import com.demeter.cloud.model.service.DictionaryService;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>封装Dcloud项目DictionaryServiceImpl类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-18 23:23
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
@Service("dictionaryService")
@Transactional(rollbackFor = {BusinessException.class, RuntimeException.class, Exception.class})
public class DictionaryServiceImpl extends BaseService implements DictionaryService {

    @Resource
    private DictionaryMapper dictionaryMapper;

    /**
     * 查询字典列表
     *
     * @return 返回列表
     */
    @Override
    public List<Dictionary> queryDictionaryList() {
        DictionaryExample example = new DictionaryExample();
        example.or();
        return dictionaryMapper.selectByExample(example);
    }

    /**
     * 查询列表
     *
     * @param name  名称
     * @param code 编码
     * @param page  页码
     * @param limit 条数
     * @param sort  排序
     * @param order 排序
     * @return 返回列表
     */
    @Override
    public List<Dictionary> queryList(String name, String code,Integer page, Integer limit, String sort, String order) {
        DictionaryExample example = new DictionaryExample();
        DictionaryExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        if (!StringUtils.isEmpty(code)) {
            criteria.andCodeEqualTo(code);
        }
        criteria.andIsDeleteEqualTo((byte) 0);
        criteria.andStatusEqualTo((byte) 1);


        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return dictionaryMapper.selectByExample(example);
    }

    /**
     * 查询
     *
     * @param id id
     * @return 返回文件信息
     */
    @Override
    public Dictionary queryById(Integer id) {
        return dictionaryMapper.selectByPrimaryKey(id);
    }

    /**
     * 更新
     *
     * @param dictionary 信息
     * @return 返回文件信息
     */
    @Override
    public int update(Dictionary dictionary) {
        dictionary.setUpdateTime(LocalDateTime.now());
        return dictionaryMapper.updateByPrimaryKeySelective(dictionary);
    }

    /**
     * 新增
     *
     * @param dictionary 文件信息
     */
    @Override
    public void add(Dictionary dictionary) {
        dictionary.setCreateTime(LocalDateTime.now());
        dictionary.setUpdateTime(LocalDateTime.now());
        dictionaryMapper.insertSelective(dictionary);
    }

    /**
     * 查询
     *
     * @param code 文件索引
     * @return
     */
    @Override
    public Dictionary queryByCode(String code) {
        DictionaryExample example = new DictionaryExample();
        example.or().andCodeEqualTo(code);
        return dictionaryMapper.selectOneByExample(example);
    }

    /**
     * 删除
     *
     * @param id 文件索引
     */
    @Override
    public void deleteById(Integer id) {
        dictionaryMapper.logicalDeleteByPrimaryKey(id);
    }
}
