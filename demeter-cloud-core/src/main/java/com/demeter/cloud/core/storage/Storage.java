package com.demeter.cloud.core.storage;

import org.springframework.core.io.Resource;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * <p>封装Dcloud项目Storage类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-17 00:58
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
public interface Storage {
    /**
     * 存储一个文件对象
     *
     * @param inputStream   文件输入流
     * @param contentLength 文件长度
     * @param contentType   文件类型
     * @param keyName       文件名
     */
    void store(InputStream inputStream, long contentLength, String contentType, String keyName);

    /**
     * 加载资源-文件key
     *
     * @return
     */
    Stream<Path> loadAll();

    /**
     * 加载资源-文件key
     *
     * @return
     */
    Path load(String keyName);

    /**
     * 加载资源-文件key
     *
     * @return
     */
    Resource loadAsResource(String keyName);

    /**
     * 删除
     *
     * @return
     */
    void delete(String keyName);

    /**
     * 生成URL
     *
     * @return
     */
    String generateUrl(String keyName);
}
