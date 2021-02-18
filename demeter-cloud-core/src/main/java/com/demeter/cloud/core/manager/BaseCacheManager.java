package com.demeter.cloud.core.manager;

import java.util.Map;

/**
 * <p>封装Qicloud项目BaseCacheManager类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-19 02:31
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
public interface BaseCacheManager extends Manager {

    /**
     * 加载数据
     *
     * @param key   缓存key
     * @param value 值
     */
    void loadData(String key, Map<String, Object> value);

    /**
     * 校验数据
     *
     * @param key 缓存key
     * @return 返回值
     */
    boolean hasData(String key);

    /**
     * 清除所有数据
     */
    void clearAll();

    /**
     * 清除数据
     *
     * @param key 缓存key
     */
    void clear(String key);

}
