package com.demeter.cloud.core.manager;

/**
 * <p>封装Qicloud项目Manager类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-19 02:31
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
@FunctionalInterface
public interface Manager {
    /**
     * 获取应用实例
     */
    void getInstance();
}
