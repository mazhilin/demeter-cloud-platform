package com.demeter.cloud.gateway.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>封装Qicloud项目GatewayLoginUser类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-19 02:08
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface GatewayApiToken {
}
