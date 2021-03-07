package com.demeter.cloud.model.redis.service;

import com.demeter.cloud.persistence.service.BaseService;
import com.demeter.cloud.model.redis.RedisOperationsService;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.core.query.SortQuery;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.core.types.RedisClientInfo;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Closeable;
import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * <p>封装Dcloud项目RedisOperationsServiceImpl类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-23 15:48
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
@Service(value = "redisOperationsService")
public class RedisOperationsServiceImpl<K, V> extends BaseService implements RedisOperationsService<K, V> {
    @Resource(name = "stringRedisTemplate")
    private ValueOperations<K, V> valueOperations;

    /**
     * Executes the given action within a Redis connection. Application exceptions thrown by the action object get
     * propagated to the caller (can only be unchecked) whenever possible. Redis exceptions are transformed into
     * appropriate DAO ones. Allows for returning a result object, that is a domain object or a collection of domain
     * objects. Performs automatic serialization/deserialization for the given objects to and from binary data suitable
     * for the Redis storage. Note: Callback code is not supposed to handle transactions itself! Use an appropriate
     * transaction manager. Generally, callback code must not touch any Connection lifecycle methods, like close, to let
     * the template do its work.
     *
     * @param action callback object that specifies the Redis action. Must not be {@literal null}.
     * @return a result object returned by the action or <tt>null</tt>
     */
    @Override
    public <T> T execute(RedisCallback<T> action) {
        return valueOperations.getOperations().execute(action);
    }

    /**
     * Executes a Redis session. Allows multiple operations to be executed in the same session enabling 'transactional'
     * capabilities through {@link #multi()} and {@link #watch(Collection)} operations.
     *
     * @param session session callback. Must not be {@literal null}.
     * @return result object returned by the action or <tt>null</tt>
     */
    @Override
    public <T> T execute(SessionCallback<T> session) {
        return valueOperations.getOperations().execute(session);
    }

    /**
     * Executes the given action object on a pipelined connection, returning the results. Note that the callback
     * <b>cannot</b> return a non-null value as it gets overwritten by the pipeline. This method will use the default
     * serializers to deserialize results
     *
     * @param action callback object to execute
     * @return list of objects returned by the pipeline
     */
    @Override
    public List<Object> executePipelined(RedisCallback<?> action) {
        return valueOperations.getOperations().executePipelined(action);
    }

    /**
     * Executes the given action object on a pipelined connection, returning the results using a dedicated serializer.
     * Note that the callback <b>cannot</b> return a non-null value as it gets overwritten by the pipeline.
     *
     * @param action           callback object to execute
     * @param resultSerializer The Serializer to use for individual values or Collections of values. If any returned
     *                         values are hashes, this serializer will be used to deserialize both the key and value
     * @return list of objects returned by the pipeline
     */
    @Override
    public List<Object> executePipelined(RedisCallback<?> action, RedisSerializer<?> resultSerializer) {
        return valueOperations.getOperations().executePipelined(action, resultSerializer);
    }

    /**
     * Executes the given Redis session on a pipelined connection. Allows transactions to be pipelined. Note that the
     * callback <b>cannot</b> return a non-null value as it gets overwritten by the pipeline.
     *
     * @param session Session callback
     * @return list of objects returned by the pipeline
     */
    @Override
    public List<Object> executePipelined(SessionCallback<?> session) {
        return valueOperations.getOperations().executePipelined(session);
    }

    /**
     * Executes the given Redis session on a pipelined connection, returning the results using a dedicated serializer.
     * Allows transactions to be pipelined. Note that the callback <b>cannot</b> return a non-null value as it gets
     * overwritten by the pipeline.
     *
     * @param session          Session callback
     * @param resultSerializer
     * @return list of objects returned by the pipeline
     */
    @Override
    public List<Object> executePipelined(SessionCallback<?> session, RedisSerializer<?> resultSerializer) {
        return valueOperations.getOperations().executePipelined(session, resultSerializer);
    }

    /**
     * Executes the given {@link RedisScript}
     *
     * @param script The script to execute
     * @param keys   Any keys that need to be passed to the script
     * @param args   Any args that need to be passed to the script
     * @return The return value of the script or null if {@link RedisScript#getResultType()} is null, likely indicating a
     * throw-away status reply (i.e. "OK")
     */
    @Override
    public <T> T execute(RedisScript<T> script, List<K> keys, Object... args) {
        return valueOperations.getOperations().execute(script, keys, args);
    }

