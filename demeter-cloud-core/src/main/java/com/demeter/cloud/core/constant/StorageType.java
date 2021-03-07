package com.demeter.cloud.core.constant;


import com.demeter.cloud.ConstantHandler;

import java.util.Objects;

/**
 * <p>封装Dcloud项目StorageType类.<br></p>
 * <p>定义云存储StorageType类型<br></p>
 *
 * @author Powered by marklin 2021-02-20 22:22
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
public enum StorageType implements ConstantHandler {
    /**
     * 本地存储-local
     */
    LOCAL("local", "本地存储"),
    /**
     * 阿里云存储-aliyun
     */
    ALIYUN("aliyun", "阿里云存储"),
    /**
     * 腾讯云存储-tencent
     */
    TENCENT("tencent", "腾讯云存储"),
    /**
     * 七牛云存储-qiniu
     */
    QINIU("qiniu", "七牛云存储"),
    /**
     * 私有云存储-minio
     */
    MINIO("minio", "私有云存储");


    /**
     * 编码-code
     */
    private final String code;
    /**
     * 描述-message
     */
    private final String message;

    StorageType(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static StorageType getInstance(String code) {
        if (code != null) {
            for (StorageType type : StorageType.values()) {
                if (Objects.equals(type.code, code)) {
                    return type;
                }
            }
        }
        return LOCAL;
    }

    /**
     * 编码
     *
     * @return code
     */
    @Override
    public String code() {
        return code;
    }

    /**
     * 消息
     *
     * @return message
     */
    @Override
    public String message() {
        return message;
    }
}
