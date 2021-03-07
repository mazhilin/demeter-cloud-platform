package com.demeter.cloud.core.constant;



import com.demeter.cloud.ConstantHandler;

import java.util.Objects;

/**
 * <p>封装Dcloud项目PaymentType类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-22 02:05
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
public enum PaymentType implements ConstantHandler {
    /**
     * 微信支付-wechat
     */
    WECHAT("wechat", "微信支付"),
    /**
     * 支付宝支付-alipay
     */
    ALIPAY("alipay", "支付宝支付"),
    /**
     * 银联支付-unionPay
     */
    UNIONPAY("unionPay", "银联支付"),
    ;


    /**
     * 编码-code
     */
    private final String code;
    /**
     * 描述-message
     */
    private final String message;

    PaymentType(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static PaymentType getInstance(String code) {
        if (code != null) {
            for (PaymentType type : PaymentType.values()) {
                if (Objects.equals(type.code, code)) {
                    return type;
                }
            }
        }
        return null;
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
