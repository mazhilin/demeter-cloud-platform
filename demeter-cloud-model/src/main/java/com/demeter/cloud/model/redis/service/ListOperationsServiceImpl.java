package com.demeter.cloud.model.redis.service;

import com.demeter.cloud.model.persistence.service.BaseService;
import com.demeter.cloud.model.redis.ListOperationsService;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>封装Dcloud项目ListOperationsServiceImpl类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-23 15:47
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
@Service(value = "listOperationsService")
public class ListOperationsServiceImpl<K, V> extends BaseService implements ListOperationsService<K, V> {

    @Resource(name = "stringRedisTemplate")
    private ValueOperations<K, V> valueOperations;

    /**
     * Get elements between {@code begin} and {@code end} from list at {@code key}.
     *
     * @param key   must not be {@literal null}.
     * @param start
     * @param end
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/lrange">Redis Documentation: LRANGE</a>
     */
    @Override
    public List<V> range(K key, long start, long end) {
        return valueOperations.getOperations().opsForList().range(key, start, end);
    }

    /**
     * Trim list at {@code key} to elements between {@code start} and {@code end}.
     *
     * @param key   must not be {@literal null}.
     * @param start
     * @param end
     * @see <a href="https://redis.io/commands/ltrim">Redis Documentation: LTRIM</a>
     */
    @Override
    public void trim(K key, long start, long end) {
        valueOperations.getOperations().opsForList().trim(key, start, end);
    }

    /**
     * Get the size of list stored at {@code key}.
     *
     * @param key must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/llen">Redis Documentation: LLEN</a>
     */
    @Override
    public Long size(K key) {
        return valueOperations.getOperations().opsForList().size(key);
    }

    /**
     * Prepend {@code value} to {@code key}.
     *
     * @param key   must not be {@literal null}.
     * @param value
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/lpush">Redis Documentation: LPUSH</a>
     */
    @Override
    public Long leftPush(K key, V value) {
        return valueOperations.getOperations().opsForList().leftPush(key, value);
    }

    /**
     * Prepend {@code values} to {@code key}.
     *
     * @param key    must not be {@literal null}.
     * @param values
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/lpush">Redis Documentation: LPUSH</a>
     */
    @Override
    public Long leftPushAll(K key, V... values) {
        return valueOperations.getOperations().opsForList().leftPushAll(key, values);
    }

    /**
     * Prepend {@code values} to {@code key}.
     *
     * @param key    must not be {@literal null}.
     * @param values must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/lpush">Redis Documentation: LPUSH</a>
     * @since 1.5
     */
    @Override
    public Long leftPushAll(K key, Collection<V> values) {
        return valueOperations.getOperations().opsForList().leftPushAll(key, values);
    }

    /**
     * Prepend {@code values} to {@code key} only if the list exists.
     *
     * @param key   must not be {@literal null}.
     * @param value
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/lpushx">Redis Documentation: LPUSHX</a>
     */
    @Override
    public Long leftPushIfPresent(K key, V value) {
        return valueOperations.getOperations().opsForList().leftPushIfPresent(key, value);
    }

    /**
     * Insert {@code value} to {@code key} before {@code pivot}.
     *
     * @param key   must not be {@literal null}.
     * @param pivot must not be {@literal null}.
     * @param value
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/linsert">Redis Documentation: LINSERT</a>
     */
    @Override
    public Long leftPush(K key, V pivot, V value) {
        return valueOperations.getOperations().opsForList().leftPush(key, pivot, value);
    }

    /**
     * Append {@code value} to {@code key}.
     *
     * @param key   must not be {@literal null}.
     * @param value
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/rpush">Redis Documentation: RPUSH</a>
     */
    @Override
    public Long rightPush(K key, V value) {
        return valueOperations.getOperations().opsForList().rightPush(key, value);
    }

