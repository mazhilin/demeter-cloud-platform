package com.demeter.cloud.model.redis.service;

import com.demeter.cloud.framework.persistence.service.BaseService;
import com.demeter.cloud.model.redis.SetOperationsService;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * <p>封装Dcloud项目SetOperationsServiceImpl类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-23 15:48
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
@Service(value = "setOperationsService")
public class SetOperationsServiceImpl<K, V> extends BaseService implements SetOperationsService<K, V> {
    @Resource(name = "stringRedisTemplate")
    private ValueOperations<K, V> valueOperations;

    /**
     * Add given {@code values} to set at {@code key}.
     *
     * @param key    must not be {@literal null}.
     * @param values
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/sadd">Redis Documentation: SADD</a>
     */
    @Override
    public Long add(K key, V... values) {
        return valueOperations.getOperations().opsForSet().add(key, values);
    }

    /**
     * Remove given {@code values} from set at {@code key} and return the number of removed elements.
     *
     * @param key    must not be {@literal null}.
     * @param values
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/srem">Redis Documentation: SREM</a>
     */
    @Override
    public Long remove(K key, Object... values) {
        return valueOperations.getOperations().opsForSet().remove(key, values);
    }

    /**
     * Remove and return a random member from set at {@code key}.
     *
     * @param key must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/spop">Redis Documentation: SPOP</a>
     */
    @Override
    public V pop(K key) {
        return valueOperations.getOperations().opsForSet().pop(key);
    }

    /**
     * Remove and return {@code count} random members from set at {@code key}.
     *
     * @param key   must not be {@literal null}.
     * @param count number of random members to pop from the set.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/spop">Redis Documentation: SPOP</a>
     * @since 2.0
     */
    @Override
    public List<V> pop(K key, long count) {
        return valueOperations.getOperations().opsForSet().pop(key, count);
    }

    /**
     * Move {@code value} from {@code key} to {@code destKey}
     *
     * @param key     must not be {@literal null}.
     * @param value
     * @param destKey must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/smove">Redis Documentation: SMOVE</a>
     */
    @Override
    public Boolean move(K key, V value, K destKey) {
        return valueOperations.getOperations().opsForSet().move(key, value, destKey);
    }

    /**
     * Get size of set at {@code key}.
     *
     * @param key must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/scard">Redis Documentation: SCARD</a>
     */
    @Override
    public Long size(K key) {
        return valueOperations.getOperations().opsForSet().size(key);
    }

    /**
     * Check if set at {@code key} contains {@code value}.
     *
     * @param key must not be {@literal null}.
     * @param o
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/sismember">Redis Documentation: SISMEMBER</a>
     */
    @Override
    public Boolean isMember(K key, Object o) {
        return valueOperations.getOperations().opsForSet().isMember(key, o);
    }

    /**
     * Returns the members intersecting all given sets at {@code key} and {@code otherKey}.
     *
     * @param key      must not be {@literal null}.
     * @param otherKey must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/sinter">Redis Documentation: SINTER</a>
     */
    @Override
    public Set<V> intersect(K key, K otherKey) {
        return valueOperations.getOperations().opsForSet().intersect(key, otherKey);
    }

    /**
     * Returns the members intersecting all given sets at {@code key} and {@code otherKeys}.
     *
     * @param key       must not be {@literal null}.
     * @param otherKeys must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/sinter">Redis Documentation: SINTER</a>
     */
    @Override
    public Set<V> intersect(K key, Collection<K> otherKeys) {
        return valueOperations.getOperations().opsForSet().intersect(key, otherKeys);
    }

    /**
     * Returns the members intersecting all given sets at {@code keys}.
     *
     * @param keys must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/sinter">Redis Documentation: SINTER</a>
     * @since 2.2
     */
    @Override
    public Set<V> intersect(Collection<K> keys) {
        return valueOperations.getOperations().opsForSet().intersect(keys);
    }

    /**
     * Intersect all given sets at {@code key} and {@code otherKey} and store result in {@code destKey}.
     *
     * @param key      must not be {@literal null}.
     * @param otherKey must not be {@literal null}.
     * @param destKey  must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/sinterstore">Redis Documentation: SINTERSTORE</a>
     */
    @Override
    public Long intersectAndStore(K key, K otherKey, K destKey) {
        return valueOperations.getOperations().opsForSet().intersectAndStore(key, otherKey, destKey);
    }

