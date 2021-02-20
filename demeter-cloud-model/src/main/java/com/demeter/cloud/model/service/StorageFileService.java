package com.demeter.cloud.model.service;

import com.demeter.cloud.model.entity.StorageFile;

import java.util.List;

/**
 * <p>封装Dcloud项目StorageFileService类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-17 00:38
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
public interface StorageFileService {

    /**
     * 查询文件列表
     * @param key 文件索引
     * @param name 文件名称
     * @param page 页码
     * @param limit 条数
     * @param sort 排序
     * @param order 排序
     * @return 返回列表
     */
    List<StorageFile> queryFileList(String key, String name, Integer page, Integer limit, String sort, String order);


    /**
     * 查询
     * @param id 文件id
     * @return 返回文件信息
     */
    StorageFile queryFileById(Integer id);


    /**
     * 更新
     * @param file 文件信息
     * @return 返回文件信息
     */
    int update(StorageFile file);

    /**
     * 新增
     * @param file 文件信息
     */
    void add(StorageFile file);

    /**
     * 查询
     * @param key 文件索引
     * @return
     */
    StorageFile queryFileByKey(String key);

    /**
     * 删除
     * @param key 文件索引
     */
    void deleteByKey(String key);
}
