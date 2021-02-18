package com.demeter.cloud.gateway.manager;

import com.demeter.cloud.core.constant.Constants;
import com.demeter.cloud.core.manager.BaseCacheManager;
import com.google.common.collect.Maps;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * <p>封装Qicloud项目GatewayHomeManager类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-19 02:26
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
public class GatewayHomeManager implements BaseCacheManager {
    /**
     * 默认启动缓存
     */
    public static final boolean ENABLE = true;
    public static final String INDEX = "index";
    public static final String CATALOG = "catalog";
    public static final String GOODS = "goods";

    private static Map<String, Map<String, Object>> cacheDataList = Maps.newConcurrentMap();

    public static Map<String, Object> getCacheData(String cacheKey) {
        return cacheDataList.get(cacheKey);
    }

    /**
     * 缓存首页数据
     *
     * @param data
     */
    @Override
    public void loadData(String cacheKey, Map<String, Object> data) {
        Map<String, Object> cacheData = cacheDataList.get(cacheKey);
        // 有记录，则先丢弃
        if (cacheData != null) {
            cacheData.remove(cacheKey);
        }
        // 深拷贝
        cacheData = Maps.newLinkedHashMap(data);
        // 拷贝后去掉可能含用户独有的信息数据
        if (INDEX.equals(cacheKey)) {
            cacheData.remove("couponList");
        }
        cacheData.put("isCache", "true");
        // 设置缓存有效期单位 ： 分钟
        cacheData.put("expireTime", LocalDateTime.now().plusMinutes(Constants.CACHE_EXPIRE_MINUTES));
        cacheDataList.put(cacheKey, cacheData);
    }

    /**
     * 判断缓存中是否有数据
     *
     * @return
     */
    @Override
    public boolean hasData(String cacheKey) {
        if (!ENABLE) {
            return false;
        }
        Map<String, Object> cacheData = cacheDataList.get(cacheKey);
        if (cacheData == null) {
            return false;
        } else {
            LocalDateTime expire = (LocalDateTime) cacheData.get("expireTime");
            if (expire.isBefore(LocalDateTime.now())) {
                return false;
            } else {
                return true;
            }
        }
    }

    /**
     * 清除所有缓存
     */
    @Override
    public void clearAll() {
        cacheDataList = Maps.newConcurrentMap();
    }

    /**
     * 清除缓存数据
     */
    @Override
    public void clear(String cacheKey) {
        Map<String, Object> cacheData = cacheDataList.get(cacheKey);
        if (cacheData != null) {
            cacheDataList.remove(cacheKey);
        }
    }

    /**
     * 获取应用实例
     */
    @Override
    public void getInstance() {

    }
}
