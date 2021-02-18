package com.demeter.cloud.gateway.controller;

import com.demeter.cloud.core.util.ResponseUtil;
import com.demeter.cloud.gateway.manager.GatewayHomeManager;
import com.demeter.cloud.model.persistence.controller.BaseController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * <p>封装Qicloud项目GatewayHomeController类.<br></p>
 * <p>首页<br></p>
 *
 * @author Powered by marklin 2021-02-19 03:03
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
@RestController
@RequestMapping("/api/gateway/home")
@Validated
public class GatewayHomeController extends BaseController {


    /**
     * 清除缓存
     *
     * @param key key
     * @return
     */
    @GetMapping("/cache")
    public Object cache(@NotNull String key) {
        logger.info("【请求开始】缓存已清除,请求参数,key:{}", key);

        if (!key.equals("demeter_gateway_cache")) {
            logger.error("缓存已清除出错:非本平台标识！！！");
            return ResponseUtil.fail();
        }

        // 清除缓存
        new GatewayHomeManager().clearAll();
        logger.info("【请求结束】缓存已清除成功!");
        return ResponseUtil.ok("缓存已清除");
    }
}
