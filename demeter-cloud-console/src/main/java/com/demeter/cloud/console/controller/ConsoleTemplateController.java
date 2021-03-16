package com.demeter.cloud.console.controller;

import com.alibaba.fastjson.JSONObject;
import com.demeter.cloud.console.annotation.RequiresPermissionsDesc;
import com.demeter.cloud.core.utils.DateTimeUtil;
import com.demeter.cloud.core.utils.IpAddressUtil;
import com.demeter.cloud.core.utils.ResponseUtil;
import com.demeter.cloud.core.validator.Order;
import com.demeter.cloud.core.validator.Sort;
import com.demeter.cloud.model.entity.ActivityTemplate;
import com.demeter.cloud.model.entity.AdminUser;
import com.demeter.cloud.persistence.controller.BaseController;
import com.demeter.cloud.model.service.ActivityTemplateService;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * <p>封装Dcloud项目ConsoleTemplateController类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-17 03:29
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
@RestController
@RequestMapping(value = "/admin/template/")
@Validated
public class ConsoleTemplateController extends BaseController {

    @Resource
    private ActivityTemplateService activityTemplateService;


    /**
     * 查询模板列表
     *
     * @param code  模板编码
     * @param name  模板名称
     * @param page  页码数
     * @param limit 条目数
     * @param sort  排序参数
     * @param order 排序
     * @return 返回列表
     */
    @RequiresPermissions("admin:template:list")
    @RequiresPermissionsDesc(
            menu = {"活动中心", "模板管理"},
            button = "列表")
    @GetMapping(value = "list")
    public Object list(
            @RequestParam(name = "code", required = false) String code,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer limit,
            @Sort @RequestParam(defaultValue = "create_time") String sort,
            @Order @RequestParam(defaultValue = "desc") String order) {
        logger.info("【请求开始】活动中心->模板管理->列表,请求参数,name:{},code:{},page:{}", name, code, page);
        List<ActivityTemplate> templateInfoList =
                activityTemplateService.queryList(name, code, page, limit, sort, order);
        long total = PageInfo.of(templateInfoList).getTotal();
        Map<String, Object> data = Maps.newConcurrentMap();
        data.put("total", total);
        data.put("items", templateInfoList);
        logger.info("【请求结束】【请求开始】活动中心->模板管理->列表:响应结果:{}", JSONObject.toJSONString(data));
        return ResponseUtil.ok(data);
    }

    private Object validate(ActivityTemplate template) {
        String name = template.getName();
        if (StringUtils.isEmpty(name)) {
            return ResponseUtil.badArgument();
        }
        return null;
    }

    @RequiresPermissions("admin:template:create")
    @RequiresPermissionsDesc(
            menu = {"活动中心", "模板管理"},
            button = "新增")
    @PostMapping(value = "create")
    public Object create(@RequestBody ActivityTemplate template) {
        logger.info("【请求开始】活动中心->模板管理->新增,请求参数:{}", JSONObject.toJSONString(template));

        Object error = validate(template);
        if (error != null) {
            return error;
        }
        template.setCode(
                DateTimeUtil.getDate(LocalDateTime.now()) + DateTimeUtil.getDateTime(LocalDateTime.now()));
        AdminUser adminUser = (AdminUser) SecurityUtils.getSubject().getPrincipal();
        template.setCreateBy(adminUser.getId().toString());
        template.setUpdateBy(adminUser.getId().toString());
        template.setCreateTime(LocalDateTime.now());
        template.setUpdateTime(LocalDateTime.now());
        activityTemplateService.add(template);

        logger.info("【请求结束】活动中心->模板管理->新增,响应结果:{}", JSONObject.toJSONString(template));
        return ResponseUtil.ok(template);
    }


    @RequiresPermissions("admin:template:show")
    @RequiresPermissionsDesc(menu = {"活动中心", "模板管理"}, button = "详情")
    @GetMapping(value = "show")
    public Object show(@NotNull Integer id) {
        logger.info("【请求开始】活动中心->模板管理->详情,请求参数,id:{}", id);

        ActivityTemplate template = activityTemplateService.queryById(id);

        logger.info("【请求结束】活动中心->模板管理->详情,响应结果:{}", JSONObject.toJSONString(template));
        return ResponseUtil.ok(template);
    }


    /**
     * @param template 参数对象
     * @return
     */
    @RequiresPermissions("admin:template:edit")
    @RequiresPermissionsDesc(
            menu = {"活动中心", "模板管理"},
            button = "编辑")
    @PostMapping(value = "edit")
    public Object edit(@RequestBody ActivityTemplate template) {
        logger.info("【请求开始】活动中心->模板管理->编辑,请求参数:{}", JSONObject.toJSONString(template));

        Object error = validate(template);
        if (error != null) {
            return error;
        }
        Integer templateId = template.getId();
        if (templateId == null) {
            return ResponseUtil.badArgument();
        }
        if (activityTemplateService.update(template) == 0) {
            logger.error("活动中心->模板管理-编辑 ,错误：{}", "编辑数据失败！");
            return ResponseUtil.updatedDataFailed();
        }
        logger.info("【请求结束】活动中心->模板管理->编辑,响应结果:{}", JSONObject.toJSONString(template));
        return ResponseUtil.ok(template);
    }

    /**
     * @param template 参数对象
     * @return
     */
    @RequiresPermissions("admin:template:update")
    @RequiresPermissionsDesc(
            menu = {"活动中心", "模板管理"},
            button = "更新")
    @PostMapping(value = "update")
    public Object update(@RequestBody ActivityTemplate template) {
        logger.info("【请求开始】活动中心->模板管理->更新,请求参数:{}", JSONObject.toJSONString(template));

        Object error = validate(template);
        if (error != null) {
            return error;
        }
        Integer templateId = template.getId();
        if (templateId == null) {
            return ResponseUtil.badArgument();
        }
        if (activityTemplateService.update(template) == 0) {
            logger.error("活动中心->模板管理-更新 ,错误：{}", "更新数据失败！");
            return ResponseUtil.updatedDataFailed();
        }
        logger.info("【请求结束】活动中心->模板管理->更新,响应结果:{}", JSONObject.toJSONString(template));
        return ResponseUtil.ok(template);
    }

    @RequiresPermissions("admin:template:delete")
    @RequiresPermissionsDesc(
            menu = {"活动中心", "模板管理"},
            button = "删除")
    @PostMapping(value = "delete")
    public Object delete(@RequestBody ActivityTemplate template) {
        logger.info("【请求开始】活动中心->模板管理->删除,请求参数:{}", JSONObject.toJSONString(template));
        activityTemplateService.deleteById(template.getId());
        logger.info("【请求结束】活动中心->模板管理->删除,响应结果:{}", "成功!");
        return ResponseUtil.ok();
    }
}
