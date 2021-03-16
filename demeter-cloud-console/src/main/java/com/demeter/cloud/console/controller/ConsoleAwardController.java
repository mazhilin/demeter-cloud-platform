package com.demeter.cloud.console.controller;

import com.alibaba.fastjson.JSONObject;
import com.demeter.cloud.console.annotation.RequiresPermissionsDesc;
import com.demeter.cloud.core.utils.ResponseUtil;
import com.demeter.cloud.core.validator.Order;
import com.demeter.cloud.core.validator.Sort;
import com.demeter.cloud.model.entity.OrderInfo;
import com.demeter.cloud.model.entity.PrizeAward;
import com.demeter.cloud.model.service.PrizeAwardService;
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
 * <p>封装Dcloud项目ConsolePrizeController类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-03-10 11:18
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
@RestController
@RequestMapping(value = "/admin/award/")
@Validated
public class ConsoleAwardController extends BaseController {

    @Autowired
    private PrizeAwardService awardService;

    @RequiresPermissions("admin:award:list")
    @RequiresPermissionsDesc(
            menu = {"奖品中心", "奖品管理"},
            button = "列表")
    @GetMapping(value = "list")
    public Object list(
            String name,
            String code,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer limit,
            @Sort @RequestParam(defaultValue = "create_time") String sort,
            @Order @RequestParam(defaultValue = "desc") String order) {
        logger.info("【请求开始】奖品中心->奖品管理->列表,请求参数,name:{},code:{},page:{}", name, code, page);
        List<PrizeAward> awardList = awardService.queryList(name, code, page, limit, sort, order);
        long total = PageInfo.of(awardList).getTotal();
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", awardList);
        logger.info("【请求结束】【请求开始】奖品中心->奖品管理->列表:响应结果:{}", JSONObject.toJSONString(data));
        return ResponseUtil.ok(data);
    }

    private Object validate(PrizeAward award) {
        String mobile = award.getPrizeName();
        if (StringUtils.isEmpty(mobile)) {
            return ResponseUtil.badArgument();
        }
        return null;
    }

    @RequiresPermissions("admin:award:create")
    @RequiresPermissionsDesc(
            menu = {"奖品中心", "奖品管理"},
            button = "新增")
    @PostMapping(value = "create")
    public Object create(@RequestBody PrizeAward award) {
        logger.info("【请求开始】奖品中心->奖品管理->新增,请求参数:{}", JSONObject.toJSONString(award));

        Object error = validate(award);
        if (error != null) {
            return error;
        }
        awardService.add(award);

        logger.info("【请求结束】奖品中心->奖品管理->新增,响应结果:{}", JSONObject.toJSONString(award));
        return ResponseUtil.ok(award);
    }


    /**
     * @param award 参数对象
     * @return
     */
    @RequiresPermissions("admin:award:edit")
    @RequiresPermissionsDesc(
            menu = {"奖品中心", "奖品管理"},
            button = "编辑")
    @PostMapping(value = "edit")
    public Object edit(@RequestBody PrizeAward award) {
        logger.info("【请求开始】奖品中心->奖品管理->编辑,请求参数:{}", JSONObject.toJSONString(award));

        Object error = validate(award);
        if (error != null) {
            return error;
        }
        Integer awardId = award.getId();
        if (awardId == null) {
            return ResponseUtil.badArgument();
        }
        if (awardService.update(award) == 0) {
            logger.error("奖品中心->奖品管理-编辑 ,错误：{}", "编辑数据失败！");
            return ResponseUtil.updatedDataFailed();
        }
        logger.info("【请求结束】奖品中心->奖品管理->编辑,响应结果:{}", JSONObject.toJSONString(award));
        return ResponseUtil.ok(award);
    }

    /**
     * @param award 参数对象
     * @return
     */
    @RequiresPermissions("admin:award:update")
    @RequiresPermissionsDesc(
            menu = {"奖品中心", "奖品管理"},
            button = "更新")
    @PostMapping(value = "update")
    public Object update(@RequestBody PrizeAward award) {
        logger.info("【请求开始】奖品中心->奖品管理->更新,请求参数:{}", JSONObject.toJSONString(award));

        Object error = validate(award);
        if (error != null) {
            return error;
        }
        Integer awardId = award.getId();
        if (awardId == null) {
            return ResponseUtil.badArgument();
        }
        if (awardService.update(award) == 0) {
            logger.error("奖品中心->奖品管理-更新 ,错误：{}", "更新数据失败！");
            return ResponseUtil.updatedDataFailed();
        }
        logger.info("【请求结束】奖品中心->奖品管理->更新,响应结果:{}", JSONObject.toJSONString(award));
        return ResponseUtil.ok(award);
    }

    @RequiresPermissions("admin:award:delete")
    @RequiresPermissionsDesc(
            menu = {"奖品中心", "奖品管理"},
            button = "删除")
    @PostMapping(value = "delete")
    public Object delete(@RequestBody PrizeAward award) {
        logger.info("【请求开始】奖品中心->奖品管理->删除,请求参数:{}", JSONObject.toJSONString(award));
        awardService.deleteById(Integer.valueOf(award.getId()));
        logger.info("【请求结束】奖品中心->奖品管理->删除,响应结果:{}", "成功!");
        return ResponseUtil.ok();
    }

}
