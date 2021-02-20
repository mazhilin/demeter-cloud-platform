package com.demeter.cloud.core.storage;

import org.springframework.core.io.Resource;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * <p>封装Dcloud项目MinioStorage类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-17 01:03
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
public class MinioStorage implements Storage{
    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    /**
     * 存储一个文件对象
     *
     * @param inputStream   文件输入流
     * @param contentLength 文件长度
     * @param contentType   文件类型
     * @param keyName       文件名
     */
    @Override
    public void store(InputStream inputStream, long contentLength, String contentType, String keyName) {

    }

    /**
     * 加载资源-文件key
     *
     * @return
     */
    @Override
    public Stream<Path> loadAll() {
        return null;
    }

    /**
     * 加载资源-文件key
     *
     * @param keyName
     * @return
     */
    @Override
    public Path load(String keyName) {
        return null;
    }

    /**
     * 加载资源-文件key
     *
     * @param keyName
     * @return
     */
    @Override
    public Resource loadAsResource(String keyName) {
        return null;
    }

    /**
     * 删除
     *
     * @param keyName
     * @return
     */
    @Override
    public void delete(String keyName) {

    }

    /**
     * 生成URL
     *
     * @param keyName
     * @return
     */
    @Override
    public String generateUrl(String keyName) {
        return null;
    }
}
