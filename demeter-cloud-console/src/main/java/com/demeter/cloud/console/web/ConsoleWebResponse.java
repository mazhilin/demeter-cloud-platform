package com.demeter.cloud.console.web;

import com.demeter.cloud.core.BaseHandler;

/**
 * <p>封装Qicloud项目ConsoleWebResponse类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-16 23:34
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
public enum ConsoleWebResponse implements BaseHandler {
    ADMIN_INVALID_ACCOUNT(600, "账户格式不符合规定"),
    ADMIN_INVALID_NAME(600, "账戶名称不符合规定"),
    ADMIN_INVALID_PASSWORD(601, "账户密码长度不能小于6"),
    ADMIN_NAME_EXIST(602, "账户已经存在"),
    // ADMIN_ALTER_NOT_ALLOWED(603,""),
    // ADMIN_DELETE_NOT_ALLOWED(604,""),
    ADMIN_INVALID_ACCOUNT_OR_PASSWORD(605, "用户帐号或密码不正确"),
    ADMIN_LOCK_ACCOUNT(606, "用户帐号已锁定不可用"),
    ADMIN_INVALID_AUTH(607, "认证失败"),
    GOODS_UPDATE_NOT_ALLOWED(610, "商品已经在订单或购物车中，不能修改"),
    GOODS_NAME_EXIST(611, "商品名已经存在"),
    ORDER_CONFIRM_NOT_ALLOWED(620, "当前订单状态不能确认收货"),
    ORDER_REFUND_FAILED(621, "当前订单状态不能退款"),
    ORDER_REPLY_EXIST(622, "订单商品已回复！"),
    ADMIN_INVALID_OLD_PASSWORD(623, "原始密码不正确！"),
    // USER_INVALID_NAME(630,""),
    // USER_INVALID_PASSWORD(631,""),
    // USER_INVALID_MOBILE(632,""),
    // USER_NAME_EXIST(633,""),
    // USER_MOBILE_EXIST(634,""),
    ROLE_NAME_EXIST(640, "角色已经存在"),
    ROLE_SUPER_SUPERMISSION(641, "当前角色的超级权限不能变更"),
    ARTICLE_NAME_EXIST(642, "公告或通知文章已经存在");

    private Integer code;
    private String message;

    ConsoleWebResponse(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ConsoleWebResponse getInstance(Integer code) {
        if (code != null) {
            for (ConsoleWebResponse tmp : ConsoleWebResponse.values()) {
                if (tmp.code.intValue() == code.intValue()) {
                    return tmp;
                }
            }
        }
        return null;
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
