package com.demeter.cloud.model.redis;

import org.springframework.data.redis.core.ZSetOperations;

/**
 * <p>封装Dcloud项目ZSetOperationsService类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-23 15:41
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
public interface ZSetOperationsService <K, V> extends ZSetOperations<K, V> {
}
