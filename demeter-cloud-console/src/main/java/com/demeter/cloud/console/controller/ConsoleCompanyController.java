package com.demeter.cloud.console.controller;

import com.alibaba.fastjson.JSONObject;
import com.demeter.cloud.console.annotation.RequiresPermissionsDesc;
import com.demeter.cloud.core.util.ResponseUtil;
import com.demeter.cloud.core.validator.Order;
import com.demeter.cloud.core.validator.Sort;
import com.demeter.cloud.model.entity.Company;
import com.demeter.cloud.model.persistence.controller.BaseController;
import com.demeter.cloud.model.service.CompanyService;
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
 * <p>封装Dcloud项目ConsoleCompanyController类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-20 01:20
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
@RestController
@RequestMapping(value = "/console/admin/company/")
@Validated
public class ConsoleCompanyController extends BaseController {
    
    @Autowired
    private CompanyService companyService;

    @RequiresPermissions("admin:company:list")
    @RequiresPermissionsDesc(
            menu = {"企业中心", "企业管理"},
            button = "列表")
    @GetMapping("/list")
    public Object list(
            String name,
            String code,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer limit,
            @Sort @RequestParam(defaultValue = "create_time") String sort,
            @Order @RequestParam(defaultValue = "desc") String order) {
        logger.info("【请求开始】企业中心->企业管理->列表,请求参数,name:{},code:{},page:{}", name, code, page);
        List<Company> companyList =
                companyService.queryList(name, code, page, limit, sort, order);
        long total = PageInfo.of(companyList).getTotal();
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", companyList);
        logger.info("【请求结束】【请求开始】企业中心->企业管理->列表:响应结果:{}", JSONObject.toJSONString(data));
        return ResponseUtil.ok(data);
    }

    private Object validate(Company company) {
        String name = company.getName();
        if (StringUtils.isEmpty(name)) {
            return ResponseUtil.badArgument();
        }
        String code = company.getCode();
        if (StringUtils.isEmpty(code)) {
            return ResponseUtil.badArgument();
        }
        return null;
    }

    @RequiresPermissions("admin:company:create")
    @RequiresPermissionsDesc(
            menu = {"企业中心", "企业管理"},
            button = "新增")
    @PostMapping("/create")
    public Object create(@RequestBody Company company) {
        logger.info("【请求开始】企业中心->企业管理->新增,请求参数:{}", JSONObject.toJSONString(company));

        Object error = validate(company);
        if (error != null) {
            return error;
        }
        companyService.add(company);

        logger.info("【请求结束】企业中心->企业管理->新增,响应结果:{}", JSONObject.toJSONString(company));
        return ResponseUtil.ok(company);
    }


    /**
     * @param company 参数对象
     * @return
     */
    @RequiresPermissions("admin:company:edit")
    @RequiresPermissionsDesc(
            menu = {"企业中心", "企业管理"},
            button = "编辑")
    @PostMapping("/edit")
    public Object edit(@RequestBody Company company) {
        logger.info("【请求开始】企业中心->企业管理->编辑,请求参数:{}", JSONObject.toJSONString(company));

        Object error = validate(company);
        if (error != null) {
            return error;
        }
        Integer companyId = company.getId();
        if (companyId == null) {
            return ResponseUtil.badArgument();
        }
        if (companyService.update(company) == 0) {
            logger.error("企业中心->企业管理-编辑 ,错误：{}", "编辑数据失败！");
            return ResponseUtil.updatedDataFailed();
        }
        logger.info("【请求结束】企业中心->企业管理->编辑,响应结果:{}", JSONObject.toJSONString(company));
        return ResponseUtil.ok(company);
    }

    /**
     * @param company 参数对象
     * @return
     */
    @RequiresPermissions("admin:company:update")
    @RequiresPermissionsDesc(
            menu = {"企业中心", "企业管理"},
            button = "更新")
    @PostMapping("/update")
    public Object update(@RequestBody Company company) {
        logger.info("【请求开始】企业中心->企业管理->更新,请求参数:{}", JSONObject.toJSONString(company));

        Object error = validate(company);
        if (error != null) {
            return error;
        }
        Integer companyId = company.getId();
        if (companyId == null) {
            return ResponseUtil.badArgument();
        }
        if (companyService.update(company) == 0) {
            logger.error("企业中心->企业管理-更新 ,错误：{}", "更新数据失败！");
            return ResponseUtil.updatedDataFailed();
        }
        logger.info("【请求结束】企业中心->企业管理->更新,响应结果:{}", JSONObject.toJSONString(company));
        return ResponseUtil.ok(company);
    }

    @RequiresPermissions("admin:company:delete")
    @RequiresPermissionsDesc(
            menu = {"企业中心", "企业管理"},
            button = "删除")
    @PostMapping("/delete")
    public Object delete(@RequestBody Company company) {
        logger.info("【请求开始】企业中心->企业管理->删除,请求参数:{}", JSONObject.toJSONString(company));
        companyService.deleteById(company.getId());
        logger.info("【请求结束】企业中心->企业管理->删除,响应结果:{}", "成功!");
        return ResponseUtil.ok();
    }
}
