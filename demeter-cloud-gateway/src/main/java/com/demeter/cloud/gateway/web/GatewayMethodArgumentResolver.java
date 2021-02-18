package com.demeter.cloud.gateway.web;

import com.demeter.cloud.gateway.annotation.GatewayApiToken;
import com.demeter.cloud.gateway.manager.GatewayApiTokenManager;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * <p>封装Qicloud项目GatewayMethodArgumentResolver类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-19 02:12
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
public class GatewayMethodArgumentResolver implements HandlerMethodArgumentResolver {
    public static final String LOGIN_TOKEN_KEY = "X-Program-Token";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(Integer.class)
                && parameter.hasParameterAnnotation(GatewayApiToken.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer container, NativeWebRequest request,
                                  WebDataBinderFactory factory) throws Exception {
        String token = request.getHeader(LOGIN_TOKEN_KEY);
        if (token == null || token.isEmpty()) {
            return null;
        }

        return GatewayApiTokenManager.getUserId(token);
    }
}
