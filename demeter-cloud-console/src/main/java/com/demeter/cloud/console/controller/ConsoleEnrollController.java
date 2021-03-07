package com.demeter.cloud.console.controller;

import com.alibaba.fastjson.JSONObject;
import com.demeter.cloud.console.annotation.RequiresPermissionsDesc;
import com.demeter.cloud.core.utils.ResponseUtil;
import com.demeter.cloud.core.validator.Order;
import com.demeter.cloud.core.validator.Sort;
import com.demeter.cloud.model.entity.EnrollInfo;
import com.demeter.cloud.framework.persistence.controller.BaseController;
import com.demeter.cloud.model.service.EnrollInfoService;
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
 * <p>封装Dcloud项目ConsoleEnrollController类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-17 03:33
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
@RestController
@RequestMapping(value = "/admin/enroll/")
@Validated
public class ConsoleEnrollController extends BaseController {
    @Autowired
    private EnrollInfoService enrollInfoService;


    @RequiresPermissions("admin:enroll:list")
    @RequiresPermissionsDesc(
            menu = {"活动中心", "报名管理"},
            button = "列表")
    @GetMapping(value = "list")
    public Object list(
            String name,
            String code,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer limit,
            @Sort @RequestParam(defaultValue = "create_time") String sort,
            @Order @RequestParam(defaultValue = "desc") String order) {
        logger.info("【请求开始】活动中心->报名管理->列表,请求参数,name:{},code:{},page:{}", name, code, page);
        List<EnrollInfo> enrollInfoList =
                enrollInfoService.queryList(name, code, page, limit, sort, order);
        long total = PageInfo.of(enrollInfoList).getTotal();
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", enrollInfoList);
        logger.info("【请求结束】【请求开始】活动中心->报名管理->列表:响应结果:{}", JSONObject.toJSONString(data));
        return ResponseUtil.ok(data);
    }

    private Object validate(EnrollInfo enroll) {
        Integer subjectId = enroll.getSubjectId();
        if (StringUtils.isEmpty(subjectId)) {
            return ResponseUtil.badArgument();
        }
        return null;
    }

    @RequiresPermissions("admin:enroll:create")
    @RequiresPermissionsDesc(
            menu = {"活动中心", "报名管理"},
            button = "新增")
    @PostMapping(value ="create")
    public Object create(@RequestBody EnrollInfo enroll) {
        logger.info("【请求开始】活动中心->报名管理->新增,请求参数:{}", JSONObject.toJSONString(enroll));

        Object error = validate(enroll);
        if (error != null) {
            return error;
        }
        enrollInfoService.add(enroll);

        logger.info("【请求结束】活动中心->报名管理->新增,响应结果:{}", JSONObject.toJSONString(enroll));
        return ResponseUtil.ok(enroll);
    }


    @RequiresPermissions("admin:enroll:show")
    @RequiresPermissionsDesc(menu = {"活动中心", "报名管理"}, button = "详情")
    @GetMapping(value = "show")
    public Object show(@NotNull Integer id) {
        logger.info("【请求开始】活动中心->报名管理->详情,请求参数,id:{}", id);

        EnrollInfo enroll = enrollInfoService.queryById(id);

        logger.info("【请求结束】活动中心->报名管理->详情,响应结果:{}", JSONObject.toJSONString(enroll));
        return ResponseUtil.ok(enroll);
    }


    /**
     * @param enroll 参数对象
     * @return
     */
    @RequiresPermissions("admin:enroll:edit")
    @RequiresPermissionsDesc(
            menu = {"活动中心", "报名管理"},
            button = "编辑")
    @PostMapping(value ="edit")
    public Object edit(@RequestBody EnrollInfo enroll) {
        logger.info("【请求开始】活动中心->报名管理->编辑,请求参数:{}", JSONObject.toJSONString(enroll));

        Object error = validate(enroll);
        if (error != null) {
            return error;
        }
        Integer enrollId = enroll.getId();
        if (enrollId == null) {
            return ResponseUtil.badArgument();
        }
        if (enrollInfoService.update(enroll) == 0) {
            logger.error("活动中心->报名管理-编辑 ,错误：{}", "编辑数据失败！");
            return ResponseUtil.updatedDataFailed();
        }
        logger.info("【请求结束】活动中心->报名管理->编辑,响应结果:{}", JSONObject.toJSONString(enroll));
        return ResponseUtil.ok(enroll);
    }

    /**
     * @param enroll 参数对象
     * @return
     */
    @RequiresPermissions("admin:enroll:update")
    @RequiresPermissionsDesc(
            menu = {"活动中心", "报名管理"},
            button = "更新")
    @PostMapping(value ="update")
    public Object update(@RequestBody EnrollInfo enroll) {
        logger.info("【请求开始】活动中心->报名管理->更新,请求参数:{}", JSONObject.toJSONString(enroll));

        Object error = validate(enroll);
        if (error != null) {
            return error;
        }
        Integer enrollId = enroll.getId();
        if (enrollId == null) {
            return ResponseUtil.badArgument();
        }
        if (enrollInfoService.update(enroll) == 0) {
            logger.error("活动中心->报名管理-更新 ,错误：{}", "更新数据失败！");
            return ResponseUtil.updatedDataFailed();
        }
        logger.info("【请求结束】活动中心->报名管理->更新,响应结果:{}", JSONObject.toJSONString(enroll));
        return ResponseUtil.ok(enroll);
    }

    @RequiresPermissions("admin:enroll:delete")
    @RequiresPermissionsDesc(
            menu = {"活动中心", "报名管理"},
            button = "删除")
    @PostMapping(value ="delete")
    public Object delete(@RequestBody EnrollInfo enroll) {
        logger.info("【请求开始】活动中心->报名管理->删除,请求参数:{}", JSONObject.toJSONString(enroll));
        enrollInfoService.deleteById(enroll.getId());
        logger.info("【请求结束】活动中心->报名管理->删除,响应结果:{}", "成功!");
        return ResponseUtil.ok();
    }
}
