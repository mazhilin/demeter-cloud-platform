package com.demeter.cloud.console.controller;

import com.alibaba.fastjson.JSONObject;
import com.demeter.cloud.console.annotation.RequiresPermissionsDesc;
import com.demeter.cloud.core.util.ResponseUtil;
import com.demeter.cloud.core.validator.Order;
import com.demeter.cloud.core.validator.Sort;
import com.demeter.cloud.model.entity.WorksInfo;
import com.demeter.cloud.model.persistence.controller.BaseController;
import com.demeter.cloud.model.service.WorksInfoService;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>封装Qicloud项目ConsoleWorksController类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-17 03:21
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
@RestController
@RequestMapping(value = "/api/console/works/")
@Validated
public class ConsoleWorksController extends BaseController {

    @Autowired
    private WorksInfoService worksInfoService;


    @RequiresPermissions("admin:worksinfo:list")
    @RequiresPermissionsDesc(
            menu = {"作品中心", "作品管理"},
            button = "查询")
    @GetMapping("/list")
    public Object list(
            String name,
            String code,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer limit,
            @Sort @RequestParam(defaultValue = "create_time") String sort,
            @Order @RequestParam(defaultValue = "desc") String order) {
        logger.info("【请求开始】作品中心->作品管理->查询,请求参数,name:{},code:{},page:{}", name, code, page);
        List<WorksInfo> worksInfoList =
                worksInfoService.queryList(name, code, page, limit, sort, order);
        long total = PageInfo.of(worksInfoList).getTotal();
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", worksInfoList);
        logger.info("【请求结束】【请求开始】作品中心->作品管理->查询:响应结果:{}", JSONObject.toJSONString(data));
        return ResponseUtil.ok(data);
    }

    private Object validate(WorksInfo worksInfo) {
        String name = worksInfo.getName();
        if (StringUtils.isEmpty(name)) {
            return ResponseUtil.badArgument();
        }
        String code = worksInfo.getCode();
        if (StringUtils.isEmpty(code)) {
            return ResponseUtil.badArgument();
        }
        return null;
    }

    @RequiresPermissions("admin:worksinfo:create")
    @RequiresPermissionsDesc(
            menu = {"作品中心", "作品管理"},
            button = "添加")
    @PostMapping("/create")
    public Object create(@RequestBody WorksInfo worksInfo) {
        logger.info("【请求开始】作品中心->作品管理->添加,请求参数:{}", JSONObject.toJSONString(worksInfo));

        Object error = validate(worksInfo);
        if (error != null) {
            return error;
        }
        worksInfoService.add(worksInfo);

        logger.info("【请求结束】作品中心->作品管理->添加,响应结果:{}", JSONObject.toJSONString(worksInfo));
        return ResponseUtil.ok(worksInfo);
    }


    /**
     * @param worksInfo 参数对象
     * @return
     */
    @RequiresPermissions("admin:worksinfo:edit")
    @RequiresPermissionsDesc(
            menu = {"作品中心", "作品管理"},
            button = "编辑")
    @PostMapping("/edit")
    public Object edit(@RequestBody WorksInfo worksInfo) {
        logger.info("【请求开始】作品中心->作品管理->编辑,请求参数:{}", JSONObject.toJSONString(worksInfo));

        Object error = validate(worksInfo);
        if (error != null) {
            return error;
        }
        Integer worksinfoId = worksInfo.getId();
        if (worksinfoId == null) {
            return ResponseUtil.badArgument();
        }
        if (worksInfoService.update(worksInfo) == 0) {
            logger.error("作品中心->作品管理-编辑 ,错误：{}", "编辑数据失败！");
            return ResponseUtil.updatedDataFailed();
        }
        logger.info("【请求结束】作品中心->作品管理->编辑,响应结果:{}", JSONObject.toJSONString(worksInfo));
        return ResponseUtil.ok(worksInfo);
    }

    /**
     * @param worksInfo 参数对象
     * @return
     */
    @RequiresPermissions("admin:worksinfo:update")
    @RequiresPermissionsDesc(
            menu = {"作品中心", "作品管理"},
            button = "更新")
    @PostMapping("/update")
    public Object update(@RequestBody WorksInfo worksInfo) {
        logger.info("【请求开始】作品中心->作品管理->更新,请求参数:{}", JSONObject.toJSONString(worksInfo));

        Object error = validate(worksInfo);
        if (error != null) {
            return error;
        }
        Integer worksinfoId = worksInfo.getId();
        if (worksinfoId == null) {
            return ResponseUtil.badArgument();
        }
        if (worksInfoService.update(worksInfo) == 0) {
            logger.error("作品中心->作品管理-更新 ,错误：{}", "更新数据失败！");
            return ResponseUtil.updatedDataFailed();
        }
        logger.info("【请求结束】作品中心->作品管理->更新,响应结果:{}", JSONObject.toJSONString(worksInfo));
        return ResponseUtil.ok(worksInfo);
    }

    @RequiresPermissions("admin:worksinfo:delete")
    @RequiresPermissionsDesc(
            menu = {"作品中心", "作品管理"},
            button = "删除")
    @PostMapping("/delete")
    public Object delete(@RequestBody WorksInfo worksInfo) {
        logger.info("【请求开始】作品中心->作品管理->删除,请求参数:{}", JSONObject.toJSONString(worksInfo));
        worksInfoService.deleteById(worksInfo.getId());
        logger.info("【请求结束】作品中心->作品管理->删除,响应结果:{}", "成功!");
        return ResponseUtil.ok();
    }
}
