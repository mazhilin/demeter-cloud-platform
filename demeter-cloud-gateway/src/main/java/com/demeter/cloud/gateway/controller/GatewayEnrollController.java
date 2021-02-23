package com.demeter.cloud.gateway.controller;

import com.demeter.cloud.gateway.utils.IpHelper;
import com.demeter.cloud.model.entity.EnrollInfo;
import com.demeter.cloud.model.persistence.controller.BaseController;
import com.demeter.cloud.model.service.CustomerUserService;
import com.demeter.cloud.model.service.EnrollInfoService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * <p>封装Dcloud项目GatewayEnrollController类.<br></p>
 * <p>报名相关<br></p>
 *
 * @author Powered by marklin 2021-02-19 03:23
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
@RestController
@RequestMapping(value = "/api/gateway/enroll")
@Validated
public class GatewayEnrollController extends BaseController {

    @Resource
    private EnrollInfoService enrollInfoService;
    @Resource
    private CustomerUserService customerUserService;

    @PostMapping("/addEnroll")
    @ResponseStatus(code = HttpStatus.OK,reason = "报名成功")
    public Object addEnroll(@RequestBody EnrollInfo enrollInfo){
        logger.info("========>报名");
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ip = IpHelper.getIpAddr(req);
        logger.info("客户端ip地址:======>"+ip);

        /*组装对象*/
        enrollInfo.setIpAddress(ip);
        enrollInfo.setCreateBy("system");
        enrollInfo.setIsDelete(false);
        enrollInfo.setUpdateBy("system");
        LocalDateTime nowTime = LocalDateTime.now(ZoneOffset.UTC);
        enrollInfo.setCreateTime(nowTime);
        enrollInfo.setStatus(true);


        enrollInfoService.add(enrollInfo);
        return "报名成功";
    }
}
