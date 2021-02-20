package com.demeter.cloud.core.constant;

/**
 * 封装Dcloud项目Messages类.<br>
 *
 * <p>//TODO...<br>
 *
 * @author Powered by marklin 2020-12-06 20:51
 * @version 1.0.0
 *     <p>Copyright © 2018-2020 Pivotal Cloud Technology Systems Incorporated. All rights reserved.
 *     <br>
 */
public final class MessageConstant {
  /** 公共参数:短信API产品名称（短信产品名固定，无需修改） */
  public static final String REGION_ID = "cn-qingdao";
  /** 公共参数:设置连接操作 */
  public static final String CONNECT = "sun.net.client.defaultConnectTimeout";
  /** 公共参数:设置读操作 */
  public static final String READ = "sun.net.client.defaultReadTimeout";
  /** 公共参数:设置最大超时时间 */
  public static final String MAX_TIME_OUT = "10000";
  /** 验证码模板参数 尊敬的用户，您的注册会员动态密码为：${code}，请勿泄漏于他人！ 个人-SMS_135840015 SMS_141596549 SMS_152281697 */
  public static final String CAPTCHA_CODE = "SMS_206600151";
  /** 支付模板 */
  public static final String PAYMENT_CODE = "SMS_205710313";
  /**
   * 短信模板参数 尊敬的${name}：您好！恭喜注册账户,用户名是：${mtname}，密码是：${password} ,请您牢记您的用户名与密码! 个人-SMS_146290856
   * SMS_139232932 SMS_149416760 SMS_152281693
   */
  public static final String MESSAGE_CODE = "SMS_152281693";
  /**
   *定义通用响应码-OK
   *
   */
  public  static  final   String RESPONSE_CODE="OK";

  public static final  String LIMIT_CONTROL_CODE="isv.BUSINESS_LIMIT_CONTROL";
}
