package com.demeter.cloud.console.controller;

import com.alibaba.fastjson.JSONObject;
import com.demeter.cloud.console.annotation.RequiresPermissionsDesc;
import com.demeter.cloud.core.util.ResponseUtil;
import com.demeter.cloud.core.validator.Order;
import com.demeter.cloud.core.validator.Sort;
import com.demeter.cloud.model.entity.CustomerUser;
import com.demeter.cloud.model.entity.PublicParameter;
import com.demeter.cloud.model.persistence.controller.BaseController;
import com.demeter.cloud.model.service.CustomerUserService;
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
 * <p>封装Dcloud项目ConsoleCustomerController类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-17 03:15
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
@RestController
@RequestMapping(value = "/admin/customer/")
@Validated
public class ConsoleCustomerController extends BaseController {

    @Autowired
    private CustomerUserService customerUserService;

    @RequiresPermissions("admin:customer:list")
    @RequiresPermissionsDesc(
            menu = {"作品中心", "作者管理"},
            button = "列表")
    @GetMapping("/list")
    public Object list(
            String name,
            String code,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer limit,
            @Sort @RequestParam(defaultValue = "create_time") String sort,
            @Order @RequestParam(defaultValue = "desc") String order) {
        logger.info("【请求开始】作品中心->作者管理->作者列表,请求参数,name:{},code:{},page:{}", name, code, page);
        List<CustomerUser> customerUserList =
                customerUserService.queryList(name, code, page, limit, sort, order);
        long total = PageInfo.of(customerUserList).getTotal();
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", customerUserList);
        logger.info("【请求结束】【请求开始】作品中心->作者管理->列表:响应结果:{}", JSONObject.toJSONString(data));
        return ResponseUtil.ok(data);
    }

    private Object validate(CustomerUser customer) {
        String name = customer.getUsername();
        if (StringUtils.isEmpty(name)) {
            return ResponseUtil.badArgument();
        }
        String code = customer.getMobile();
        if (StringUtils.isEmpty(code)) {
            return ResponseUtil.badArgument();
        }
        return null;
    }

    @RequiresPermissions("admin:customer:create")
    @RequiresPermissionsDesc(
            menu = {"作品中心", "作者管理"},
            button = "新增")
    @PostMapping("/create")
    public Object create(@RequestBody CustomerUser customer) {
        logger.info("【请求开始】作品中心->作者管理->新增,请求参数:{}", JSONObject.toJSONString(customer));

        Object error = validate(customer);
        if (error != null) {
            return error;
        }
        customerUserService.add(customer);

        logger.info("【请求结束】作品中心->作者管理->新增,响应结果:{}", JSONObject.toJSONString(customer));
        return ResponseUtil.ok(customer);
    }


    /**
     * @param customer 参数对象
     * @return
     */
    @RequiresPermissions("admin:customer:edit")
    @RequiresPermissionsDesc(
            menu = {"作品中心", "作者管理"},
            button = "编辑")
    @PostMapping("/edit")
    public Object edit(@RequestBody CustomerUser customer) {
        logger.info("【请求开始】作品中心->作者管理->编辑,请求参数:{}", JSONObject.toJSONString(customer));

        Object error = validate(customer);
        if (error != null) {
            return error;
        }
        Integer customerId = customer.getId();
        if (customerId == null) {
            return ResponseUtil.badArgument();
        }
        if (customerUserService.update(customer) == 0) {
            logger.error("作品中心->作者管理-编辑 ,错误：{}", "编辑数据失败！");
            return ResponseUtil.updatedDataFailed();
        }
        logger.info("【请求结束】作品中心->作者管理->编辑,响应结果:{}", JSONObject.toJSONString(customer));
        return ResponseUtil.ok(customer);
    }

    /**
     * @param customer 参数对象
     * @return
     */
    @RequiresPermissions("admin:customer:update")
    @RequiresPermissionsDesc(
            menu = {"作品中心", "作者管理"},
            button = "更新")
    @PostMapping("/update")
    public Object update(@RequestBody CustomerUser customer) {
        logger.info("【请求开始】作品中心->作者管理->作者更新,请求参数:{}", JSONObject.toJSONString(customer));

        Object error = validate(customer);
        if (error != null) {
            return error;
        }
        Integer customerId = customer.getId();
        if (customerId == null) {
            return ResponseUtil.badArgument();
        }
        if (customerUserService.update(customer) == 0) {
            logger.error("作品中心->作者管理-更新 ,错误：{}", "更新数据失败！");
            return ResponseUtil.updatedDataFailed();
        }
        logger.info("【请求结束】作品中心->作者管理->更新,响应结果:{}", JSONObject.toJSONString(customer));
        return ResponseUtil.ok(customer);
    }

    @RequiresPermissions("admin:customer:delete")
    @RequiresPermissionsDesc(
            menu = {"作品中心", "作者管理"},
            button = "删除")
    @PostMapping("/delete")
    public Object delete(@RequestBody CustomerUser customer) {
        logger.info("【请求开始】作品中心->作者管理->删除,请求参数:{}", JSONObject.toJSONString(customer));
        customerUserService.deleteById(customer.getId());
        logger.info("【请求结束】作品中心->作者管理->删除,响应结果:{}", "成功!");
        return ResponseUtil.ok();
    }
}
