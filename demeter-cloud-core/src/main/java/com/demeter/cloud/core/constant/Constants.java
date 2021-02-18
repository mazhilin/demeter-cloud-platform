package com.demeter.cloud.core.constant;

/**
 * <p>封装Qicloud项目Constants类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2020-12-04 09:46
 * @version 1.0.0
 * <p>Copyright © 2018-2020 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
public final class Constants {
    /**
     * MD5加密公共盐
     */
    public static final String PUBLIC_SALT = "demetercloud";
    /**
     * 定义日期格式常量
     */
    public static final String FORMAT = "yyyyMMdd";

    /**
     * 封装字符最大长度
     */
    public static final Integer MAX_LENGTH = 6;
    /**
     * 统一的字符编码格式
     */
    public static final String UNIFIED_CODE = "UTF-8";
    public static final String DEFAULT_AVATAR = "https://avatar.csdnimg.cn/8/A/0/2_qiguliuxing.jpg";

    public static final String DEFAULT_ORDER_FIX = "商礼优品小程序订单：";

    public static final String MISS_PARAMS = "缺少必要参数";

    /**
     * 设置缓存 6小时，有效期单位 ： 分钟
     */
    public static final Long CACHE_EXPIRE_MINUTES = 60 * 6L;
}
