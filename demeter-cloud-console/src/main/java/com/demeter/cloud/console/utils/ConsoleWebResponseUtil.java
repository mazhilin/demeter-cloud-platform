package com.demeter.cloud.console.utils;

import com.demeter.cloud.console.web.ConsoleWebResponse;
import com.demeter.cloud.core.utils.ResponseUtil;

/**
 * <p>封装Dcloud项目ConsoleWebResponseUtil类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-16 23:40
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
public class ConsoleWebResponseUtil extends ResponseUtil {

    /**
     * 按枚举返回错误响应结果
     *
     * @param response
     * @return
     */
    public static Object fail(ConsoleWebResponse response) {
        return fail(response.code(), response.message());
    }
}