    /**
     * Executes the given {@link RedisScript}, using the provided {@link RedisSerializer}s to serialize the script
     * arguments and result.
     *
     * @param script           The script to execute
     * @param argsSerializer   The {@link RedisSerializer} to use for serializing args
     * @param resultSerializer The {@link RedisSerializer} to use for serializing the script return value
     * @param keys             Any keys that need to be passed to the script
     * @param args             Any args that need to be passed to the script
     * @return The return value of the script or null if {@link RedisScript#getResultType()} is null, likely indicating a
     * throw-away status reply (i.e. "OK")
     */
    @Override
    public <T> T execute(RedisScript<T> script, RedisSerializer<?> argsSerializer, RedisSerializer<T> resultSerializer, List<K> keys, Object... args) {
        return valueOperations.getOperations().execute(script, argsSerializer, resultSerializer, keys, args);
    }

    /**
     * Allocates and binds a new {@link RedisConnection} to the actual return type of the method. It is up to the caller
     * to free resources after use.
     *
     * @param callback must not be {@literal null}.
     * @return
     * @since 1.8
     */
    @Override
    public <T extends Closeable> T executeWithStickyConnection(RedisCallback<T> callback) {
        return valueOperations.getOperations().executeWithStickyConnection(callback);
    }

    /**
     * Determine if given {@code key} exists.
     *
     * @param key must not be {@literal null}.
     * @return
     * @see <a href="https://redis.io/commands/exists">Redis Documentation: EXISTS</a>
     */
    @Override
    public Boolean hasKey(K key) {
        return valueOperations.getOperations().hasKey(key);
    }

    /**
     * Count the number of {@code keys} that exist.
     *
     * @param keys must not be {@literal null}.
     * @return The number of keys existing among the ones specified as arguments. Keys mentioned multiple times and
     * existing are counted multiple times.
     * @see <a href="https://redis.io/commands/exists">Redis Documentation: EXISTS</a>
     * @since 2.1
     */
    @Override
    public Long countExistingKeys(Collection<K> keys) {
        return valueOperations.getOperations().countExistingKeys(keys);
    }

    /**
     * Delete given {@code key}.
     *
     * @param key must not be {@literal null}.
     * @return {@literal true} if the key was removed.
     * @see <a href="https://redis.io/commands/del">Redis Documentation: DEL</a>
     */
    @Override
    public Boolean delete(K key) {
        return valueOperations.getOperations().delete(key);
    }

    /**
     * Delete given {@code keys}.
     *
     * @param keys must not be {@literal null}.
     * @return The number of keys that were removed. {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/del">Redis Documentation: DEL</a>
     */
    @Override
    public Long delete(Collection<K> keys) {
        return valueOperations.getOperations().delete(keys);
    }

    /**
     * Unlink the {@code key} from the keyspace. Unlike with {@link #delete(Object)} the actual memory reclaiming here
     * happens asynchronously.
     *
     * @param key must not be {@literal null}.
     * @return The number of keys that were removed. {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/unlink">Redis Documentation: UNLINK</a>
     * @since 2.1
     */
    @Override
    public Boolean unlink(K key) {
        return valueOperations.getOperations().unlink(key);
    }

    /**
     * Unlink the {@code keys} from the keyspace. Unlike with {@link #delete(Collection)} the actual memory reclaiming
     * here happens asynchronously.
     *
     * @param keys must not be {@literal null}.
     * @return The number of keys that were removed. {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/unlink">Redis Documentation: UNLINK</a>
     * @since 2.1
     */
    @Override
    public Long unlink(Collection<K> keys) {
        return valueOperations.getOperations().unlink(keys);
    }

    /**
     * Determine the type stored at {@code key}.
     *
     * @param key must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/type">Redis Documentation: TYPE</a>
     */
    @Override
    public DataType type(K key) {
        return valueOperations.getOperations().type(key);
    }

    /**
     * Find all keys matching the given {@code pattern}.
     *
     * @param pattern must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/keys">Redis Documentation: KEYS</a>
     */
    @Override
    public Set<K> keys(K pattern) {
        return valueOperations.getOperations().keys(pattern);
    }

