package com.demeter.cloud.model.redis.service;

import com.demeter.cloud.model.persistence.service.BaseService;
import com.demeter.cloud.model.redis.ValueOperationsService;
import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>封装Dcloud项目ValueOperationsServiceImpl类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-23 15:48
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
@Service(value = "valueOperationsService")
public class ValueOperationsServiceImpl<K, V> extends BaseService implements ValueOperationsService<K, V> {

    @Resource(name = "stringRedisTemplate")
    private ValueOperations<K, V> valueOperations;

    /**
     * Set {@code value} for {@code key}.
     *
     * @param key   must not be {@literal null}.
     * @param value must not be {@literal null}.
     * @see <a href="https://redis.io/commands/set">Redis Documentation: SET</a>
     */
    @Override
    public void set(K key, V value) {
        valueOperations.set(key, value);
    }

    /**
     * Set the {@code value} and expiration {@code timeout} for {@code key}.
     *
     * @param key     must not be {@literal null}.
     * @param value   must not be {@literal null}.
     * @param timeout the key expiration timeout.
     * @param unit    must not be {@literal null}.
     * @see <a href="https://redis.io/commands/setex">Redis Documentation: SETEX</a>
     */
    @Override
    public void set(K key, V value, long timeout, TimeUnit unit) {
        valueOperations.set(key, value, timeout, unit);
    }

    /**
     * Set {@code key} to hold the string {@code value} if {@code key} is absent.
     *
     * @param key   must not be {@literal null}.
     * @param value must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/setnx">Redis Documentation: SETNX</a>
     */
    @Override
    public Boolean setIfAbsent(K key, V value) {
        return valueOperations.setIfAbsent(key, value);
    }

    /**
     * Set {@code key} to hold the string {@code value} and expiration {@code timeout} if {@code key} is absent.
     *
     * @param key     must not be {@literal null}.
     * @param value   must not be {@literal null}.
     * @param timeout the key expiration timeout.
     * @param unit    must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/set">Redis Documentation: SET</a>
     * @since 2.1
     */
    @Override
    public Boolean setIfAbsent(K key, V value, long timeout, TimeUnit unit) {
        return valueOperations.setIfAbsent(key, value, timeout, unit);
    }

    /**
     * Set {@code key} to hold the string {@code value} if {@code key} is present.
     *
     * @param key   must not be {@literal null}.
     * @param value must not be {@literal null}.
     * @return command result indicating if the key has been set.
     * @throws IllegalArgumentException if either {@code key} or {@code value} is not present.
     * @see <a href="https://redis.io/commands/set">Redis Documentation: SET</a>
     * @since 2.1
     */
    @Override
    public Boolean setIfPresent(K key, V value) {
        return valueOperations.setIfPresent(key, value);
    }

    /**
     * Set {@code key} to hold the string {@code value} and expiration {@code timeout} if {@code key} is present.
     *
     * @param key     must not be {@literal null}.
     * @param value   must not be {@literal null}.
     * @param timeout the key expiration timeout.
     * @param unit    must not be {@literal null}.
     * @return command result indicating if the key has been set.
     * @throws IllegalArgumentException if either {@code key}, {@code value} or {@code timeout} is not present.
     * @see <a href="https://redis.io/commands/set">Redis Documentation: SET</a>
     * @since 2.1
     */
    @Override
    public Boolean setIfPresent(K key, V value, long timeout, TimeUnit unit) {
        return valueOperations.setIfPresent(key, value, timeout, unit);
    }

    /**
     * Set multiple keys to multiple values using key-value pairs provided in {@code tuple}.
     *
     * @param map must not be {@literal null}.
     * @see <a href="https://redis.io/commands/mset">Redis Documentation: MSET</a>
     */
    @Override
    public void multiSet(Map<? extends K, ? extends V> map) {
        valueOperations.multiSet(map);
    }

    /**
     * Set multiple keys to multiple values using key-value pairs provided in {@code tuple} only if the provided key does
     * not exist.
     *
     * @param map must not be {@literal null}.
     * @see <a href="https://redis.io/commands/msetnx">Redis Documentation: MSETNX</a>
     */
    @Override
    public Boolean multiSetIfAbsent(Map<? extends K, ? extends V> map) {
        return valueOperations.multiSetIfAbsent(map);
    }

    /**
     * Get the value of {@code key}.
     *
     * @param key must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/get">Redis Documentation: GET</a>
     */
    @Override
    public V get(Object key) {
        return valueOperations.get(key);
    }

    /**
     * Set {@code value} of {@code key} and return its old value.
     *
     * @param key   must not be {@literal null}.
     * @param value
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/getset">Redis Documentation: GETSET</a>
     */
    @Override
    public V getAndSet(K key, V value) {
        return valueOperations.getAndSet(key, value);
    }

    /**
     * Get multiple {@code keys}. Values are returned in the order of the requested keys.
     *
     * @param keys must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/mget">Redis Documentation: MGET</a>
     */
    @Override
    public List<V> multiGet(Collection<K> keys) {
        return valueOperations.multiGet(keys);
    }

    /**
     * Increment an integer value stored as string value under {@code key} by one.
     *
     * @param key must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/incr">Redis Documentation: INCR</a>
     * @since 2.1
     */
    @Override
    public Long increment(K key) {
        return valueOperations.increment(key);
    }

