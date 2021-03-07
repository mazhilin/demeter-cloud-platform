package com.demeter.cloud.core.storage;


import com.demeter.cloud.framework.utils.CheckEmptyUtil;
import com.demeter.cloud.model.exception.BusinessException;
import io.minio.MinioClient;
import io.minio.ObjectStat;
import io.minio.PutObjectOptions;
import io.minio.errors.*;
import io.minio.messages.ObjectMetadata;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
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
    private static final Logger logger = LoggerFactory.getLogger(MinioStorage.class);
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

    private MinioClient instance;


    @PostConstruct
    public void init() {
        try {
            instance = new MinioClient(endpoint, accessKeyId, accessKeySecret);
        } catch (InvalidPortException | InvalidEndpointException e) {
            e.printStackTrace();
        }
    }

    private String getBaseUrl() {
        return endpoint + bucketName + "/";
    }


    /**
     * 创建 bucket
     *
     * @param bucketName
     */
    public void makeBucket(String bucketName) {
        try {
            boolean isExist = instance.bucketExists(bucketName);
            if (!isExist) {
                instance.makeBucket(bucketName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 判断 bucket是否存在
     *
     * @param bucketName
     * @return
     */
    public boolean bucketExists(String bucketName) {
        try {
            return instance.bucketExists(bucketName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
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
        try {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.userMetadata();
            PutObjectOptions options = new PutObjectOptions(inputStream.available(), -1);
            options.setContentType(contentType);
            instance.putObject(bucketName, keyName, inputStream, options);

            ObjectStat objectStat = statObject(bucketName, keyName);
            if (CheckEmptyUtil.isNotEmpty(objectStat)) {
                logger.info("私有云存储：{}", objectStat);
            }
        } catch (BusinessException | ErrorResponseException | InsufficientDataException | InternalException | InvalidBucketNameException | InvalidKeyException | InvalidResponseException | IOException | NoSuchAlgorithmException | XmlParserException exception) {
            logger.error("私有云存储 keyName：{} ,失败：{}", keyName, exception.getMessage());
            exception.printStackTrace();
        }
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
        try {
            URL url = new URL(getBaseUrl() + keyName);
            Resource resource = new UrlResource(url);
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                return null;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 删除
     *
     * @param keyName
     * @return
     */
    @SneakyThrows
    @Override
    public void delete(String keyName) {
        instance.removeObject(bucketName, keyName);
    }

    /**
     * 生成URL
     *
     * @param keyName
     * @return
     */
    @Override
    public String generateUrl(String keyName) {
        return getBaseUrl() + keyName;
    }


    /**
     * 获取对象的元数据
     *
     * @param bucketName 存储桶名称
     * @param objectName 存储桶里的对象名称
     * @return
     */
    @SneakyThrows
    protected ObjectStat statObject(String bucketName, String objectName) {
        boolean flag = bucketExists(bucketName);
        if (flag) {
            ObjectStat statObject = instance.statObject(bucketName, objectName);
            return statObject;
        }
        return null;
    }
}
