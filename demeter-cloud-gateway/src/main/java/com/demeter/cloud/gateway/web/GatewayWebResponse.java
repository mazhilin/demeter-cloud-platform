package com.demeter.cloud.gateway.web;


import com.demeter.cloud.framework.BaseHandler;

/**
 * <p>封装Dcloud项目GatewayWebResponse类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-19 02:56
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
public enum GatewayWebResponse implements BaseHandler {
    AUTH_INVALID_ACCOUNT(700, "账号密码不对"),
    AUTH_CAPTCHA_UNSUPPORT(701, "小程序后台验证码服务不支持"),
    AUTH_CAPTCHA_FREQUENCY(702, "验证码未超时1分钟，不能发送"),
    AUTH_CAPTCHA_UNMATCH(703, "验证码错误"),
    AUTH_NAME_REGISTERED(704, "用户名已注册"),
    AUTH_MOBILE_REGISTERED(705, "手机号已注册"),
    AUTH_MOBILE_UNREGISTERED(706,
            "手机号未注册"),
    AUTH_INVALID_MOBILE(707, "手机号格式不正确"),
    AUTH_OPENID_UNACCESS(708,
            "获取腾讯官方 openid失败"),
    AUTH_OPENID_BINDED(709, "openid已绑定账号"),

    GOODS_UNSHELVE(710, "商品已下架"), GOODS_NO_STOCK(711, "库存不足"), GOODS_UNKNOWN(712, "未找到对应的商品"),
    // GOODS_INVALID(713, ""),

    // 订单当前状态下不支持用户的操作，例如商品未发货状态用户执行确认收货是不可能的。
    ORDER_UNKNOWN(720, "订单不存在"), ORDER_INVALID(721, "不是当前用户的订单"),
    // ORDER_CHECKOUT_FAIL(722, ""),
    ORDER_REPAY_OPERATION(722, ""), ORDER_NOT_COMMENT(723, "订单不能评价"), ORDER_PAY_FAIL(724,
            "订单支付失败"), ORDER_INVALID_OPERATION(725, "订单不能取消"), ORDER_COMMENTED(726, "订单商品已评价"), ORDER_COMMENT_EXPIRED(
            727, "当前订单评价时间已过期"), ORDER_CONFIRM_OPERATION(728, "订单不能确认收货"), ORDER_DEL_OPERATION(729, "订单不能删除"),
    // ORDER_CANCEL_FAIL(820,""),

    GROUPON_EXPIRED(730, "团购活动已过期!"),

    COUPON_EXCEED_LIMIT(740, "优惠券已领完"), COUPON_NOT_RECEIVE(741, "非领取优惠券"), COUPON_CODE_INVALID(742,
            "优惠券券码不正确"), COUPON_EXPIRED(743, "优惠券已经过期"), COUPON_NOT_CHANGE(744, "非兑换优惠券"),

    // 提现错误
    APPLY_WITHDRAWAL_FAIL(850, "申请提现金额不能大于可提现金额"), INVALID_COUPON(851, "无效购物券"), INVALID_USER(852, "无效用户"),
    MEMBER_VIP_USER(852, "已经是标准会员!"), MEMBER_VVIP_USER(853, "已经是高级会员!");

    private Integer code;
    private String message;

    GatewayWebResponse(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 编码
     *
     * @return
     */
    @Override
    public Integer code() {
        return code;
    }

    /**
     * 消息
     *
     * @return
     */
    @Override
    public String message() {
        return message;
    }
}
