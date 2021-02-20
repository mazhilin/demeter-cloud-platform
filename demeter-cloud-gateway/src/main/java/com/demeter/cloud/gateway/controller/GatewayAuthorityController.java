package com.demeter.cloud.gateway.controller;

import com.demeter.cloud.model.persistence.controller.BaseController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>封装Dcloud项目GatewayAuthorityController类.<br></p>
 * <p>权限相关<br></p>
 *
 * @author Powered by marklin 2021-02-19 03:25
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
@RestController
@RequestMapping("/api/gateway/authority")
@Validated
public class GatewayAuthorityController extends BaseController {
}
