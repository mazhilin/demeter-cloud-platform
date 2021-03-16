package com.demeter.cloud.console.controller;

import com.alibaba.fastjson.JSONObject;
import com.demeter.cloud.console.annotation.RequiresPermissionsDesc;
import com.demeter.cloud.core.utils.ResponseUtil;
import com.demeter.cloud.core.validator.Order;
import com.demeter.cloud.core.validator.Sort;
import com.demeter.cloud.model.entity.Company;
import com.demeter.cloud.model.entity.GoodsInfo;
import com.demeter.cloud.model.entity.OrderInfo;
import com.demeter.cloud.model.service.GoodsCategoryService;
import com.demeter.cloud.model.service.GoodsInfoService;
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
 * <p>封装Dcloud项目ConsoleGoodsController类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-03-10 10:13
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
@RestController
@RequestMapping(value = "/admin/goods/")
@Validated
public class ConsoleGoodsController extends BaseController {
     @Autowired
     private GoodsInfoService goodsService;

     @Autowired
     private GoodsCategoryService categoryService;

    @RequiresPermissions("admin:goods:list")
    @RequiresPermissionsDesc(
            menu = {"商城中心", "商品管理"},
            button = "列表")
    @GetMapping(value = "list")
    public Object list(
            String name,
            String code,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer limit,
            @Sort @RequestParam(defaultValue = "create_time") String sort,
            @Order @RequestParam(defaultValue = "desc") String order) {
        logger.info("【请求开始】商城中心->商品管理->列表,请求参数,name:{},code:{},page:{}", name, code, page);
        List<GoodsInfo> goodsList = goodsService.queryList(name, code, page, limit, sort, order);
        long total = PageInfo.of(goodsList).getTotal();
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", goodsList);
        logger.info("【请求结束】【请求开始】商城中心->商品管理->列表:响应结果:{}", JSONObject.toJSONString(data));
        return ResponseUtil.ok(data);
    }

    private Object validate(GoodsInfo goods) {
        String name = goods.getName();
        if (StringUtils.isEmpty(name)) {
            return ResponseUtil.badArgument();
        }
        return null;
    }

    @RequiresPermissions("admin:goods:create")
    @RequiresPermissionsDesc(
            menu = {"商城中心", "商品管理"},
            button = "新增")
    @PostMapping(value = "create")
    public Object create(@RequestBody GoodsInfo goods) {
        logger.info("【请求开始】商城中心->商品管理->新增,请求参数:{}", JSONObject.toJSONString(goods));

        Object error = validate(goods);
        if (error != null) {
            return error;
        }
        goodsService.add(goods);

        logger.info("【请求结束】商城中心->商品管理->新增,响应结果:{}", JSONObject.toJSONString(goods));
        return ResponseUtil.ok(goods);
    }


    /**
     * @param goods 参数对象
     * @return
     */
    @RequiresPermissions("admin:goods:edit")
    @RequiresPermissionsDesc(
            menu = {"商城中心", "商品管理"},
            button = "编辑")
    @PostMapping(value = "edit")
    public Object edit(@RequestBody GoodsInfo goods) {
        logger.info("【请求开始】商城中心->商品管理->编辑,请求参数:{}", JSONObject.toJSONString(goods));

        Object error = validate(goods);
        if (error != null) {
            return error;
        }
        Integer goodsId = goods.getId();
        if (goodsId == null) {
            return ResponseUtil.badArgument();
        }
        if (goodsService.update(goods) == 0) {
            logger.error("商城中心->商品管理-编辑 ,错误：{}", "编辑数据失败！");
            return ResponseUtil.updatedDataFailed();
        }
        logger.info("【请求结束】商城中心->商品管理->编辑,响应结果:{}", JSONObject.toJSONString(goods));
        return ResponseUtil.ok(goods);
    }

    /**
     * @param goods 参数对象
     * @return
     */
    @RequiresPermissions("admin:goods:update")
    @RequiresPermissionsDesc(
            menu = {"商城中心", "商品管理"},
            button = "更新")
    @PostMapping(value = "update")
    public Object update(@RequestBody GoodsInfo goods) {
        logger.info("【请求开始】商城中心->商品管理->更新,请求参数:{}", JSONObject.toJSONString(goods));

        Object error = validate(goods);
        if (error != null) {
            return error;
        }
        Integer goodsId = goods.getId();
        if (goodsId == null) {
            return ResponseUtil.badArgument();
        }
        if (goodsService.update(goods) == 0) {
            logger.error("商城中心->商品管理-更新 ,错误：{}", "更新数据失败！");
            return ResponseUtil.updatedDataFailed();
        }
        logger.info("【请求结束】商城中心->商品管理->更新,响应结果:{}", JSONObject.toJSONString(goods));
        return ResponseUtil.ok(goods);
    }

    @RequiresPermissions("admin:goods:delete")
    @RequiresPermissionsDesc(
            menu = {"商城中心", "商品管理"},
            button = "删除")
    @PostMapping(value = "delete")
    public Object delete(@RequestBody GoodsInfo goods) {
        logger.info("【请求开始】商城中心->商品管理->删除,请求参数:{}", JSONObject.toJSONString(goods));
        goodsService.deleteById(goods.getId());
        logger.info("【请求结束】商城中心->商品管理->删除,响应结果:{}", "成功!");
        return ResponseUtil.ok();
    }
}