    /**
     * Return a random key from the keyspace.
     *
     * @return {@literal null} no keys exist or when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/randomkey">Redis Documentation: RANDOMKEY</a>
     */
    @Override
    public K randomKey() {
        return valueOperations.getOperations().randomKey();
    }

    /**
     * Rename key {@code oldKey} to {@code newKey}.
     *
     * @param oldKey must not be {@literal null}.
     * @param newKey must not be {@literal null}.
     * @see <a href="https://redis.io/commands/rename">Redis Documentation: RENAME</a>
     */
    @Override
    public void rename(K oldKey, K newKey) {
        valueOperations.getOperations().rename(oldKey, newKey);
    }

    /**
     * Rename key {@code oleName} to {@code newKey} only if {@code newKey} does not exist.
     *
     * @param oldKey must not be {@literal null}.
     * @param newKey must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/renamenx">Redis Documentation: RENAMENX</a>
     */
    @Override
    public Boolean renameIfAbsent(K oldKey, K newKey) {
        return valueOperations.getOperations().renameIfAbsent(oldKey, newKey);
    }

    /**
     * Set time to live for given {@code key}.
     *
     * @param key     must not be {@literal null}.
     * @param timeout
     * @param unit    must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     */
    @Override
    public Boolean expire(K key, long timeout, TimeUnit unit) {
        return valueOperations.getOperations().expire(key, timeout, unit);
    }

    /**
     * Set the expiration for given {@code key} as a {@literal date} timestamp.
     *
     * @param key  must not be {@literal null}.
     * @param date must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     */
    @Override
    public Boolean expireAt(K key, Date date) {
        return valueOperations.getOperations().expireAt(key, date);
    }

    /**
     * Remove the expiration from given {@code key}.
     *
     * @param key must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/persist">Redis Documentation: PERSIST</a>
     */
    @Override
    public Boolean persist(K key) {
        return valueOperations.getOperations().persist(key);
    }

    /**
     * Move given {@code key} to database with {@code index}.
     *
     * @param key     must not be {@literal null}.
     * @param dbIndex
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/move">Redis Documentation: MOVE</a>
     */
    @Override
    public Boolean move(K key, int dbIndex) {
        return valueOperations.getOperations().move(key, dbIndex);
    }

    /**
     * Retrieve serialized version of the value stored at {@code key}.
     *
     * @param key must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/dump">Redis Documentation: DUMP</a>
     */
    @Override
    public byte[] dump(K key) {
        return valueOperations.getOperations().dump(key);
    }

    /**
     * Create {@code key} using the {@code serializedValue}, previously obtained using {@link #dump(Object)}.
     *
     * @param key        must not be {@literal null}.
     * @param value      must not be {@literal null}.
     * @param timeToLive
     * @param unit       must not be {@literal null}.
     * @param replace    use {@literal true} to replace a potentially existing value instead of erroring.
     * @see <a href="https://redis.io/commands/restore">Redis Documentation: RESTORE</a>
     * @since 2.1
     */
    @Override
    public void restore(K key, byte[] value, long timeToLive, TimeUnit unit, boolean replace) {
        valueOperations.getOperations().restore(key, value, timeToLive, unit, replace);
    }

    /**
     * Get the time to live for {@code key} in seconds.
     *
     * @param key must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/ttl">Redis Documentation: TTL</a>
     */
    @Override
    public Long getExpire(K key) {
        return valueOperations.getOperations().getExpire(key);
    }

    /**
     * Get the time to live for {@code key} in and convert it to the given {@link TimeUnit}.
     *
     * @param key      must not be {@literal null}.
     * @param timeUnit must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @since 1.8
     */
    @Override
    public Long getExpire(K key, TimeUnit timeUnit) {
        return valueOperations.getOperations().getExpire(key, timeUnit);
    }

    /**
     * Sort the elements for {@code query}.
     *
     * @param query must not be {@literal null}.
     * @return the results of sort. {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/sort">Redis Documentation: SORT</a>
     */
    @Override
    public List<V> sort(SortQuery<K> query) {
        return valueOperations.getOperations().sort(query);
    }

