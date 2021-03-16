package com.demeter.cloud.console.controller;

import com.alibaba.fastjson.JSONObject;
import com.demeter.cloud.console.annotation.RequiresPermissionsDesc;
import com.demeter.cloud.core.utils.ResponseUtil;
import com.demeter.cloud.core.validator.Order;
import com.demeter.cloud.core.validator.Sort;
import com.demeter.cloud.model.entity.Company;
import com.demeter.cloud.model.entity.VoteInfo;
import com.demeter.cloud.model.service.VoteInfoService;
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
 * <p>封装Dcloud项目ConsoleVoteController类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-03-10 10:02
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
@RestController
@RequestMapping(value = "/admin/vote/")
@Validated
public class ConsoleVoteController extends BaseController {
    @Autowired
    private VoteInfoService voteService;

    @RequiresPermissions("admin:vote:list")
    @RequiresPermissionsDesc(
            menu = {"作品中心", "投票管理"},
            button = "列表")
    @GetMapping(value = "list")
    public Object list(
            String name,
            String code,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer limit,
            @Sort @RequestParam(defaultValue = "create_time") String sort,
            @Order @RequestParam(defaultValue = "desc") String order) {
        logger.info("【请求开始】作品中心->投票管理->列表,请求参数,name:{},code:{},page:{}", name, code, page);
        List<VoteInfo> voteList = voteService.queryList(name, code, page, limit, sort, order);
        long total = PageInfo.of(voteList).getTotal();
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", voteList);
        logger.info("【请求结束】【请求开始】作品中心->投票管理->列表:响应结果:{}", JSONObject.toJSONString(data));
        return ResponseUtil.ok(data);
    }

    private Object validate(VoteInfo vote) {
        String mobile = vote.getSubjectId();
        if (StringUtils.isEmpty(mobile)) {
            return ResponseUtil.badArgument();
        }
        return null;
    }

    @RequiresPermissions("admin:vote:create")
    @RequiresPermissionsDesc(
            menu = {"作品中心", "投票管理"},
            button = "新增")
    @PostMapping(value = "create")
    public Object create(@RequestBody VoteInfo vote) {
        logger.info("【请求开始】作品中心->投票管理->新增,请求参数:{}", JSONObject.toJSONString(vote));

        Object error = validate(vote);
        if (error != null) {
            return error;
        }
        voteService.add(vote);

        logger.info("【请求结束】作品中心->投票管理->新增,响应结果:{}", JSONObject.toJSONString(vote));
        return ResponseUtil.ok(vote);
    }


    /**
     * @param vote 参数对象
     * @return
     */
    @RequiresPermissions("admin:vote:edit")
    @RequiresPermissionsDesc(
            menu = {"作品中心", "投票管理"},
            button = "编辑")
    @PostMapping(value = "edit")
    public Object edit(@RequestBody VoteInfo vote) {
        logger.info("【请求开始】作品中心->投票管理->编辑,请求参数:{}", JSONObject.toJSONString(vote));

        Object error = validate(vote);
        if (error != null) {
            return error;
        }
        Integer voteId = vote.getId();
        if (voteId == null) {
            return ResponseUtil.badArgument();
        }
        if (voteService.update(vote) == 0) {
            logger.error("作品中心->投票管理-编辑 ,错误：{}", "编辑数据失败！");
            return ResponseUtil.updatedDataFailed();
        }
        logger.info("【请求结束】作品中心->投票管理->编辑,响应结果:{}", JSONObject.toJSONString(vote));
        return ResponseUtil.ok(vote);
    }

    /**
     * @param vote 参数对象
     * @return
     */
    @RequiresPermissions("admin:vote:update")
    @RequiresPermissionsDesc(
            menu = {"作品中心", "投票管理"},
            button = "更新")
    @PostMapping(value = "update")
    public Object update(@RequestBody VoteInfo vote) {
        logger.info("【请求开始】作品中心->投票管理->更新,请求参数:{}", JSONObject.toJSONString(vote));

        Object error = validate(vote);
        if (error != null) {
            return error;
        }
        Integer voteId = vote.getId();
        if (voteId == null) {
            return ResponseUtil.badArgument();
        }
        if (voteService.update(vote) == 0) {
            logger.error("作品中心->投票管理-更新 ,错误：{}", "更新数据失败！");
            return ResponseUtil.updatedDataFailed();
        }
        logger.info("【请求结束】作品中心->投票管理->更新,响应结果:{}", JSONObject.toJSONString(vote));
        return ResponseUtil.ok(vote);
    }

    @RequiresPermissions("admin:vote:delete")
    @RequiresPermissionsDesc(
            menu = {"作品中心", "投票管理"},
            button = "删除")
    @PostMapping(value = "delete")
    public Object delete(@RequestBody Company vote) {
        logger.info("【请求开始】作品中心->投票管理->删除,请求参数:{}", JSONObject.toJSONString(vote));
        voteService.deleteById(vote.getId());
        logger.info("【请求结束】作品中心->投票管理->删除,响应结果:{}", "成功!");
        return ResponseUtil.ok();
    }
}