    /**
     * Append {@code values} to {@code key}.
     *
     * @param key    must not be {@literal null}.
     * @param values
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/rpush">Redis Documentation: RPUSH</a>
     */
    @Override
    public Long rightPushAll(K key, V... values) {
        return valueOperations.getOperations().opsForList().rightPushAll(key, values);
    }

    /**
     * Append {@code values} to {@code key}.
     *
     * @param key    must not be {@literal null}.
     * @param values
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/rpush">Redis Documentation: RPUSH</a>
     * @since 1.5
     */
    @Override
    public Long rightPushAll(K key, Collection<V> values) {
        return valueOperations.getOperations().opsForList().rightPushAll(key, values);
    }

    /**
     * Append {@code values} to {@code key} only if the list exists.
     *
     * @param key   must not be {@literal null}.
     * @param value
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/rpushx">Redis Documentation: RPUSHX</a>
     */
    @Override
    public Long rightPushIfPresent(K key, V value) {
        return valueOperations.getOperations().opsForList().rightPushIfPresent(key, value);
    }

    /**
     * Insert {@code value} to {@code key} after {@code pivot}.
     *
     * @param key   must not be {@literal null}.
     * @param pivot must not be {@literal null}.
     * @param value
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/linsert">Redis Documentation: LINSERT</a>
     */
    @Override
    public Long rightPush(K key, V pivot, V value) {
        return valueOperations.getOperations().opsForList().rightPush(key, value);
    }

    /**
     * Set the {@code value} list element at {@code index}.
     *
     * @param key   must not be {@literal null}.
     * @param index
     * @param value
     * @see <a href="https://redis.io/commands/lset">Redis Documentation: LSET</a>
     */
    @Override
    public void set(K key, long index, V value) {
        valueOperations.getOperations().opsForList().set(key, index, value);
    }

    /**
     * Removes the first {@code count} occurrences of {@code value} from the list stored at {@code key}.
     *
     * @param key   must not be {@literal null}.
     * @param count
     * @param value
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/lrem">Redis Documentation: LREM</a>
     */
    @Override
    public Long remove(K key, long count, Object value) {
        return valueOperations.getOperations().opsForList().remove(key, count, value);
    }

    /**
     * Get element at {@code index} form list at {@code key}.
     *
     * @param key   must not be {@literal null}.
     * @param index
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/lindex">Redis Documentation: LINDEX</a>
     */
    @Override
    public V index(K key, long index) {
        return valueOperations.getOperations().opsForList().index(key, index);
    }

    /**
     * Removes and returns first element in list stored at {@code key}.
     *
     * @param key must not be {@literal null}.
     * @return can be {@literal null}.
     * @see <a href="https://redis.io/commands/lpop">Redis Documentation: LPOP</a>
     */
    @Override
    public V leftPop(K key) {
        return valueOperations.getOperations().opsForList().leftPop(key);
    }

    /**
     * Removes and returns first element from lists stored at {@code key} . <br>
     * <b>Blocks connection</b> until element available or {@code timeout} reached.
     *
     * @param key     must not be {@literal null}.
     * @param timeout
     * @param unit    must not be {@literal null}.
     * @return can be {@literal null}.
     * @see <a href="https://redis.io/commands/blpop">Redis Documentation: BLPOP</a>
     */
    @Override
    public V leftPop(K key, long timeout, TimeUnit unit) {
        return valueOperations.getOperations().opsForList().leftPop(key, timeout, unit);
    }

    /**
     * Removes and returns last element in list stored at {@code key}.
     *
     * @param key must not be {@literal null}.
     * @return can be {@literal null}.
     * @see <a href="https://redis.io/commands/rpop">Redis Documentation: RPOP</a>
     */
    @Override
    public V rightPop(K key) {
        return valueOperations.getOperations().opsForList().rightPop(key);
    }

