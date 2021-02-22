package com.demeter.cloud.console.controller;

import com.alibaba.fastjson.JSONObject;
import com.demeter.cloud.console.annotation.RequiresPermissionsDesc;
import com.demeter.cloud.core.util.ResponseUtil;
import com.demeter.cloud.core.validator.Order;
import com.demeter.cloud.core.validator.Sort;
import com.demeter.cloud.model.entity.Dictionary;
import com.demeter.cloud.model.entity.DictionaryItem;
import com.demeter.cloud.model.persistence.controller.BaseController;
import com.demeter.cloud.model.service.DictionaryItemService;
import com.demeter.cloud.model.service.DictionaryService;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>封装Dcloud项目ConsoleDictionaryController类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-18 23:20
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
@RestController
@RequestMapping(value = "/console/admin/dictionary/")
@Validated
public class ConsoleDictionaryController extends BaseController {
    @Autowired
    private DictionaryService dictionaryService;

    @Autowired
    private DictionaryItemService dictionaryItemService;

    @RequiresPermissions("admin:dictionary:list")
    @RequiresPermissionsDesc(
            menu = {"配置中心", "数据字典"},
            button = "列表")
    @GetMapping("/list")
    public Object list(
            String name,
            String code,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer limit,
            @Sort @RequestParam(defaultValue = "create_time") String sort,
            @Order @RequestParam(defaultValue = "desc") String order) {
        logger.info("【请求开始】配置中心->数据字典->列表,请求参数,name:{},code:{},page:{}", name, code, page);
        List<Dictionary> dictionaryList = dictionaryService.queryList(name, code, page, limit, sort, order);
        long total = PageInfo.of(dictionaryList).getTotal();
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", dictionaryList);
        logger.info("【请求结束】【请求开始】配置中心->数据字典->列表:响应结果:{}", JSONObject.toJSONString(data));
        return ResponseUtil.ok(data);
    }

    private Object validate(Dictionary dictionary) {
        String name = dictionary.getName();
        if (StringUtils.isEmpty(name)) {
            return ResponseUtil.badArgument();
        }
        String code = dictionary.getCode();
        if (StringUtils.isEmpty(code)) {
            return ResponseUtil.badArgument();
        }
        return null;
    }

    /**
     * @param dictionary 参数对象
     * @return
     */
    @RequiresPermissions("admin:dictionary:edit")
    @RequiresPermissionsDesc(
            menu = {"配置中心", "数据字典"},
            button = "编辑")
    @PostMapping("/edit")
    public Object edit(@RequestBody Dictionary dictionary) {
        logger.info("【请求开始】配置中心->数据字典->编辑,请求参数:{}", JSONObject.toJSONString(dictionary));

        Object error = validate(dictionary);
        if (error != null) {
            return error;
        }
        Integer parameterId = dictionary.getId();
        if (parameterId == null) {
            return ResponseUtil.badArgument();
        }
        if (dictionaryService.update(dictionary) == 0) {
            logger.error("配置中心->数据字典-编辑 ,错误：{}", "编辑数据失败！");
            return ResponseUtil.updatedDataFailed();
        }
        logger.info("【请求结束】配置中心->数据字典->编辑,响应结果:{}", JSONObject.toJSONString(dictionary));
        return ResponseUtil.ok(dictionary);
    }

    @RequiresPermissions("admin:dictionary:show")
    @RequiresPermissionsDesc(menu = {"配置中心", "数据字典"}, button = "详情")
    @GetMapping("/show")
    public Object show(@NotNull Integer id) {
        logger.info("【请求开始】配置中心->数字字典->详情,请求参数,id:{}", id);

        Dictionary dictionary = dictionaryService.queryById(id);

        logger.info("【请求结束】配置中心->数字字典->详情,响应结果:{}", JSONObject.toJSONString(dictionary));
        return ResponseUtil.ok(dictionary);
    }

    @RequiresPermissions("admin:dictionary:create")
    @RequiresPermissionsDesc(
            menu = {"配置中心", "数据字典"},
            button = "新增")
    @PostMapping("/create")
    public Object create(@RequestBody Dictionary dictionary) {
        logger.info("【请求开始】配置中心->数字字典->新增,请求参数:{}", JSONObject.toJSONString(dictionary));

        Object error = validate(dictionary);
        if (error != null) {
            return error;
        }
        dictionaryService.add(dictionary);
        logger.info("【请求结束】配置中心->数字字典->新增,响应结果:{}", JSONObject.toJSONString(dictionary));
        return ResponseUtil.ok(dictionary);
    }

    /**
     * @param dictionary 参数对象
     * @return
     */
    @RequiresPermissions("admin:dictionary:update")
    @RequiresPermissionsDesc(
            menu = {"配置中心", "数据字典"},
            button = "更新")
    @PostMapping("/update")
    public Object update(@RequestBody Dictionary dictionary) {
        logger.info("【请求开始】配置中心->数据字典->更新,请求参数:{}", JSONObject.toJSONString(dictionary));

        Object error = validate(dictionary);
        if (error != null) {
            return error;
        }
        Integer parameterId = dictionary.getId();
        if (parameterId == null) {
            return ResponseUtil.badArgument();
        }
        if (dictionaryService.update(dictionary) == 0) {
            logger.error("配置中心->数据字典-更新 ,错误：{}", "更新数据失败！");
            return ResponseUtil.updatedDataFailed();
        }
        logger.info("【请求结束】配置中心->数据字典->更新,响应结果:{}", JSONObject.toJSONString(dictionary));
        return ResponseUtil.ok(dictionary);
    }

    @RequiresPermissions("admin:dictionary:delete")
    @RequiresPermissionsDesc(
            menu = {"配置中心", "数据字典"},
            button = "删除")
    @PostMapping("/delete")
    public Object delete(@RequestBody Dictionary dictionary) {
        logger.info("【请求开始】配置中心->数据字典->删除,请求参数:{}", JSONObject.toJSONString(dictionary));
        dictionaryService.deleteById(dictionary.getId());
        List<DictionaryItem> dictionaryItemList = dictionaryItemService.findListById(dictionary.getId());
        dictionaryItemService.deleteAll(dictionaryItemList);
        logger.info("【请求结束】配置中心->数据字典->删除,响应结果:{}", "成功!");
        return ResponseUtil.ok();
    }
}
