package com.demeter.cloud.console.controller;

import com.alibaba.fastjson.JSONObject;
import com.demeter.cloud.console.annotation.RequiresPermissionsDesc;
import com.demeter.cloud.core.util.ResponseUtil;
import com.demeter.cloud.core.validator.Order;
import com.demeter.cloud.core.validator.Sort;
import com.demeter.cloud.model.entity.ActivityInfo;
import com.demeter.cloud.model.persistence.controller.BaseController;
import com.demeter.cloud.model.service.ActivityInfoService;
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
 * <p>封装Dcloud项目ConsoleActivityController类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-17 03:32
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
@RestController
@RequestMapping(value = "/admin/activity/")
@Validated
public class ConsoleActivityController extends BaseController {

    @Autowired
    private ActivityInfoService activityInfoService;

    @RequiresPermissions("admin:activity:list")
    @RequiresPermissionsDesc(
            menu = {"活动中心", "活动管理"},
            button = "列表")
    @GetMapping(value ="list")
    public Object list(
            String name,
            String code,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer limit,
            @Sort @RequestParam(defaultValue = "create_time") String sort,
            @Order @RequestParam(defaultValue = "desc") String order) {
        logger.info("【请求开始】活动中心->活动管理->列表,请求参数,name:{},code:{},page:{}", name, code, page);
        List<ActivityInfo> activityInfoList =
                activityInfoService.queryList(name, code, page, limit, sort, order);
        long total = PageInfo.of(activityInfoList).getTotal();
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", activityInfoList);
        logger.info("【请求结束】【请求开始】活动中心->活动管理->列表:响应结果:{}", JSONObject.toJSONString(data));
        return ResponseUtil.ok(data);
    }

    private Object validate(ActivityInfo activity) {
        String code = activity.getCode();
        if (StringUtils.isEmpty(code)) {
            return ResponseUtil.badArgument();
        }
        return null;
    }

    @RequiresPermissions("admin:activity:create")
    @RequiresPermissionsDesc(
            menu = {"活动中心", "活动管理"},
            button = "新增")
    @PostMapping(value ="create")
    public Object create(@RequestBody ActivityInfo activity) {
        logger.info("【请求开始】活动中心->活动管理->新增,请求参数:{}", JSONObject.toJSONString(activity));

        Object error = validate(activity);
        if (error != null) {
            return error;
        }
        activityInfoService.add(activity);

        logger.info("【请求结束】活动中心->活动管理->新增,响应结果:{}", JSONObject.toJSONString(activity));
        return ResponseUtil.ok(activity);
    }


    @RequiresPermissions("admin:activity:show")
    @RequiresPermissionsDesc(menu = {"活动中心", "活动管理"}, button = "详情")
    @GetMapping(value = "show")
    public Object show(@NotNull Integer id) {
        logger.info("【请求开始】活动中心->活动管理->详情,请求参数,id:{}", id);

        ActivityInfo activity = activityInfoService.queryById(id);

        logger.info("【请求结束】活动中心->活动管理->详情,响应结果:{}", JSONObject.toJSONString(activity));
        return ResponseUtil.ok(activity);
    }


    /**
     * @param activity 参数对象
     * @return
     */
    @RequiresPermissions("admin:activity:edit")
    @RequiresPermissionsDesc(
            menu = {"活动中心", "活动管理"},
            button = "编辑")
    @PostMapping(value ="edit")
    public Object edit(@RequestBody ActivityInfo activity) {
        logger.info("【请求开始】活动中心->活动管理->编辑,请求参数:{}", JSONObject.toJSONString(activity));

        Object error = validate(activity);
        if (error != null) {
            return error;
        }
        Integer activityId = activity.getId();
        if (activityId == null) {
            return ResponseUtil.badArgument();
        }
        if (activityInfoService.update(activity) == 0) {
            logger.error("活动中心->活动管理-编辑 ,错误：{}", "编辑数据失败！");
            return ResponseUtil.updatedDataFailed();
        }
        logger.info("【请求结束】活动中心->活动管理->编辑,响应结果:{}", JSONObject.toJSONString(activity));
        return ResponseUtil.ok(activity);
    }

    /**
     * @param activity 参数对象
     * @return
     */
    @RequiresPermissions("admin:activity:update")
    @RequiresPermissionsDesc(
            menu = {"活动中心", "活动管理"},
            button = "更新")
    @PostMapping(value ="update")
    public Object update(@RequestBody ActivityInfo activity) {
        logger.info("【请求开始】活动中心->活动管理->更新,请求参数:{}", JSONObject.toJSONString(activity));

        Object error = validate(activity);
        if (error != null) {
            return error;
        }
        Integer activityId = activity.getId();
        if (activityId == null) {
            return ResponseUtil.badArgument();
        }
        if (activityInfoService.update(activity) == 0) {
            logger.error("活动中心->活动管理-更新 ,错误：{}", "更新数据失败！");
            return ResponseUtil.updatedDataFailed();
        }
        logger.info("【请求结束】活动中心->活动管理->更新,响应结果:{}", JSONObject.toJSONString(activity));
        return ResponseUtil.ok(activity);
    }

    @RequiresPermissions("admin:activity:delete")
    @RequiresPermissionsDesc(
            menu = {"活动中心", "活动管理"},
            button = "删除")
    @PostMapping(value ="delete")
    public Object delete(@RequestBody ActivityInfo activity) {
        logger.info("【请求开始】活动中心->活动管理->删除,请求参数:{}", JSONObject.toJSONString(activity));
        activityInfoService.deleteById(activity.getId());
        logger.info("【请求结束】活动中心->活动管理->删除,响应结果:{}", "成功!");
        return ResponseUtil.ok();
    }

}
