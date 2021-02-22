package com.demeter.cloud.core.constant;

/**
 * <p>封装Dcloud项目Tokens类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-22 17:05
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
public final class Tokens {
    /**
     * 定义后台登录全局Token标识别-X-Console-Web-Token
     */
    public static final String CONSOLE_LOGIN_TOKEN = "X-Console-Web-Token";

    /**
     * 定义前端登录全局Token标识别-X-Gateway-Web-Token
     */
    public static final String GATEWAY_LOGIN_TOKEN = "X-Gateway-Web-Token";

    /**
     * 定义小程序登录全局Token标识别-X-Program-Web-Token
     */
    public static final String PROGRAM_LOGIN_TOKEN = "X-Program-Web-Token";
}
