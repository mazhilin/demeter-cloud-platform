package com.demeter.cloud.console.controller;

import com.alibaba.fastjson.JSONObject;
import com.demeter.cloud.console.annotation.RequiresPermissionsDesc;
import com.demeter.cloud.core.utils.DateTimeUtil;
import com.demeter.cloud.core.utils.IpAddressUtil;
import com.demeter.cloud.core.utils.ResponseUtil;
import com.demeter.cloud.core.validator.Order;
import com.demeter.cloud.core.validator.Sort;
import com.demeter.cloud.model.entity.AdminUser;
import com.demeter.cloud.model.entity.WorksInfo;
import com.demeter.cloud.model.service.CustomerUserService;
import com.demeter.cloud.model.service.WorksInfoService;
import com.demeter.cloud.persistence.controller.BaseController;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>封装Dcloud项目ConsoleWorksController类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-17 03:21
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
@RestController
@RequestMapping(value = "/admin/works/")
@Validated
public class ConsoleWorksController extends BaseController {

    @Autowired
    private WorksInfoService worksInfoService;

    @Autowired
    private CustomerUserService customerUserService;


    @RequiresPermissions("admin:works:list")
    @RequiresPermissionsDesc(
            menu = {"作品中心", "作品管理"},
            button = "列表")
    @GetMapping(value = "list")
    public Object list(
            String name,
            String code,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer limit,
            @Sort @RequestParam(defaultValue = "create_time") String sort,
            @Order @RequestParam(defaultValue = "desc") String order) {
        logger.info("【请求开始】作品中心->作品管理->列表,请求参数,name:{},code:{},page:{}", name, code, page);
        List<WorksInfo> worksInfoList =
                worksInfoService.queryList(name, code, page, limit, sort, order);
        Objects.requireNonNull(worksInfoList).parallelStream().forEachOrdered(works->{
            works.setCustomerName(customerUserService.queryById(works.getCustomerId()).getName());
        });
        long total = PageInfo.of(worksInfoList).getTotal();
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", worksInfoList);
        logger.info("【请求结束】【请求开始】作品中心->作品管理->列表:响应结果:{}", JSONObject.toJSONString(data));
        return ResponseUtil.ok(data);
    }

    private Object validate(WorksInfo worksInfo) {
        String name = worksInfo.getName();
        if (StringUtils.isEmpty(name)) {
            return ResponseUtil.badArgument();
        }
        return null;
    }

    @RequiresPermissions("admin:works:create")
    @RequiresPermissionsDesc(
            menu = {"作品中心", "作品管理"},
            button = "新增")
    @PostMapping(value = "create")
    public Object create(@RequestBody WorksInfo worksInfo) {
        logger.info("【请求开始】作品中心->作品管理->新增,请求参数:{}", JSONObject.toJSONString(worksInfo));

        Object error = validate(worksInfo);
        if (error != null) {
            return error;
        }
    worksInfo.setCode(
        DateTimeUtil.getDate(LocalDateTime.now()) + DateTimeUtil.getDateTime(LocalDateTime.now()));
    AdminUser adminUser = (AdminUser) SecurityUtils.getSubject().getPrincipal();
    worksInfo.setIpAddress(IpAddressUtil.getIpAddress(request));
    worksInfo.setCreateBy(adminUser.getId().toString());
    worksInfo.setUpdateBy(adminUser.getId().toString());
    worksInfo.setCreateTime(LocalDateTime.now());
    worksInfo.setUpdateTime(LocalDateTime.now());
    worksInfoService.add(worksInfo);
        logger.info("【请求结束】作品中心->作品管理->新增,响应结果:{}", JSONObject.toJSONString(worksInfo));
        return ResponseUtil.ok(worksInfo);
    }

    @RequiresPermissions("admin:works:show")
    @RequiresPermissionsDesc(menu = {"作品中心", "作品管理"}, button = "详情")
    @GetMapping(value = "show")
    public Object show(@NotNull Integer id) {
        logger.info("【请求开始】作品中心->作品管理->详情,请求参数,id:{}", id);

        WorksInfo worksInfo = worksInfoService.queryById(id);

        logger.info("【请求结束】作品中心->作品管理->详情,响应结果:{}", JSONObject.toJSONString(worksInfo));
        return ResponseUtil.ok(worksInfo);
    }

    /**
     * @param worksInfo 参数对象
     * @return
     */
    @RequiresPermissions("admin:works:edit")
    @RequiresPermissionsDesc(
            menu = {"作品中心", "作品管理"},
            button = "编辑")
    @PostMapping(value = "edit")
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
        AdminUser adminUser = (AdminUser) SecurityUtils.getSubject().getPrincipal();
        worksInfo.setCreateBy(adminUser.getId().toString());
        worksInfo.setUpdateBy(adminUser.getId().toString());
        worksInfo.setCreateTime(LocalDateTime.now());
        worksInfo.setUpdateTime(LocalDateTime.now());
        worksInfo.setIpAddress(IpAddressUtil.getIpAddress(request));
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
    @RequiresPermissions("admin:works:update")
    @RequiresPermissionsDesc(
            menu = {"作品中心", "作品管理"},
            button = "更新")
    @PostMapping(value = "update")
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

    @RequiresPermissions("admin:works:delete")
    @RequiresPermissionsDesc(
            menu = {"作品中心", "作品管理"},
            button = "删除")
    @PostMapping(value = "delete")
    public Object delete(@RequestBody WorksInfo worksInfo) {
        logger.info("【请求开始】作品中心->作品管理->删除,请求参数:{}", JSONObject.toJSONString(worksInfo));
        worksInfoService.deleteById(worksInfo.getId());
        logger.info("【请求结束】作品中心->作品管理->删除,响应结果:{}", "成功!");
        return ResponseUtil.ok();
    }
}
