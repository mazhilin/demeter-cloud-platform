package com.demeter.cloud.console.controller;

import com.alibaba.fastjson.JSONObject;
import com.demeter.cloud.console.annotation.RequiresPermissionsDesc;
import com.demeter.cloud.core.utils.ResponseUtil;
import com.demeter.cloud.core.validator.Order;
import com.demeter.cloud.core.validator.Sort;
import com.demeter.cloud.model.entity.GoodsInfo;
import com.demeter.cloud.model.entity.LikeInfo;
import com.demeter.cloud.model.service.LikeInfoService;
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
 * <p>封装Dcloud项目ConsoleLikeController类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-03-10 10:40
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */

@RestController
@RequestMapping(value = "/admin/like/")
@Validated
public class ConsoleLikeController extends BaseController {
    @Autowired
    private LikeInfoService likeService;

    @RequiresPermissions("admin:like:list")
    @RequiresPermissionsDesc(
            menu = {"运营中心", "点赞管理"},
            button = "列表")
    @GetMapping(value = "list")
    public Object list(
            String name,
            String code,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer limit,
            @Sort @RequestParam(defaultValue = "create_time") String sort,
            @Order @RequestParam(defaultValue = "desc") String order) {
        logger.info("【请求开始】运营中心->点赞管理->列表,请求参数,name:{},code:{},page:{}", name, code, page);
        List<LikeInfo> likeList = likeService.queryList(name, code, page, limit, sort, order);
        long total = PageInfo.of(likeList).getTotal();
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", likeList);
        logger.info("【请求结束】【请求开始】运营中心->点赞管理->列表:响应结果:{}", JSONObject.toJSONString(data));
        return ResponseUtil.ok(data);
    }

    private Object validate(LikeInfo like) {
        String name = like.getIpAddress();
        if (StringUtils.isEmpty(name)) {
            return ResponseUtil.badArgument();
        }
        return null;
    }

    @RequiresPermissions("admin:like:create")
    @RequiresPermissionsDesc(
            menu = {"运营中心", "点赞管理"},
            button = "新增")
    @PostMapping(value = "create")
    public Object create(@RequestBody LikeInfo like) {
        logger.info("【请求开始】运营中心->点赞管理->新增,请求参数:{}", JSONObject.toJSONString(like));

        Object error = validate(like);
        if (error != null) {
            return error;
        }
        likeService.add(like);

        logger.info("【请求结束】运营中心->点赞管理->新增,响应结果:{}", JSONObject.toJSONString(like));
        return ResponseUtil.ok(like);
    }


    /**
     * @param like 参数对象
     * @return
     */
    @RequiresPermissions("admin:like:edit")
    @RequiresPermissionsDesc(
            menu = {"运营中心", "点赞管理"},
            button = "编辑")
    @PostMapping(value = "edit")
    public Object edit(@RequestBody LikeInfo like) {
        logger.info("【请求开始】运营中心->点赞管理->编辑,请求参数:{}", JSONObject.toJSONString(like));

        Object error = validate(like);
        if (error != null) {
            return error;
        }
        Integer likeId = like.getId();
        if (likeId == null) {
            return ResponseUtil.badArgument();
        }
        if (likeService.update(like) == 0) {
            logger.error("运营中心->点赞管理-编辑 ,错误：{}", "编辑数据失败！");
            return ResponseUtil.updatedDataFailed();
        }
        logger.info("【请求结束】运营中心->点赞管理->编辑,响应结果:{}", JSONObject.toJSONString(like));
        return ResponseUtil.ok(like);
    }

    /**
     * @param like 参数对象
     * @return
     */
    @RequiresPermissions("admin:like:update")
    @RequiresPermissionsDesc(
            menu = {"运营中心", "点赞管理"},
            button = "更新")
    @PostMapping(value = "update")
    public Object update(@RequestBody LikeInfo like) {
        logger.info("【请求开始】运营中心->点赞管理->更新,请求参数:{}", JSONObject.toJSONString(like));

        Object error = validate(like);
        if (error != null) {
            return error;
        }
        Integer likeId = like.getId();
        if (likeId == null) {
            return ResponseUtil.badArgument();
        }
        if (likeService.update(like) == 0) {
            logger.error("运营中心->点赞管理-更新 ,错误：{}", "更新数据失败！");
            return ResponseUtil.updatedDataFailed();
        }
        logger.info("【请求结束】运营中心->点赞管理->更新,响应结果:{}", JSONObject.toJSONString(like));
        return ResponseUtil.ok(like);
    }

    @RequiresPermissions("admin:like:delete")
    @RequiresPermissionsDesc(
            menu = {"运营中心", "点赞管理"},
            button = "删除")
    @PostMapping(value = "delete")
    public Object delete(@RequestBody LikeInfo like) {
        logger.info("【请求开始】运营中心->点赞管理->删除,请求参数:{}", JSONObject.toJSONString(like));
        likeService.deleteById(like.getId());
        logger.info("【请求结束】运营中心->点赞管理->删除,响应结果:{}", "成功!");
        return ResponseUtil.ok();
    }
    
    
}
