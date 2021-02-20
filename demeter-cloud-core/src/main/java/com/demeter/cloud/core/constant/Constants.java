package com.demeter.cloud.core.constant;

/**
 * <p>封装Dcloud项目Constants类.<br></p>
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

    public static final String REQUEST_WITH = "X-Requested-With";
    public static final String REQUEST_HTTP = "XMLHttpRequest";
    public static final String FORWARDED_IP = "x-forwarded-for";
    public static final String PROXY_IP = "Proxy-Client-IP";
    public static final String WL_PROXY_IP = "WL-Proxy-Client-IP";
    public static final String HTTP_CLIENT_IP = "HTTP_CLIENT_IP";
    public static final String HTTP_FORWARDED_IP = "HTTP_X_FORWARDED_FOR";
    public static final String IP_FOUR_LOCALHOST = "127.0.0.1";
    public static final String IP_SIX_LOCALHOST = "0:0:0:0:0:0:0:1";
    public static final String IP_SEPARATOR = ",";
    public static final String UNKNOWN = "unknown";
    public static final String OS_NAME = "os.name";

    /**
     * 系统统一密码- DEFAULT_PASSWORD[123456@Abc]
     */
    public static final String DEFAULT_PASSWORD = "123456@Abc";

    /**
     * 系统统一邮箱- DEFAULT_EMAIL[123456@Abc]
     */
    public static final String DEFAULT_EMAIL = "@dcloud.com";

    /**
     * 系统密码参数- password
     */
    public static final String PASSWORD = "password";
    /**
     * 重复密码的参数名 -confirmPassword
     */
    public static final String CONFIRM_PASSWORD = "confirmPassword";
}
