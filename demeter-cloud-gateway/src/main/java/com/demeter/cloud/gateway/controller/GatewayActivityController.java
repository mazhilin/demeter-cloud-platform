package com.demeter.cloud.gateway.controller;

import com.demeter.cloud.model.persistence.controller.BaseController;
import com.demeter.cloud.model.service.ActivityInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>封装Dcloud项目GatewayActivityController类.<br></p>
 * <p>活动相关<br></p>
 *
 * @author Powered by marklin 2021-02-19 03:28
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
@RestController
@RequestMapping("/api/gateway/activity")
@Validated
public class GatewayActivityController extends BaseController {
    @Autowired
    private ActivityInfoService activityInfoService;
    @GetMapping("/activityInfoList")
    public Object ActivityInfoList(){
        logger.info("【请求开始】活动信息列表查询");
        return activityInfoService.queryActivityInfoList();
    }
}