    /**
     * Sort the elements for {@code query} applying {@link RedisSerializer}.
     *
     * @param query            must not be {@literal null}.
     * @param resultSerializer
     * @return the deserialized results of sort. {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/sort">Redis Documentation: SORT</a>
     */
    @Override
    public <T> List<T> sort(SortQuery<K> query, RedisSerializer<T> resultSerializer) {
        return valueOperations.getOperations().sort(query, resultSerializer);
    }

    /**
     * Sort the elements for {@code query} applying {@link BulkMapper}.
     *
     * @param query      must not be {@literal null}.
     * @param bulkMapper
     * @return the deserialized results of sort. {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/sort">Redis Documentation: SORT</a>
     */
    @Override
    public <T> List<T> sort(SortQuery<K> query, BulkMapper<T, V> bulkMapper) {
        return valueOperations.getOperations().sort(query, bulkMapper);
    }

    /**
     * Sort the elements for {@code query} applying {@link BulkMapper} and {@link RedisSerializer}.
     *
     * @param query            must not be {@literal null}.
     * @param bulkMapper
     * @param resultSerializer
     * @return the deserialized results of sort. {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/sort">Redis Documentation: SORT</a>
     */
    @Override
    public <T, S> List<T> sort(SortQuery<K> query, BulkMapper<T, S> bulkMapper, RedisSerializer<S> resultSerializer) {
        return valueOperations.getOperations().sort(query, bulkMapper, resultSerializer);
    }

    /**
     * Sort the elements for {@code query} and store result in {@code storeKey}.
     *
     * @param query    must not be {@literal null}.
     * @param storeKey must not be {@literal null}.
     * @return number of values. {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/sort">Redis Documentation: SORT</a>
     */
    @Override
    public Long sort(SortQuery<K> query, K storeKey) {
        return valueOperations.getOperations().sort(query, storeKey);
    }

    /**
     * Watch given {@code key} for modifications during transaction started with {@link #multi()}.
     *
     * @param key must not be {@literal null}.
     * @see <a href="https://redis.io/commands/watch">Redis Documentation: WATCH</a>
     */
    @Override
    public void watch(K key) {
        valueOperations.getOperations().watch(key);
    }

    /**
     * Watch given {@code keys} for modifications during transaction started with {@link #multi()}.
     *
     * @param keys must not be {@literal null}.
     * @see <a href="https://redis.io/commands/watch">Redis Documentation: WATCH</a>
     */
    @Override
    public void watch(Collection<K> keys) {
        valueOperations.getOperations().watch(keys);
    }

    /**
     * Flushes all the previously {@link #watch(Object)} keys.
     *
     * @see <a href="https://redis.io/commands/unwatch">Redis Documentation: UNWATCH</a>
     */
    @Override
    public void unwatch() {
        valueOperations.getOperations().unwatch();
    }

    /**
     * Mark the start of a transaction block. <br>
     * Commands will be queued and can then be executed by calling {@link #exec()} or rolled back using {@link #discard()}
     * <p>
     *
     * @see <a href="https://redis.io/commands/multi">Redis Documentation: MULTI</a>
     */
    @Override
    public void multi() {
        valueOperations.getOperations().multi();
    }

    /**
     * Discard all commands issued after {@link #multi()}.
     *
     * @see <a href="https://redis.io/commands/discard">Redis Documentation: DISCARD</a>
     */
    @Override
    public void discard() {
        valueOperations.getOperations().discard();
    }

    /**
     * Executes all queued commands in a transaction started with {@link #multi()}. <br>
     * If used along with {@link #watch(Object)} the operation will fail if any of watched keys has been modified.
     *
     * @return List of replies for each executed command.
     * @see <a href="https://redis.io/commands/exec">Redis Documentation: EXEC</a>
     */
    @Override
    public List<Object> exec() {
        return valueOperations.getOperations().exec();
    }

    /**
     * Execute a transaction, using the provided {@link RedisSerializer} to deserialize any results that are byte[]s or
     * Collections of byte[]s. If a result is a Map, the provided {@link RedisSerializer} will be used for both the keys
     * and values. Other result types (Long, Boolean, etc) are left as-is in the converted results. Tuple results are
     * automatically converted to TypedTuples.
     *
     * @param valueSerializer The {@link RedisSerializer} to use for deserializing the results of transaction exec
     * @return The deserialized results of transaction exec
     */
    @Override
    public List<Object> exec(RedisSerializer<?> valueSerializer) {
        return valueOperations.getOperations().exec(valueSerializer);
    }