    /**
     * Increment an integer value stored as string value under {@code key} by {@code delta}.
     *
     * @param key   must not be {@literal null}.
     * @param delta
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/incrby">Redis Documentation: INCRBY</a>
     */
    @Override
    public Long increment(K key, long delta) {
        return valueOperations.increment(key, delta);
    }

    /**
     * Increment a floating point number value stored as string value under {@code key} by {@code delta}.
     *
     * @param key   must not be {@literal null}.
     * @param delta
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/incrbyfloat">Redis Documentation: INCRBYFLOAT</a>
     */
    @Override
    public Double increment(K key, double delta) {
        return valueOperations.increment(key, delta);
    }

    /**
     * Decrement an integer value stored as string value under {@code key} by one.
     *
     * @param key must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/decr">Redis Documentation: DECR</a>
     * @since 2.1
     */
    @Override
    public Long decrement(K key) {
        return valueOperations.decrement(key);
    }

    /**
     * Decrement an integer value stored as string value under {@code key} by {@code delta}.
     *
     * @param key   must not be {@literal null}.
     * @param delta
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/decrby">Redis Documentation: DECRBY</a>
     * @since 2.1
     */
    @Override
    public Long decrement(K key, long delta) {
        return valueOperations.decrement(key, delta);
    }

    /**
     * Append a {@code value} to {@code key}.
     *
     * @param key   must not be {@literal null}.
     * @param value
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/append">Redis Documentation: APPEND</a>
     */
    @Override
    public Integer append(K key, String value) {
        return valueOperations.append(key, value);
    }

    /**
     * Get a substring of value of {@code key} between {@code begin} and {@code end}.
     *
     * @param key   must not be {@literal null}.
     * @param start
     * @param end
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/getrange">Redis Documentation: GETRANGE</a>
     */
    @Override
    public String get(K key, long start, long end) {
        return valueOperations.get(key, start, end);
    }

    /**
     * Overwrite parts of {@code key} starting at the specified {@code offset} with given {@code value}.
     *
     * @param key    must not be {@literal null}.
     * @param value
     * @param offset
     * @see <a href="https://redis.io/commands/setrange">Redis Documentation: SETRANGE</a>
     */
    @Override
    public void set(K key, V value, long offset) {
        valueOperations.set(key, value, offset);
    }

    /**
     * Get the length of the value stored at {@code key}.
     *
     * @param key must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/strlen">Redis Documentation: STRLEN</a>
     */
    @Override
    public Long size(K key) {
        return valueOperations.size(key);
    }

    /**
     * Sets the bit at {@code offset} in value stored at {@code key}.
     *
     * @param key    must not be {@literal null}.
     * @param offset
     * @param value
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/setbit">Redis Documentation: SETBIT</a>
     * @since 1.5
     */
    @Override
    public Boolean setBit(K key, long offset, boolean value) {
        return valueOperations.setBit(key, offset, value);
    }

    /**
     * Get the bit value at {@code offset} of value at {@code key}.
     *
     * @param key    must not be {@literal null}.
     * @param offset
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/setbit">Redis Documentation: GETBIT</a>
     * @since 1.5
     */
    @Override
    public Boolean getBit(K key, long offset) {
        return valueOperations.getBit(key, offset);
    }

    /**
     * Get / Manipulate specific integer fields of varying bit widths and arbitrary non (necessary) aligned offset stored
     * at a given {@code key}.
     *
     * @param key         must not be {@literal null}.
     * @param subCommands must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/bitfield">Redis Documentation: BITFIELD</a>
     * @since 2.1
     */
    @Override
    public List<Long> bitField(K key, BitFieldSubCommands subCommands) {
        return valueOperations.bitField(key, subCommands);
    }

    @Override
    public RedisOperations<K, V> getOperations() {
        return valueOperations.getOperations();
    }

    /**
     * Set the {@code value} and expiration {@code timeout} for {@code key}.
     *
     * @param key     must not be {@literal null}.
     * @param value   must not be {@literal null}.
     * @param timeout must not be {@literal null}.
     * @throws IllegalArgumentException if either {@code key}, {@code value} or {@code timeout} is not present.
     * @see <a href="https://redis.io/commands/setex">Redis Documentation: SETEX</a>
     * @since 2.1
     */
    @Override
    public void set(K key, V value, Duration timeout) {
        valueOperations.set(key, value, timeout);
    }

    /**
     * Set {@code key} to hold the string {@code value} and expiration {@code timeout} if {@code key} is absent.
     *
     * @param key     must not be {@literal null}.
     * @param value   must not be {@literal null}.
     * @param timeout must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @throws IllegalArgumentException if either {@code key}, {@code value} or {@code timeout} is not present.
     * @see <a href="https://redis.io/commands/set">Redis Documentation: SET</a>
     * @since 2.1
     */
    @Override
    public Boolean setIfAbsent(K key, V value, Duration timeout) {
        return valueOperations.setIfAbsent(key, value, timeout);
    }

    /**
     * Set {@code key} to hold the string {@code value} and expiration {@code timeout} if {@code key} is present.
     *
     * @param key     must not be {@literal null}.
     * @param value   must not be {@literal null}.
     * @param timeout must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @throws IllegalArgumentException if either {@code key}, {@code value} or {@code timeout} is not present.
     * @see <a href="https://redis.io/commands/set">Redis Documentation: SET</a>
     * @since 2.1
     */
    @Override
    public Boolean setIfPresent(K key, V value, Duration timeout) {
        return valueOperations.setIfAbsent(key, value, timeout);
    }
}
