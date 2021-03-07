package com.demeter.cloud.core.constant;

/**
 * <p>封装Dcloud项目RedisConstant类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2020-12-07 23:02
 * @version 1.0.0
 * <p>Copyright © 2018-2020 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
public class RedisConstant {
    public  static final Long APP_DATA_EXPIRY_TIME = 60 * 60 * 24 * 30L;
    /**
     * 统一设置token有效时间为30天[60 * 60 * 24 * 30L]
     */
    public  static final Long APP_TOKEN_EXPIRY_TIME = 60 * 60 * 24 * 30L;
    /**
     * 统一设置会员有效时间为365天[60 * 60 * 24 * 30L]
     */
    public static final Long APP_MEMBER_EXPIRY_TIME = 365 * 24 * 60 * 60 * 1000L;
    /**
     * 发送短信通用key设计
     */
    public static final String APP_MEMBER_USER_KEY = "app:member:user:id:";
    /**
     * 发送短信通用key设计
     */
    public static final String APP_MESSAGE_PHONE_KEY = "app:message:phone:common:";
    /**
     * 发送短信限制请求总次数
     */
    public static final String APP_MESSAGE_PHONE_TOTLAL_KEY = "app:message:phone:total:";
    /**
     * 发送短信限制请求时效
     */
    public static final String APP_MESSAGE_PHONE_TIME_KEY = "app:message:phone:time:";
    /**
     * Customer用户登录Key
     */
    public static final String APP_CUSTOMER_USER_TOKEN_KEY = "app:customer:token:";
    /**
     * System用户登录Key
     */
    public static final String APP_SYSTEM_USER_TOKEN_KEY = "app:system:token:";
    /**
     * APP端用户登录Key
     */
    public static final String APP_USER_LOGIN_TOKEN_KEY = "app:user:login:token:";

    /**
     * 市
     */
    public static final String APP_SYSTEM_CITY_CITY_LIST = "app:system:city:cityList:";

    /**
     * 省
     */
    public static final String APP_SYSTEM_CITY_PROVINCE_LIST = "app:system:city:provinceList:";

    /**
     * 省/市/区级联
     */
    public static final String APP_SYSTEM_REGION_LIST = "app:system:region:list";

    /**
     * 快速录入客户
     */
    public static final String APP_EMPLOEE_INPUT_CUSTOMER = "app:employee:input:customer:";
    /**
     * 后台商品分类数据缓存key设计
     */
    public static final String GOODS_CATEGORY_PARENT_LIST = "goods:category:parent:list";

    /**
     * Member用户登录Key
     */
    public static final String APP_MEMBER_USER_ID_KEY = "app:member:user:id:";
}
