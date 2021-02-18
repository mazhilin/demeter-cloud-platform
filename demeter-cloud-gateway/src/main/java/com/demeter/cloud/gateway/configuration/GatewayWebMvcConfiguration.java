package com.demeter.cloud.gateway.configuration;

import com.demeter.cloud.gateway.web.GatewayMethodArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * <p>封装Qicloud项目GatawayWebMvcConfiguration类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-19 02:10
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
@Configuration
public class GatewayWebMvcConfiguration implements WebMvcConfigurer {
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new GatewayMethodArgumentResolver());
    }
}
