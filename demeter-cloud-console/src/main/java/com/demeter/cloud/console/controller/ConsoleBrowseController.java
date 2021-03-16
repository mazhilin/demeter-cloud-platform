package com.demeter.cloud.console.controller;

import com.alibaba.fastjson.JSONObject;
import com.demeter.cloud.console.annotation.RequiresPermissionsDesc;
import com.demeter.cloud.core.utils.ResponseUtil;
import com.demeter.cloud.core.validator.Order;
import com.demeter.cloud.core.validator.Sort;
import com.demeter.cloud.model.entity.BrowseInfo;
import com.demeter.cloud.model.entity.Company;
import com.demeter.cloud.model.service.BrowseInfoService;
import com.demeter.cloud.persistence.controller.BaseController;
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
 * <p>封装Dcloud项目ConsoleProductController类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-17 03:25
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
@RestController
@RequestMapping(value = "/admin/browse/")
@Validated
public class ConsoleBrowseController extends BaseController {

    @Autowired
    private BrowseInfoService browseService;

    @RequiresPermissions("admin:company:list")
    @RequiresPermissionsDesc(
            menu = {"运营中心", "浏览管理"},
            button = "列表")
    @GetMapping(value = "list")
    public Object list(
            String name,
            String code,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer limit,
            @Sort @RequestParam(defaultValue = "create_time") String sort,
            @Order @RequestParam(defaultValue = "desc") String order) {
        logger.info("【请求开始】运营中心->浏览管理->列表,请求参数,name:{},code:{},page:{}", name, code, page);
        List<BrowseInfo> browseList = browseService.queryList(name, code, page, limit, sort, order);
        long total = PageInfo.of(browseList).getTotal();
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", browseList);
        logger.info("【请求结束】【请求开始】运营中心->浏览管理->列表:响应结果:{}", JSONObject.toJSONString(data));
        return ResponseUtil.ok(data);
    }

    private Object validate(BrowseInfo browse) {
        String ipAddress = browse.getIpAddress();
        if (StringUtils.isEmpty(ipAddress)) {
            return ResponseUtil.badArgument();
        }
        return null;
    }

    @RequiresPermissions("admin:company:create")
    @RequiresPermissionsDesc(
            menu = {"运营中心", "浏览管理"},
            button = "新增")
    @PostMapping(value = "create")
    public Object create(@RequestBody BrowseInfo browse) {
        logger.info("【请求开始】运营中心->浏览管理->新增,请求参数:{}", JSONObject.toJSONString(browse));

        Object error = validate(browse);
        if (error != null) {
            return error;
        }
        browseService.add(browse);

        logger.info("【请求结束】运营中心->浏览管理->新增,响应结果:{}", JSONObject.toJSONString(browse));
        return ResponseUtil.ok(browse);
    }


    /**
     * @param browse 参数对象
     * @return
     */
    @RequiresPermissions("admin:company:edit")
    @RequiresPermissionsDesc(
            menu = {"运营中心", "浏览管理"},
            button = "编辑")
    @PostMapping(value = "edit")
    public Object edit(@RequestBody BrowseInfo browse) {
        logger.info("【请求开始】运营中心->浏览管理->编辑,请求参数:{}", JSONObject.toJSONString(browse));

        Object error = validate(browse);
        if (error != null) {
            return error;
        }
        Integer companyId = browse.getId();
        if (companyId == null) {
            return ResponseUtil.badArgument();
        }
        if (browseService.update(browse) == 0) {
            logger.error("运营中心->浏览管理-编辑 ,错误：{}", "编辑数据失败！");
            return ResponseUtil.updatedDataFailed();
        }
        logger.info("【请求结束】运营中心->浏览管理->编辑,响应结果:{}", JSONObject.toJSONString(browse));
        return ResponseUtil.ok(browse);
    }

    /**
     * @param browse 参数对象
     * @return
     */
    @RequiresPermissions("admin:company:update")
    @RequiresPermissionsDesc(
            menu = {"运营中心", "浏览管理"},
            button = "更新")
    @PostMapping(value = "update")
    public Object update(@RequestBody BrowseInfo browse) {
        logger.info("【请求开始】运营中心->浏览管理->更新,请求参数:{}", JSONObject.toJSONString(browse));

        Object error = validate(browse);
        if (error != null) {
            return error;
        }
        Integer companyId = browse.getId();
        if (companyId == null) {
            return ResponseUtil.badArgument();
        }
        if (browseService.update(browse) == 0) {
            logger.error("运营中心->浏览管理-更新 ,错误：{}", "更新数据失败！");
            return ResponseUtil.updatedDataFailed();
        }
        logger.info("【请求结束】运营中心->浏览管理->更新,响应结果:{}", JSONObject.toJSONString(browse));
        return ResponseUtil.ok(browse);
    }

    @RequiresPermissions("admin:company:delete")
    @RequiresPermissionsDesc(
            menu = {"运营中心", "浏览管理"},
            button = "删除")
    @PostMapping(value = "delete")
    public Object delete(@RequestBody Company company) {
        logger.info("【请求开始】运营中心->浏览管理->删除,请求参数:{}", JSONObject.toJSONString(company));
        browseService.deleteById(company.getId());
        logger.info("【请求结束】运营中心->浏览管理->删除,响应结果:{}", "成功!");
        return ResponseUtil.ok();
    }
}
