package com.demeter.cloud.core.constant;

import com.demeter.cloud.core.BaseHandler;

/**
 * <p>封装Dcloud项目UserType类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-17 00:07
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
public enum UserType implements BaseHandler {
    SYSTEM_USER(0, "系统用户"),
    ADMIN_USER(1, "管理用户"),
    EMPLOYEE_USER(2, "员工用户");

    private Integer code;
    private String message;

    UserType(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static UserType getInstance(Integer code) {
        if (code != null) {
            for (UserType tmp : UserType.values()) {
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
