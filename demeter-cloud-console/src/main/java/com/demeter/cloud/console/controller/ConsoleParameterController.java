package com.demeter.cloud.console.controller;

import com.alibaba.fastjson.JSONObject;
import com.demeter.cloud.console.annotation.RequiresPermissionsDesc;
import com.demeter.cloud.core.utils.ResponseUtil;
import com.demeter.cloud.core.validator.Order;
import com.demeter.cloud.core.validator.Sort;
import com.demeter.cloud.model.entity.PublicParameter;
import com.demeter.cloud.framework.persistence.controller.BaseController;
import com.demeter.cloud.model.service.PublicParameterService;
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
 * <p>封装Dcloud项目ConsoleParameterController类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-17 03:05
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
@RestController
@RequestMapping(value = "/admin/parameter/")
@Validated
public class ConsoleParameterController extends BaseController {
    @Autowired
    private PublicParameterService publicParameterService;


    @RequiresPermissions("admin:parameter:list")
    @RequiresPermissionsDesc(
            menu = {"配置中心", "参数管理"},
            button = "列表")
    @GetMapping(value = "list")
    public Object list(
            String name,
            String code,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer limit,
            @Sort @RequestParam(defaultValue = "create_time") String sort,
            @Order @RequestParam(defaultValue = "desc") String order) {
        logger.info("【请求开始】配置中心->参数管理->列表,请求参数,name:{},code:{},page:{}", name, code, page);
        List<PublicParameter> parameterList =
                publicParameterService.queryList(name, code, page, limit, sort, order);
        long total = PageInfo.of(parameterList).getTotal();
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", parameterList);
        logger.info("【请求结束】【请求开始】配置中心->参数管理->列表:响应结果:{}", JSONObject.toJSONString(data));
        return ResponseUtil.ok(data);
    }

    private Object validate(PublicParameter parameter) {
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

    @RequiresPermissions("admin:parameter:create")
    @RequiresPermissionsDesc(
            menu = {"配置中心", "参数管理"},
            button = "新增")
    @PostMapping(value = "create")
    public Object create(@RequestBody PublicParameter parameter) {
        logger.info("【请求开始】配置中心->参数管理->新增,请求参数:{}", JSONObject.toJSONString(parameter));

        Object error = validate(parameter);
        if (error != null) {
            return error;
        }
        publicParameterService.add(parameter);

        logger.info("【请求结束】配置中心->参数管理->新增,响应结果:{}", JSONObject.toJSONString(parameter));
        return ResponseUtil.ok(parameter);
    }


    @RequiresPermissions("admin:parameter:show")
    @RequiresPermissionsDesc(menu = {"配置中心", "参数管理"}, button = "详情")
    @GetMapping(value = "show")
    public Object show(@NotNull Integer id) {
        logger.info("【请求开始】配置中心->参数管理->详情,请求参数,id:{}", id);

        PublicParameter parameter = publicParameterService.queryById(id);

        logger.info("【请求结束】配置中心->参数管理->详情,响应结果:{}", JSONObject.toJSONString(parameter));
        return ResponseUtil.ok(parameter);
    }

    /**
     * @param parameter 参数对象
     * @return
     */
    @RequiresPermissions("admin:parameter:edit")
    @RequiresPermissionsDesc(
            menu = {"配置中心", "参数管理"},
            button = "编辑")
    @PostMapping(value = "edit")
    public Object edit(@RequestBody PublicParameter parameter) {
        logger.info("【请求开始】配置中心->参数管理->编辑,请求参数:{}", JSONObject.toJSONString(parameter));

        Object error = validate(parameter);
        if (error != null) {
            return error;
        }
        Integer parameterId = parameter.getId();
        if (parameterId == null) {
            return ResponseUtil.badArgument();
        }
        if (publicParameterService.update(parameter) == 0) {
            logger.error("配置中心->参数管理-编辑 ,错误：{}", "编辑数据失败！");
            return ResponseUtil.updatedDataFailed();
        }
        logger.info("【请求结束】配置中心->参数管理->编辑,响应结果:{}", JSONObject.toJSONString(parameter));
        return ResponseUtil.ok(parameter);
    }

    /**
     * @param parameter 参数对象
     * @return
     */
    @RequiresPermissions("admin:parameter:update")
    @RequiresPermissionsDesc(
            menu = {"配置中心", "参数管理"},
            button = "更新")
    @PostMapping(value = "update")
    public Object update(@RequestBody PublicParameter parameter) {
        logger.info("【请求开始】配置中心->参数管理->更新,请求参数:{}", JSONObject.toJSONString(parameter));

        Object error = validate(parameter);
        if (error != null) {
            return error;
        }
        Integer parameterId = parameter.getId();
        if (parameterId == null) {
            return ResponseUtil.badArgument();
        }
        if (publicParameterService.update(parameter) == 0) {
            logger.error("配置中心->参数管理-更新 ,错误：{}", "更新数据失败！");
            return ResponseUtil.updatedDataFailed();
        }
        logger.info("【请求结束】配置中心->参数管理->更新,响应结果:{}", JSONObject.toJSONString(parameter));
        return ResponseUtil.ok(parameter);
    }

    @RequiresPermissions("admin:parameter:delete")
    @RequiresPermissionsDesc(
            menu = {"配置中心", "参数管理"},
            button = "删除")
    @PostMapping(value = "delete")
    public Object delete(@RequestBody PublicParameter parameter) {
        logger.info("【请求开始】配置中心->参数管理->删除,请求参数:{}", JSONObject.toJSONString(parameter));
        publicParameterService.deleteById(parameter.getId());
        logger.info("【请求结束】配置中心->参数管理->删除,响应结果:{}", "成功!");
        return ResponseUtil.ok();
    }
}