    /**
     * Request information and statistics about connected clients.
     *
     * @return {@link List} of {@link RedisClientInfo} objects.
     * @since 1.3
     */
    @Override
    public List<RedisClientInfo> getClientList() {
        return valueOperations.getOperations().getClientList();
    }

    /**
     * Closes a given client connection identified by {@literal ip:port} given in {@code client}.
     *
     * @param host of connection to close.
     * @param port of connection to close
     * @since 1.3
     */
    @Override
    public void killClient(String host, int port) {
        valueOperations.getOperations().killClient(host, port);
    }

    /**
     * Change redis replication setting to new master.
     *
     * @param host must not be {@literal null}.
     * @param port
     * @see <a href="https://redis.io/commands/slaveof">Redis Documentation: SLAVEOF</a>
     * @since 1.3
     */
    @Override
    public void slaveOf(String host, int port) {
        valueOperations.getOperations().slaveOf(host, port);
    }

    /**
     * Change server into master.
     *
     * @see <a href="https://redis.io/commands/slaveof">Redis Documentation: SLAVEOF</a>
     * @since 1.3
     */
    @Override
    public void slaveOfNoOne() {
        valueOperations.getOperations().slaveOfNoOne();
    }

    /**
     * Publishes the given message to the given channel.
     *
     * @param destination the channel to publish to, must not be {@literal null}.
     * @param message     message to publish
     * @return the number of clients that received the message
     * @see <a href="https://redis.io/commands/publish">Redis Documentation: PUBLISH</a>
     */
    @Override
    public void convertAndSend(String destination, Object message) {
        valueOperations.getOperations().convertAndSend(destination, message);
    }

    /**
     * Returns the cluster specific operations interface.
     *
     * @return never {@literal null}.
     * @since 1.7
     */
    @Override
    public ClusterOperations<K, V> opsForCluster() {
        return valueOperations.getOperations().opsForCluster();
    }

    /**
     * Returns geospatial specific operations interface.
     *
     * @return never {@literal null}.
     * @since 1.8
     */
    @Override
    public GeoOperations<K, V> opsForGeo() {
        return valueOperations.getOperations().opsForGeo();
    }

    /**
     * Returns geospatial specific operations interface bound to the given key.
     *
     * @param key must not be {@literal null}.
     * @return never {@literal null}.
     * @since 1.8
     */
    @Override
    public BoundGeoOperations<K, V> boundGeoOps(K key) {
        return valueOperations.getOperations().boundGeoOps(key);
    }

    /**
     * Returns the operations performed on hash values.
     *
     * @return hash operations
     */
    @Override
    public <HK, HV> HashOperations<K, HK, HV> opsForHash() {
        return valueOperations.getOperations().opsForHash();
    }

    /**
     * Returns the operations performed on hash values bound to the given key. * @param <HK> hash key (or field) type
     *
     * @param key Redis key
     * @return hash operations bound to the given key.
     */
    @Override
    public <HK, HV> BoundHashOperations<K, HK, HV> boundHashOps(K key) {
        return valueOperations.getOperations().boundHashOps(key);
    }

    /**
     * @return
     * @since 1.5
     */
    @Override
    public HyperLogLogOperations<K, V> opsForHyperLogLog() {
        return valueOperations.getOperations().opsForHyperLogLog();
    }

    /**
     * Returns the operations performed on list values.
     *
     * @return list operations
     */
    @Override
    public ListOperations<K, V> opsForList() {
        return valueOperations.getOperations().opsForList();
    }

    /**
     * Returns the operations performed on list values bound to the given key.
     *
     * @param key Redis key
     * @return list operations bound to the given key
     */
    @Override
    public BoundListOperations<K, V> boundListOps(K key) {
        return valueOperations.getOperations().boundListOps(key);
    }

    /**
     * Returns the operations performed on set values.
     *
     * @return set operations
     */
    @Override
    public SetOperations<K, V> opsForSet() {
        return valueOperations.getOperations().opsForSet();
    }

    /**
     * Returns the operations performed on set values bound to the given key.
     *
     * @param key Redis key
     * @return set operations bound to the given key
     */
    @Override
    public BoundSetOperations<K, V> boundSetOps(K key) {
        return valueOperations.getOperations().boundSetOps(key);
    }

