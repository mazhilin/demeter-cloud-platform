package com.demeter.cloud.model.redis.service;

import com.demeter.cloud.framework.persistence.service.BaseService;
import com.demeter.cloud.model.redis.HashOperationsService;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>封装Dcloud项目HashOperationsServiceImpl类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-23 15:48
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
@Service(value = "hashOperationsService")
public class HashOperationsServiceImpl<H, HK, HV> extends BaseService implements HashOperationsService<H, HK, HV> {

    @Resource(name = "stringRedisTemplate")
    private HashOperations<H, HK, HV> hashOperations;

    /**
     * Delete given hash {@code hashKeys}.
     *
     * @param key      must not be {@literal null}.
     * @param hashKeys must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     */
    @Override
    public Long delete(H key, Object... hashKeys) {
        return hashOperations.delete(key, hashKeys);
    }

    /**
     * Determine if given hash {@code hashKey} exists.
     *
     * @param key     must not be {@literal null}.
     * @param hashKey must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     */
    @Override
    public Boolean hasKey(H key, Object hashKey) {
        return hashOperations.hasKey(key, hashKey);
    }

    /**
     * Get value for given {@code hashKey} from hash at {@code key}.
     *
     * @param key     must not be {@literal null}.
     * @param hashKey must not be {@literal null}.
     * @return {@literal null} when key or hashKey does not exist or used in pipeline / transaction.
     */
    @Override
    public HV get(H key, Object hashKey) {
        return hashOperations.get(key, hashKey);
    }

    /**
     * Get values for given {@code hashKeys} from hash at {@code key}.
     *
     * @param key      must not be {@literal null}.
     * @param hashKeys must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     */
    @Override
    public List<HV> multiGet(H key, Collection<HK> hashKeys) {
        return hashOperations.multiGet(key, hashKeys);
    }

    /**
     * Increment {@code value} of a hash {@code hashKey} by the given {@code delta}.
     *
     * @param key     must not be {@literal null}.
     * @param hashKey must not be {@literal null}.
     * @param delta
     * @return {@literal null} when used in pipeline / transaction.
     */
    @Override
    public Long increment(H key, HK hashKey, long delta) {
        return hashOperations.increment(key, hashKey, delta);
    }

    /**
     * Increment {@code value} of a hash {@code hashKey} by the given {@code delta}.
     *
     * @param key     must not be {@literal null}.
     * @param hashKey must not be {@literal null}.
     * @param delta
     * @return {@literal null} when used in pipeline / transaction.
     */
    @Override
    public Double increment(H key, HK hashKey, double delta) {
        return null;
    }

    /**
     * Get key set (fields) of hash at {@code key}.
     *
     * @param key must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     */
    @Override
    public Set<HK> keys(H key) {
        return hashOperations.keys(key);
    }

    /**
     * Returns the length of the value associated with {@code hashKey}. If either the {@code key} or the {@code hashKey}
     * do not exist, {@code 0} is returned.
     *
     * @param key     must not be {@literal null}.
     * @param hashKey must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @since 2.1
     */
    @Override
    public Long lengthOfValue(H key, HK hashKey) {
        return hashOperations.lengthOfValue(key, hashKey);
    }

    /**
     * Get size of hash at {@code key}.
     *
     * @param key must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     */
    @Override
    public Long size(H key) {
        return hashOperations.size(key);
    }

    /**
     * Set multiple hash fields to multiple values using data provided in {@code m}.
     *
     * @param key must not be {@literal null}.
     * @param m   must not be {@literal null}.
     */
    @Override
    public void putAll(H key, Map<? extends HK, ? extends HV> m) {
        hashOperations.putAll(key, m);
    }

    /**
     * Set the {@code value} of a hash {@code hashKey}.
     *
     * @param key     must not be {@literal null}.
     * @param hashKey must not be {@literal null}.
     * @param value
     */
    @Override
    public void put(H key, HK hashKey, HV value) {
        hashOperations.put(key, hashKey, value);
    }

    /**
     * Set the {@code value} of a hash {@code hashKey} only if {@code hashKey} does not exist.
     *
     * @param key     must not be {@literal null}.
     * @param hashKey must not be {@literal null}.
     * @param value
     * @return {@literal null} when used in pipeline / transaction.
     */
    @Override
    public Boolean putIfAbsent(H key, HK hashKey, HV value) {
        return hashOperations.putIfAbsent(key, hashKey, value);
    }

    /**
     * Get entry set (values) of hash at {@code key}.
     *
     * @param key must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     */
    @Override
    public List<HV> values(H key) {
        return hashOperations.values(key);
    }

    /**
     * Get entire hash stored at {@code key}.
     *
     * @param key must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     */
    @Override
    public Map<HK, HV> entries(H key) {
        return hashOperations.entries(key);
    }

    /**
     * Use a {@link Cursor} to iterate over entries in hash at {@code key}. <br />
     * <strong>Important:</strong> Call {@link Cursor#close()} when done to avoid resource leak.
     *
     * @param key     must not be {@literal null}.
     * @param options
     * @return {@literal null} when used in pipeline / transaction.
     * @since 1.4
     */
    @Override
    public Cursor<Map.Entry<HK, HV>> scan(H key, ScanOptions options) {
        return hashOperations.scan(key, options);
    }

    /**
     * @return never {@literal null}.
     */
    @Override
    public RedisOperations<H, ?> getOperations() {
        return hashOperations.getOperations();
    }
}
