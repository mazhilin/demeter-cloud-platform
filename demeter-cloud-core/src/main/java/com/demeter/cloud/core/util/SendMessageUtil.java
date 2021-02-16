package com.demeter.cloud.core.util;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.demeter.cloud.core.constant.MessageConstant;

import java.util.Map;

/**
 * 封装Qicloud项目SendMessageUtil类.<br>
 *
 * <p>//TODO...<br>
 *
 * @author Powered by marklin 2020-12-07 22:22
 * @version 1.0.0
 *     <p>Copyright © 2018-2020 Pivotal Cloud Technology Systems Incorporated. All rights reserved.
 *     <br>
 */
public class SendMessageUtil {

  /**
   * 发送短信的公共方法
   *
   * @param paramMap
   * @return
   * @throws ClientException
   */
  public static SendSmsResponse sendMessage(Map<String, String> paramMap) throws ClientException {
    // [1]设置连接操作并设置最大超时时间
    System.setProperty(MessageConstant.CONNECT, MessageConstant.MAX_TIME_OUT);
    System.setProperty(MessageConstant.READ, MessageConstant.MAX_TIME_OUT);
    // [2]注册网络中心以及授权访问
    IClientProfile profile = DefaultProfile.getProfile(
                MessageConstant.REGION_ID, paramMap.get("accessKeyId"), paramMap.get("accessSecret"));
    DefaultProfile.addEndpoint(
            MessageConstant.REGION_ID, paramMap.get("product"), paramMap.get("endpoint"));
    IAcsClient acsClient = new DefaultAcsClient(profile);
    // [3]组装请求对象
    SendSmsRequest request = new SendSmsRequest();
    // 必填:待发送手机号
    request.setPhoneNumbers(paramMap.get("phone"));
    // 必填:短信签名
    request.setSignName(paramMap.get("sign"));
    // 必填:短信模板
    request.setTemplateCode(paramMap.get("templateCode"));
    // 可选:模板中的变量替换JSON串
    request.setTemplateParam(paramMap.get("templateParam"));
    request.setOutId(paramMap.get("outId"));
    return acsClient.getAcsResponse(request);
  }
}