    /**
     * Removes and returns last element from lists stored at {@code key}. <br>
     * <b>Blocks connection</b> until element available or {@code timeout} reached.
     *
     * @param key     must not be {@literal null}.
     * @param timeout
     * @param unit    must not be {@literal null}.
     * @return can be {@literal null}.
     * @see <a href="https://redis.io/commands/brpop">Redis Documentation: BRPOP</a>
     */
    @Override
    public V rightPop(K key, long timeout, TimeUnit unit) {
        return valueOperations.getOperations().opsForList().rightPop(key, timeout, unit);
    }

    /**
     * Remove the last element from list at {@code sourceKey}, append it to {@code destinationKey} and return its value.
     *
     * @param sourceKey      must not be {@literal null}.
     * @param destinationKey must not be {@literal null}.
     * @return can be {@literal null}.
     * @see <a href="https://redis.io/commands/rpoplpush">Redis Documentation: RPOPLPUSH</a>
     */
    @Override
    public V rightPopAndLeftPush(K sourceKey, K destinationKey) {
        return valueOperations.getOperations().opsForList().rightPopAndLeftPush(sourceKey, destinationKey);
    }

    /**
     * Remove the last element from list at {@code srcKey}, append it to {@code dstKey} and return its value.<br>
     * <b>Blocks connection</b> until element available or {@code timeout} reached.
     *
     * @param sourceKey      must not be {@literal null}.
     * @param destinationKey must not be {@literal null}.
     * @param timeout
     * @param unit           must not be {@literal null}.
     * @return can be {@literal null}.
     * @see <a href="https://redis.io/commands/brpoplpush">Redis Documentation: BRPOPLPUSH</a>
     */
    @Override
    public V rightPopAndLeftPush(K sourceKey, K destinationKey, long timeout, TimeUnit unit) {
        return valueOperations.getOperations().opsForList().rightPopAndLeftPush(sourceKey, destinationKey, timeout,
                unit);
    }

    @Override
    public RedisOperations<K, V> getOperations() {
        return valueOperations.getOperations();
    }

    /**
     * Removes and returns first element from lists stored at {@code key} . <br>
     * <b>Blocks connection</b> until element available or {@code timeout} reached.
     *
     * @param key     must not be {@literal null}.
     * @param timeout must not be {@literal null}.
     * @return can be {@literal null}.
     * @throws IllegalArgumentException if the timeout is {@literal null} or negative.
     * @see <a href="https://redis.io/commands/blpop">Redis Documentation: BLPOP</a>
     * @since 2.3
     */
    @Override
    public V leftPop(K key, Duration timeout) {
        return valueOperations.getOperations().opsForList().leftPop(key, timeout);
    }

    /**
     * Removes and returns last element from lists stored at {@code key}. <br>
     * <b>Blocks connection</b> until element available or {@code timeout} reached.
     *
     * @param key     must not be {@literal null}.
     * @param timeout must not be {@literal null}.
     * @return can be {@literal null}.
     * @see <a href="https://redis.io/commands/brpop">Redis Documentation: BRPOP</a>
     * @since 2.3
     */
    @Override
    public V rightPop(K key, Duration timeout) {
        return valueOperations.getOperations().opsForList().rightPop(key, timeout);
    }

    /**
     * Remove the last element from list at {@code srcKey}, append it to {@code dstKey} and return its value.<br>
     * <b>Blocks connection</b> until element available or {@code timeout} reached.
     *
     * @param sourceKey      must not be {@literal null}.
     * @param destinationKey must not be {@literal null}.
     * @param timeout        must not be {@literal null}.
     * @return can be {@literal null}.
     * @throws IllegalArgumentException if the timeout is {@literal null} or negative.
     * @see <a href="https://redis.io/commands/brpoplpush">Redis Documentation: BRPOPLPUSH</a>
     * @since 2.3
     */
    @Override
    public V rightPopAndLeftPush(K sourceKey, K destinationKey, Duration timeout) {
        return valueOperations.getOperations().opsForList().rightPopAndLeftPush(sourceKey, destinationKey, timeout);
    }
}
