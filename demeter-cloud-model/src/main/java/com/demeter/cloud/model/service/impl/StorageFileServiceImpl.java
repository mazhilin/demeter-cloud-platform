package com.demeter.cloud.model.service.impl;

import com.demeter.cloud.model.entity.StorageFile;
import com.demeter.cloud.model.entity.StorageFileExample;
import com.demeter.cloud.model.exception.BusinessException;
import com.demeter.cloud.model.mapper.StorageFileMapper;
import com.demeter.cloud.model.persistence.service.BaseService;
import com.demeter.cloud.model.service.StorageFileService;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>封装Qicloud项目StorageFileServiceImpl类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-17 00:47
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
@Service
@Transactional(rollbackFor = {BusinessException.class, RuntimeException.class, Exception.class})
public class StorageFileServiceImpl extends BaseService implements StorageFileService {

    @Resource
    private StorageFileMapper storageFileMapper;

    /**
     * 查询文件列表
     *
     * @param key   文件索引
     * @param name  文件名称
     * @param page  页码
     * @param limit 条数
     * @param sort  排序
     * @param order 排序
     * @return 返回列表
     */
    @Override
    public List<StorageFile> queryFileList(String key, String name, Integer page, Integer limit, String sort, String order) {
        StorageFileExample example = new StorageFileExample();
        StorageFileExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(key)) {
            criteria.andKeyEqualTo(key);
        }
        if (!StringUtils.isEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        criteria.andIsDeleteEqualTo((byte) 0);
        criteria.andStatusEqualTo((byte) 1);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return storageFileMapper.selectByExample(example);
    }

    /**
     * 查询
     *
     * @param id 文件id
     * @return 返回文件信息
     */
    @Override
    public StorageFile queryFileById(Integer id) {
        return storageFileMapper.selectByPrimaryKey(id);
    }

    /**
     * 更新
     *
     * @param file 文件信息
     * @return 返回文件信息
     */
    @Override
    public int update(StorageFile file) {
        file.setUpdateTime(LocalDateTime.now());
        return storageFileMapper.updateByPrimaryKeySelective(file);
    }

    /**
     * 新增
     *
     * @param file 文件信息
     */
    @Override
    public void add(StorageFile file) {
        file.setCreateTime(LocalDateTime.now());
        file.setUpdateTime(LocalDateTime.now());
        storageFileMapper.insertSelective(file);
    }

    /**
     * 查询
     *
     * @param key 文件索引
     * @return
     */
    @Override
    public StorageFile queryFileByKey(String key) {
        StorageFileExample example = new StorageFileExample();
        example.or().andKeyEqualTo(key).andIsDeleteEqualTo((byte) 1).andStatusEqualTo((byte) 1);;
        return storageFileMapper.selectOneByExample(example);
    }

    /**
     * 删除
     *
     * @param key 文件索引
     */
    @Override
    public void deleteByKey(String key) {
        StorageFileExample example = new StorageFileExample();
        example.or().andKeyEqualTo(key);
        storageFileMapper.logicalDeleteByExample(example);
    }
}
