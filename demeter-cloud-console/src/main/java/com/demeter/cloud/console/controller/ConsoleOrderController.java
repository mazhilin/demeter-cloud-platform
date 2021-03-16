package com.demeter.cloud.console.controller;

import com.alibaba.fastjson.JSONObject;
import com.demeter.cloud.console.annotation.RequiresPermissionsDesc;
import com.demeter.cloud.core.utils.ResponseUtil;
import com.demeter.cloud.core.validator.Order;
import com.demeter.cloud.core.validator.Sort;
import com.demeter.cloud.model.entity.OrderInfo;
import com.demeter.cloud.model.service.OrderGoodsService;
import com.demeter.cloud.model.service.OrderInfoService;
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
 * <p>封装Dcloud项目ConsoleOrderController类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-17 03:24
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
@RestController
@RequestMapping(value = "/admin/order/")
@Validated
public class ConsoleOrderController extends BaseController {

    @Autowired
    private OrderInfoService orderService;

    @Autowired
    private OrderGoodsService orderGoodsService;

    @RequiresPermissions("admin:order:list")
    @RequiresPermissionsDesc(
            menu = {"商城中心", "订单管理"},
            button = "列表")
    @GetMapping(value = "list")
    public Object list(
            String name,
            String code,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer limit,
            @Sort @RequestParam(defaultValue = "create_time") String sort,
            @Order @RequestParam(defaultValue = "desc") String order) {
        logger.info("【请求开始】商城中心->订单管理->列表,请求参数,name:{},code:{},page:{}", name, code, page);
        List<OrderInfo> orderList = orderService.queryList(name, code, page, limit, sort, order);
        long total = PageInfo.of(orderList).getTotal();
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", orderList);
        logger.info("【请求结束】【请求开始】商城中心->订单管理->列表:响应结果:{}", JSONObject.toJSONString(data));
        return ResponseUtil.ok(data);
    }

    private Object validate(OrderInfo order) {
        String mobile = order.getMobile();
        if (StringUtils.isEmpty(mobile)) {
            return ResponseUtil.badArgument();
        }
        return null;
    }

    @RequiresPermissions("admin:order:create")
    @RequiresPermissionsDesc(
            menu = {"商城中心", "订单管理"},
            button = "新增")
    @PostMapping(value = "create")
    public Object create(@RequestBody OrderInfo order) {
        logger.info("【请求开始】商城中心->订单管理->新增,请求参数:{}", JSONObject.toJSONString(order));

        Object error = validate(order);
        if (error != null) {
            return error;
        }
        orderService.add(order);

        logger.info("【请求结束】商城中心->订单管理->新增,响应结果:{}", JSONObject.toJSONString(order));
        return ResponseUtil.ok(order);
    }


    /**
     * @param order 参数对象
     * @return
     */
    @RequiresPermissions("admin:order:edit")
    @RequiresPermissionsDesc(
            menu = {"商城中心", "订单管理"},
            button = "编辑")
    @PostMapping(value = "edit")
    public Object edit(@RequestBody OrderInfo order) {
        logger.info("【请求开始】商城中心->订单管理->编辑,请求参数:{}", JSONObject.toJSONString(order));

        Object error = validate(order);
        if (error != null) {
            return error;
        }
        String orderId = order.getId();
        if (orderId == null) {
            return ResponseUtil.badArgument();
        }
        if (orderService.update(order) == 0) {
            logger.error("商城中心->订单管理-编辑 ,错误：{}", "编辑数据失败！");
            return ResponseUtil.updatedDataFailed();
        }
        logger.info("【请求结束】商城中心->订单管理->编辑,响应结果:{}", JSONObject.toJSONString(order));
        return ResponseUtil.ok(order);
    }

    /**
     * @param order 参数对象
     * @return
     */
    @RequiresPermissions("admin:order:update")
    @RequiresPermissionsDesc(
            menu = {"商城中心", "订单管理"},
            button = "更新")
    @PostMapping(value = "update")
    public Object update(@RequestBody OrderInfo order) {
        logger.info("【请求开始】商城中心->订单管理->更新,请求参数:{}", JSONObject.toJSONString(order));

        Object error = validate(order);
        if (error != null) {
            return error;
        }
        String orderId = order.getId();
        if (orderId == null) {
            return ResponseUtil.badArgument();
        }
        if (orderService.update(order) == 0) {
            logger.error("商城中心->订单管理-更新 ,错误：{}", "更新数据失败！");
            return ResponseUtil.updatedDataFailed();
        }
        logger.info("【请求结束】商城中心->订单管理->更新,响应结果:{}", JSONObject.toJSONString(order));
        return ResponseUtil.ok(order);
    }

    @RequiresPermissions("admin:order:delete")
    @RequiresPermissionsDesc(
            menu = {"商城中心", "订单管理"},
            button = "删除")
    @PostMapping(value = "delete")
    public Object delete(@RequestBody OrderInfo order) {
        logger.info("【请求开始】商城中心->订单管理->删除,请求参数:{}", JSONObject.toJSONString(order));
        orderService.deleteById(Integer.valueOf(order.getId()));
        logger.info("【请求结束】商城中心->订单管理->删除,响应结果:{}", "成功!");
        return ResponseUtil.ok();
    }
}
