package com.demeter.cloud.model.redis;

import org.springframework.data.redis.core.SetOperations;

/**
 * <p>封装Dcloud项目SetOperationsService类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-23 15:40
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
public interface SetOperationsService <K, V> extends SetOperations<K, V> {
}
