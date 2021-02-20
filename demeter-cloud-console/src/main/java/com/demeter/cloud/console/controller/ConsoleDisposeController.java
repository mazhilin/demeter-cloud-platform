package com.demeter.cloud.console.controller;

import com.alibaba.fastjson.JSONObject;
import com.demeter.cloud.console.annotation.RequiresPermissionsDesc;
import com.demeter.cloud.core.util.ResponseUtil;
import com.demeter.cloud.core.validator.Order;
import com.demeter.cloud.core.validator.Sort;
import com.demeter.cloud.model.entity.ConfigParameter;
import com.demeter.cloud.model.persistence.controller.BaseController;
import com.demeter.cloud.model.service.ConfigParameterService;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>封装Dcloud项目ConsoleDisposeController类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-17 03:11
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
@RestController
@RequestMapping(value = "/api/console/dispose/")
@Validated
public class ConsoleDisposeController extends BaseController {

    @Autowired
    private ConfigParameterService configParameterService;


    @RequiresPermissions("admin:config:list")
    @RequiresPermissionsDesc(
            menu = {"配置中心", "配置管理"},
            button = "查询")
    @GetMapping(value = "list")
    public Object list(
            String name,
            String code,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer limit,
            @Sort @RequestParam(defaultValue = "create_time") String sort,
            @Order @RequestParam(defaultValue = "desc") String order) {
        logger.info("【请求开始】配置中心->配置管理->查询,请求参数,name:{},code:{},page:{}", name, code, page);
        List<ConfigParameter> parameterList =
                configParameterService.queryList(name, code, page, limit, sort, order);
        long total = PageInfo.of(parameterList).getTotal();
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", parameterList);
        logger.info("【请求结束】【请求开始】配置中心->配置管理->查询:响应结果:{}", JSONObject.toJSONString(data));
        return ResponseUtil.ok(data);
    }

    private Object validate(ConfigParameter parameter) {
        String name = parameter.getName();
        if (StringUtils.isEmpty(name)) {
            return ResponseUtil.badArgument();
        }
        String code = parameter.getCode();
        if (StringUtils.isEmpty(code)) {
            return ResponseUtil.badArgument();
        }
        return null;
    }

    @RequiresPermissions("admin:config:show")
    @RequiresPermissionsDesc(menu = {"配置中心", "配置管理"}, button = "详情")
    @GetMapping(value = "show")
    public Object read(@NotNull Integer id) {
        logger.info("【请求开始】配置中心->配置管理->详情,请求参数,id:{}", id);

        ConfigParameter parameter = configParameterService.queryById(id);

        logger.info("【请求结束】配置中心->配置管理->详情,响应结果:{}", JSONObject.toJSONString(parameter));
        return ResponseUtil.ok(parameter);
    }

    @RequiresPermissions("admin:config:create")
    @RequiresPermissionsDesc(
            menu = {"配置中心", "配置管理"},
            button = "添加")
    @PostMapping(value = "create")
    public Object create(@RequestBody ConfigParameter parameter) {
        logger.info("【请求开始】配置中心->配置管理->添加,请求参数:{}", JSONObject.toJSONString(parameter));

        Object error = validate(parameter);
        if (error != null) {
            return error;
        }
        configParameterService.add(parameter);

        logger.info("【请求结束】配置中心->配置管理->添加,响应结果:{}", JSONObject.toJSONString(parameter));
        return ResponseUtil.ok(parameter);
    }

    /**
     * @param parameter 参数对象
     * @return
     */
    @RequiresPermissions("admin:config:edit")
    @RequiresPermissionsDesc(
            menu = {"配置中心", "配置管理"},
            button = "编辑")
    @PostMapping(value = "edit")
    public Object edit(@RequestBody ConfigParameter parameter) {
        logger.info("【请求开始】配置中心->配置管理->编辑,请求参数:{}", JSONObject.toJSONString(parameter));

        Object error = validate(parameter);
        if (error != null) {
            return error;
        }
        Integer parameterId = parameter.getId();
        if (parameterId == null) {
            return ResponseUtil.badArgument();
        }
        if (configParameterService.update(parameter) == 0) {
            logger.error("配置中心->配置管理-编辑 ,错误：{}", "编辑数据失败！");
            return ResponseUtil.updatedDataFailed();
        }
        logger.info("【请求结束】配置中心->配置管理->编辑,响应结果:{}", JSONObject.toJSONString(parameter));
        return ResponseUtil.ok(parameter);
    }

    /**
     * @param parameter 参数对象
     * @return
     */
    @RequiresPermissions("admin:config:update")
    @RequiresPermissionsDesc(
            menu = {"配置中心", "配置管理"},
            button = "更新")
    @PostMapping(value = "update")
    public Object update(@RequestBody ConfigParameter parameter) {
        logger.info("【请求开始】配置中心->配置管理->更新,请求参数:{}", JSONObject.toJSONString(parameter));

        Object error = validate(parameter);
        if (error != null) {
            return error;
        }
        Integer parameterId = parameter.getId();
        if (parameterId == null) {
            return ResponseUtil.badArgument();
        }
        if (configParameterService.update(parameter) == 0) {
            logger.error("配置中心->配置管理-更新 ,错误：{}", "更新数据失败！");
            return ResponseUtil.updatedDataFailed();
        }
        logger.info("【请求结束】配置中心->配置管理->更新,响应结果:{}", JSONObject.toJSONString(parameter));
        return ResponseUtil.ok(parameter);
    }

    @RequiresPermissions("admin:config:delete")
    @RequiresPermissionsDesc(
            menu = {"配置中心", "配置管理"},
            button = "删除")
    @PostMapping("/delete")
    public Object delete(@RequestBody ConfigParameter parameter) {
        logger.info("【请求开始】配置中心->配置管理->删除,请求参数:{}", JSONObject.toJSONString(parameter));
        configParameterService.deleteById(parameter.getId());
        logger.info("【请求结束】配置中心->配置管理->删除,响应结果:{}", "成功!");
        return ResponseUtil.ok();
    }
}
