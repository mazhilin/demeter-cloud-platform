package com.demeter.cloud.console.controller;

import com.alibaba.fastjson.JSONObject;
import com.demeter.cloud.console.annotation.RequiresPermissionsDesc;
import com.demeter.cloud.core.util.ResponseUtil;
import com.demeter.cloud.core.validator.Order;
import com.demeter.cloud.core.validator.Sort;
import com.demeter.cloud.model.entity.AdminUser;
import com.demeter.cloud.model.entity.RegionInfo;
import com.demeter.cloud.model.persistence.controller.BaseController;
import com.demeter.cloud.model.service.RegionInfoService;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * <p>封装Dcloud项目ConsoleRegionController类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-17 03:07
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
@RestController
@RequestMapping(value = "/console/admin/region/")
@Validated
public class ConsoleRegionController extends BaseController {

    @Autowired
    private RegionInfoService regionInfoService;


    /**
     * 查询区域列表
     *
     * @param name  区域名称
     * @param code  区域编码
     * @param page  页码
     * @param limit 条目数
     * @param sort  排序参数
     * @param order 排序规则
     * @return 返回列表
     */
    @RequiresPermissions("admin:region:list")
    @RequiresPermissionsDesc(menu = {"系统中心", "区域管理"}, button = "列表")
    @GetMapping(value = "list")
    public Object list(@RequestParam(value = "name", required = false) String name,
                       @RequestParam(value = "code", required = false) Integer code,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "create_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {

        logger.info("【请求开始】系统中心->区域管理->列表,请求参数:\n[{name:{},code:{},page:{},limit{},sort{},order{}}]",
                name, code, page, limit, sort, order);

        List<RegionInfo> regionInfoList = regionInfoService.queryList(name, code, page, limit, sort, order);
        long total = PageInfo.of(regionInfoList).getTotal();
        Map<String, Object> data = Maps.newConcurrentMap();
        data.put("total", total);
        data.put("items", regionInfoList);

        logger.info("【请求结束】系统中心->区域管理->列表,响应结果:\n{}", JSONObject.toJSONString(data));
        return ResponseUtil.ok(data);
    }

    /**
     * 校验参数
     *
     * @param region 区域对象
     * @return 返回结果
     */
    private Object validate(RegionInfo region) {
        if (StringUtils.isEmpty(region.getName())) {
            return ResponseUtil.badArgument();
        }
        if (StringUtils.isEmpty(region.getCode())) {
            return ResponseUtil.badArgument();
        }
        if (StringUtils.isEmpty(region.getType())) {
            return ResponseUtil.badArgument();
        }
        return null;
    }

    /**
     * 添加
     *
     * @param region 区域对象
     * @return 返回结果
     */
    @RequiresPermissions("admin:region:create")
    @RequiresPermissionsDesc(menu = {"系统中心", "区域管理"}, button = "新增")
    @PostMapping(value = "create")
    public Object create(@RequestBody RegionInfo region) {
        logger.info("【请求开始】系统中心->区域管理->新增,请求参数:{}", JSONObject.toJSONString(region));

        Object error = validate(region);
        if (error != null) {
            return error;
        }
        Subject currentUser = SecurityUtils.getSubject();
        AdminUser admin = (AdminUser) currentUser.getPrincipal();
        region.setCreateBy(admin.getId().toString());
        region.setUpdateBy(admin.getId().toString());
        regionInfoService.add(region);
        logger.info("【请求结束】系统中心->区域管理->新增,响应结果:{}", JSONObject.toJSONString(region));
        return ResponseUtil.ok(region);
    }

    /**
     * 详情
     *
     * @param id 区域对象
     * @return 返回结果
     */
    @RequiresPermissions("admin:region:show")
    @RequiresPermissionsDesc(menu = {"系统中心", "区域管理"}, button = "详情")
    @GetMapping(value = "show")
    public Object show(@NotNull Integer id) {
        logger.info("【请求开始】系统中心->区域管理->详情,请求参数,id:{}", id);

        RegionInfo region = regionInfoService.queryById(id);

        logger.info("【请求结束】系统中心->区域管理->详情,响应结果:{}", JSONObject.toJSONString(region));
        return ResponseUtil.ok(region);
    }

    /**
     * 编辑
     *
     * @param region 区域对象
     * @return 返回结果
     */
    @RequiresPermissions("admin:region:edit")
    @RequiresPermissionsDesc(menu = {"系统中心", "区域管理"}, button = "编辑")
    @PostMapping(value = "edit")
    public Object edit(@RequestBody RegionInfo region) {
        logger.info("【请求开始】系统中心->区域管理->编辑,请求参数:{}", JSONObject.toJSONString(region));

        Object error = validate(region);
        if (error != null) {
            return error;
        }
        Subject currentUser = SecurityUtils.getSubject();
        AdminUser admin = (AdminUser) currentUser.getPrincipal();
        region.setCreateBy(admin.getId().toString());
        region.setUpdateBy(admin.getId().toString());
        regionInfoService.update(region);
        logger.info("【请求结束】系统中心->区域管理->编辑,响应结果:{}", JSONObject.toJSONString(region));
        return ResponseUtil.ok(region);
    }


    /**
     * 更新
     *
     * @param region 区域对象
     * @return 返回结果
     */
    @RequiresPermissions("admin:region:update")
    @RequiresPermissionsDesc(menu = {"系统中心", "区域管理"}, button = "更新")
    @PostMapping(value = "update")
    public Object update(@RequestBody RegionInfo region) {
        logger.info("【请求开始】系统中心->区域管理->更新,请求参数:{}", JSONObject.toJSONString(region));

        Object error = validate(region);
        if (error != null) {
            return error;
        }

        Integer regionId = region.getId();
        if (regionId == null) {
            return ResponseUtil.badArgument();
        }

        Subject currentUser = SecurityUtils.getSubject();
        AdminUser admin = (AdminUser) currentUser.getPrincipal();
        region.setCreateBy(admin.getId().toString());
        region.setUpdateBy(admin.getId().toString());
        if (regionInfoService.update(region) == 0) {
            logger.error("系统中心->区域管理-更新 ,错误：{}", "更新数据失败！");
            return ResponseUtil.updatedDataFailed();
        }

        logger.info("【请求结束】系统中心->区域管理->更新,响应结果:{}", JSONObject.toJSONString(region));
        return ResponseUtil.ok(region);
    }

    /**
     * 删除
     *
     * @param region 区域对象
     * @return 返回结果
     */
    @RequiresPermissions("admin:region:delete")
    @RequiresPermissionsDesc(menu = {"系统中心", "区域管理"}, button = "删除")
    @PostMapping(value = "delete")
    public Object delete(@RequestBody RegionInfo region) {
        logger.info("【请求开始】系统中心->区域管理->删除,请求参数:{}", JSONObject.toJSONString(region));

        Integer regionId = region.getId();
        if (regionId == null) {
            return ResponseUtil.badArgument();
        }
        regionInfoService.deleteById(regionId);
        logger.info("【请求结束】系统中心->区域管理->删除 成功！");
        return ResponseUtil.ok();
    }
}