    /**
     * Intersect all given sets at {@code key} and {@code otherKeys} and store result in {@code destKey}.
     *
     * @param key       must not be {@literal null}.
     * @param otherKeys must not be {@literal null}.
     * @param destKey   must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/sinterstore">Redis Documentation: SINTERSTORE</a>
     */
    @Override
    public Long intersectAndStore(K key, Collection<K> otherKeys, K destKey) {
        return valueOperations.getOperations().opsForSet().intersectAndStore(key, otherKeys, destKey);
    }

    /**
     * Intersect all given sets at {@code keys} and store result in {@code destKey}.
     *
     * @param keys    must not be {@literal null}.
     * @param destKey must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/sinterstore">Redis Documentation: SINTERSTORE</a>
     * @since 2.2
     */
    @Override
    public Long intersectAndStore(Collection<K> keys, K destKey) {
        return valueOperations.getOperations().opsForSet().intersectAndStore(keys, destKey);
    }

    /**
     * Union all sets at given {@code keys} and {@code otherKey}.
     *
     * @param key      must not be {@literal null}.
     * @param otherKey must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/sunion">Redis Documentation: SUNION</a>
     */
    @Override
    public Set<V> union(K key, K otherKey) {
        return valueOperations.getOperations().opsForSet().union(key, otherKey);
    }

    /**
     * Union all sets at given {@code keys} and {@code otherKeys}.
     *
     * @param key       must not be {@literal null}.
     * @param otherKeys must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/sunion">Redis Documentation: SUNION</a>
     */
    @Override
    public Set<V> union(K key, Collection<K> otherKeys) {
        return valueOperations.getOperations().opsForSet().union(key, otherKeys);
    }

    /**
     * Union all sets at given {@code keys}.
     *
     * @param keys must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/sunion">Redis Documentation: SUNION</a>
     * @since 2.2
     */
    @Override
    public Set<V> union(Collection<K> keys) {
        return valueOperations.getOperations().opsForSet().union(keys);
    }

    /**
     * Union all sets at given {@code key} and {@code otherKey} and store result in {@code destKey}.
     *
     * @param key      must not be {@literal null}.
     * @param otherKey must not be {@literal null}.
     * @param destKey  must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/sunionstore">Redis Documentation: SUNIONSTORE</a>
     */
    @Override
    public Long unionAndStore(K key, K otherKey, K destKey) {
        return valueOperations.getOperations().opsForSet().unionAndStore(key, otherKey, destKey);
    }

    /**
     * Union all sets at given {@code key} and {@code otherKeys} and store result in {@code destKey}.
     *
     * @param key       must not be {@literal null}.
     * @param otherKeys must not be {@literal null}.
     * @param destKey   must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/sunionstore">Redis Documentation: SUNIONSTORE</a>
     */
    @Override
    public Long unionAndStore(K key, Collection<K> otherKeys, K destKey) {
        return valueOperations.getOperations().opsForSet().unionAndStore(key, otherKeys, destKey);
    }

    /**
     * Union all sets at given {@code keys} and store result in {@code destKey}.
     *
     * @param keys    must not be {@literal null}.
     * @param destKey must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/sunionstore">Redis Documentation: SUNIONSTORE</a>
     * @since 2.2
     */
    @Override
    public Long unionAndStore(Collection<K> keys, K destKey) {
        return valueOperations.getOperations().opsForSet().unionAndStore(keys, destKey);
    }

    /**
     * Diff all sets for given {@code key} and {@code otherKey}.
     *
     * @param key      must not be {@literal null}.
     * @param otherKey must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/sdiff">Redis Documentation: SDIFF</a>
     */
    @Override
    public Set<V> difference(K key, K otherKey) {
        return valueOperations.getOperations().opsForSet().difference(key, otherKey);
    }

