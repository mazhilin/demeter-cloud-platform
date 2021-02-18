package com.demeter.cloud.gateway.utils;

import com.demeter.cloud.core.util.ResponseUtil;
import com.demeter.cloud.gateway.web.GatewayWebResponse;

/**
 * <p>封装Qicloud项目GatewayWebResponseUtil类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-19 02:59
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
public class GatewayWebResponseUtil extends ResponseUtil {
    public static Object fail(GatewayWebResponse response) {
        return fail(response.code(), response.message());
    }

}