    /**
     * Returns the operations performed on Streams.
     *
     * @return stream operations.
     * @since 2.2
     */
    @Override
    public <HK, HV> StreamOperations<K, HK, HV> opsForStream() {
        return valueOperations.getOperations().opsForStream();
    }

    /**
     * Returns the operations performed on Streams.
     *
     * @param hashMapper the {@link HashMapper} to use when converting {@link ObjectRecord}.
     * @return stream operations.
     * @since 2.2
     */
    @Override
    public <HK, HV> StreamOperations<K, HK, HV> opsForStream(HashMapper<? super K, ? super HK, ? super HV> hashMapper) {
        return valueOperations.getOperations().opsForStream(hashMapper);
    }

    /**
     * Returns the operations performed on Streams bound to the given key.
     *
     * @param key
     * @return stream operations.
     * @since 2.2
     */
    @Override
    public <HK, HV> BoundStreamOperations<K, HK, HV> boundStreamOps(K key) {
        return valueOperations.getOperations().boundStreamOps(key);
    }

    /**
     * Returns the operations performed on simple values (or Strings in Redis terminology).
     *
     * @return value operations
     */
    @Override
    public ValueOperations<K, V> opsForValue() {
        return valueOperations.getOperations().opsForValue();
    }

    /**
     * Returns the operations performed on simple values (or Strings in Redis terminology) bound to the given key.
     *
     * @param key Redis key
     * @return value operations bound to the given key
     */
    @Override
    public BoundValueOperations<K, V> boundValueOps(K key) {
        return valueOperations.getOperations().boundValueOps(key);
    }

    /**
     * Returns the operations performed on zset values (also known as sorted sets).
     *
     * @return zset operations
     */
    @Override
    public ZSetOperations<K, V> opsForZSet() {
        return valueOperations.getOperations().opsForZSet();
    }

    /**
     * Returns the operations performed on zset values (also known as sorted sets) bound to the given key.
     *
     * @param key Redis key
     * @return zset operations bound to the given key.
     */
    @Override
    public BoundZSetOperations<K, V> boundZSetOps(K key) {
        return valueOperations.getOperations().boundZSetOps(key);
    }

    /**
     * @return the key {@link RedisSerializer}.
     */
    @Override
    public RedisSerializer<?> getKeySerializer() {
        return valueOperations.getOperations().getKeySerializer();
    }

    /**
     * @return the value {@link RedisSerializer}.
     */
    @Override
    public RedisSerializer<?> getValueSerializer() {
        return valueOperations.getOperations().getValueSerializer();
    }

    /**
     * @return the hash key {@link RedisSerializer}.
     */
    @Override
    public RedisSerializer<?> getHashKeySerializer() {
        return valueOperations.getOperations().getHashKeySerializer();
    }

    /**
     * @return the hash value {@link RedisSerializer}.
     */
    @Override
    public RedisSerializer<?> getHashValueSerializer() {
        return valueOperations.getOperations().getHashValueSerializer();
    }

    /**
     * Set time to live for given {@code key}.
     *
     * @param key     must not be {@literal null}.
     * @param timeout must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @throws IllegalArgumentException if the timeout is {@literal null}.
     * @since 2.3
     */
    @Override
    public Boolean expire(K key, Duration timeout) {
        return valueOperations.getOperations().expire(key, timeout);
    }

    /**
     * Set the expiration for given {@code key} as a {@literal date} timestamp.
     *
     * @param key      must not be {@literal null}.
     * @param expireAt must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @throws IllegalArgumentException if the instant is {@literal null} or too large to represent as a {@code Date}.
     * @since 2.3
     */
    @Override
    public Boolean expireAt(K key, Instant expireAt) {
        return valueOperations.getOperations().expireAt(key, expireAt);
    }

    /**
     * Create {@code key} using the {@code serializedValue}, previously obtained using {@link #dump(Object)}.
     *
     * @param key        must not be {@literal null}.
     * @param value      must not be {@literal null}.
     * @param timeToLive
     * @param unit       must not be {@literal null}.
     * @see <a href="https://redis.io/commands/restore">Redis Documentation: RESTORE</a>
     */
    @Override
    public void restore(K key, byte[] value, long timeToLive, TimeUnit unit) {
        valueOperations.getOperations().restore(key, value, timeToLive, unit);
    }
}