    /**
     * Diff all sets for given {@code key} and {@code otherKeys}.
     *
     * @param key       must not be {@literal null}.
     * @param otherKeys must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/sdiff">Redis Documentation: SDIFF</a>
     */
    @Override
    public Set<V> difference(K key, Collection<K> otherKeys) {
        return valueOperations.getOperations().opsForSet().difference(key, otherKeys);
    }

    /**
     * Diff all sets for given {@code keys}.
     *
     * @param keys must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/sdiff">Redis Documentation: SDIFF</a>
     * @since 2.2
     */
    @Override
    public Set<V> difference(Collection<K> keys) {
        return valueOperations.getOperations().opsForSet().difference(keys);
    }

    /**
     * Diff all sets for given {@code key} and {@code otherKey} and store result in {@code destKey}.
     *
     * @param key      must not be {@literal null}.
     * @param otherKey must not be {@literal null}.
     * @param destKey  must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/sdiffstore">Redis Documentation: SDIFFSTORE</a>
     */
    @Override
    public Long differenceAndStore(K key, K otherKey, K destKey) {
        return valueOperations.getOperations().opsForSet().differenceAndStore(key, otherKey, destKey);
    }

    /**
     * Diff all sets for given {@code key} and {@code otherKeys} and store result in {@code destKey}.
     *
     * @param key       must not be {@literal null}.
     * @param otherKeys must not be {@literal null}.
     * @param destKey   must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/sdiffstore">Redis Documentation: SDIFFSTORE</a>
     */
    @Override
    public Long differenceAndStore(K key, Collection<K> otherKeys, K destKey) {
        return valueOperations.getOperations().opsForSet().differenceAndStore(key, otherKeys, destKey);
    }

    /**
     * Diff all sets for given {@code keys} and store result in {@code destKey}.
     *
     * @param keys    must not be {@literal null}.
     * @param destKey must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/sdiffstore">Redis Documentation: SDIFFSTORE</a>
     * @since 2.2
     */
    @Override
    public Long differenceAndStore(Collection<K> keys, K destKey) {
        return valueOperations.getOperations().opsForSet().differenceAndStore(keys, destKey);
    }

    /**
     * Get all elements of set at {@code key}.
     *
     * @param key must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/smembers">Redis Documentation: SMEMBERS</a>
     */
    @Override
    public Set<V> members(K key) {
        return valueOperations.getOperations().opsForSet().members(key);
    }

    /**
     * Get random element from set at {@code key}.
     *
     * @param key must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/srandmember">Redis Documentation: SRANDMEMBER</a>
     */
    @Override
    public V randomMember(K key) {
        return valueOperations.getOperations().opsForSet().randomMember(key);
    }

    /**
     * Get {@code count} distinct random elements from set at {@code key}.
     *
     * @param key   must not be {@literal null}.
     * @param count nr of members to return
     * @return empty {@link Set} if {@code key} does not exist.
     * @throws IllegalArgumentException if count is negative.
     * @see <a href="https://redis.io/commands/srandmember">Redis Documentation: SRANDMEMBER</a>
     */
    @Override
    public Set<V> distinctRandomMembers(K key, long count) {
        return valueOperations.getOperations().opsForSet().distinctRandomMembers(key, count);
    }

    /**
     * Get {@code count} random elements from set at {@code key}.
     *
     * @param key   must not be {@literal null}.
     * @param count nr of members to return.
     * @return empty {@link List} if {@code key} does not exist or {@literal null} when used in pipeline / transaction.
     * @throws IllegalArgumentException if count is negative.
     * @see <a href="https://redis.io/commands/srandmember">Redis Documentation: SRANDMEMBER</a>
     */
    @Override
    public List<V> randomMembers(K key, long count) {
        return valueOperations.getOperations().opsForSet().randomMembers(key, count);
    }

    /**
     * Iterate over elements in set at {@code key}. <br />
     * <strong>Important:</strong> Call {@link Cursor#close()} when done to avoid resource leak.
     *
     * @param key
     * @param options
     * @return
     * @since 1.4
     */
    @Override
    public Cursor<V> scan(K key, ScanOptions options) {
        return valueOperations.getOperations().opsForSet().scan(key, options);
    }

    @Override
    public RedisOperations<K, V> getOperations() {
        return valueOperations.getOperations();
    }
}
