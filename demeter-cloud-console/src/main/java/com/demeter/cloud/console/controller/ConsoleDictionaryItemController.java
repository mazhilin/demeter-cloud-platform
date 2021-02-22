package com.demeter.cloud.console.controller;

import com.alibaba.fastjson.JSONObject;
import com.demeter.cloud.console.annotation.RequiresPermissionsDesc;
import com.demeter.cloud.core.util.CheckEmptyUtil;
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
 * <p>封装Dcloud项目ConsoleDictionaryItemController类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-19 00:52
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
@RestController
@RequestMapping(value = "/admin/dictionary/item/")
@Validated
public class ConsoleDictionaryItemController extends BaseController {

    @Autowired
    private DictionaryService dictionaryService;

    @Autowired
    private DictionaryItemService dictionaryItemService;


    @RequiresPermissions("admin:dictionary:item:list")
    @RequiresPermissionsDesc(
            menu = {"配置中心", "字典项管理"},
            button = "列表")
    @GetMapping(value = "list")
    public Object list(
            String name,
            String label,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer limit,
            @Sort @RequestParam(defaultValue = "create_time") String sort,
            @Order @RequestParam(defaultValue = "desc") String order) {
        logger.info("【请求开始】配置中心->字典项管理->列表,请求参数,name:{},label:{},page:{}", name, label, page);
        List<DictionaryItem> dictionaryList = dictionaryItemService.queryList(name, label, page, limit, sort, order);
        long total = PageInfo.of(dictionaryList).getTotal();
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", dictionaryList);
        logger.info("【请求结束】【请求开始】配置中心->字典项管理->列表:响应结果:{}", JSONObject.toJSONString(data));
        return ResponseUtil.ok(data);
    }

    private Object validate(DictionaryItem dictionary) {
        String name = dictionary.getName();
        if (StringUtils.isEmpty(name)) {
            return ResponseUtil.badArgument();
        }
        String code = dictionary.getLabel();
        if (StringUtils.isEmpty(code)) {
            return ResponseUtil.badArgument();
        }
        return null;
    }

    @RequiresPermissions("admin:dictionary:item:show")
    @RequiresPermissionsDesc(menu = {"配置中心", "字典项管理"}, button = "详情")
    @GetMapping(value = "show")
    public Object show(@NotNull Integer id) {
        logger.info("【请求开始】配置中心->字典项管理->详情,请求参数,id:{}", id);

        DictionaryItem dictionary = dictionaryItemService.queryById(id);

        logger.info("【请求结束】配置中心->字典项管理->详情,响应结果:{}", JSONObject.toJSONString(dictionary));
        return ResponseUtil.ok(dictionary);
    }

    @RequiresPermissions("admin:dictionary:item:create")
    @RequiresPermissionsDesc(
            menu = {"配置中心", "字典项管理"},
            button = "新增")
    @PostMapping(value = "create")
    public Object create(@RequestBody DictionaryItem dictionary) {
        logger.info("【请求开始】配置中心->字典项管理->新增,请求参数:{}", JSONObject.toJSONString(dictionary));
        Object error = validate(dictionary);
        if (error != null) {
            return error;
        }
        Dictionary dictionaryData = dictionaryService.queryById(dictionary.getDictionaryId());
        if (CheckEmptyUtil.isEmpty(dictionaryData)) {
            return ResponseUtil.fail(500, "对应数据字典[" + dictionary.getDictionaryId() + "}不存在!");
        }
        dictionaryItemService.add(dictionary);
        logger.info("【请求结束】配置中心->字典项管理->新增,响应结果:{}", JSONObject.toJSONString(dictionary));
        return ResponseUtil.ok(dictionary);
    }

    /**
     * @param dictionary 参数对象
     * @return
     */
    @RequiresPermissions("admin:dictionary:item:edit")
    @RequiresPermissionsDesc(
            menu = {"配置中心", "字典项管理"},
            button = "编辑")
    @PostMapping(value = "edit")
    public Object edit(@RequestBody DictionaryItem dictionary) {
        logger.info("【请求开始】配置中心->字典项管理->编辑,请求参数:{}", JSONObject.toJSONString(dictionary));

        Object error = validate(dictionary);
        if (error != null) {
            return error;
        }
        Integer parameterId = dictionary.getId();
        if (parameterId == null) {
            return ResponseUtil.badArgument();
        }
        Dictionary dictionaryData = dictionaryService.queryById(dictionary.getDictionaryId());
        if (CheckEmptyUtil.isEmpty(dictionaryData)) {
            return ResponseUtil.fail(500, "对应数据字典[" + dictionary.getDictionaryId() + "}不存在!");
        }
        if (dictionaryItemService.update(dictionary) == 0) {
            logger.error("配置中心->字典项管理-编辑 ,错误：{}", "编辑数据失败！");
            return ResponseUtil.updatedDataFailed();
        }
        logger.info("【请求结束】配置中心->字典项管理->编辑,响应结果:{}", JSONObject.toJSONString(dictionary));
        return ResponseUtil.ok(dictionary);
    }

    /**
     * @param dictionary 参数对象
     * @return
     */
    @RequiresPermissions("admin:dictionary:item:update")
    @RequiresPermissionsDesc(
            menu = {"配置中心", "字典项管理"},
            button = "更新")
    @PostMapping(value = "update")
    public Object update(@RequestBody DictionaryItem dictionary) {
        logger.info("【请求开始】配置中心->字典项管理->更新,请求参数:{}", JSONObject.toJSONString(dictionary));

        Object error = validate(dictionary);
        if (error != null) {
            return error;
        }
        Integer parameterId = dictionary.getId();
        if (parameterId == null) {
            return ResponseUtil.badArgument();
        }
        Dictionary dictionaryData = dictionaryService.queryById(dictionary.getDictionaryId());
        if (CheckEmptyUtil.isEmpty(dictionaryData)) {
            return ResponseUtil.fail(500, "对应数据字典[" + dictionary.getDictionaryId() + "}不存在!");
        }
        if (dictionaryItemService.update(dictionary) == 0) {
            logger.error("配置中心->字典项管理-更新 ,错误：{}", "更新数据失败！");
            return ResponseUtil.updatedDataFailed();
        }
        logger.info("【请求结束】配置中心->字典项管理->更新,响应结果:{}", JSONObject.toJSONString(dictionary));
        return ResponseUtil.ok(dictionary);
    }

    @RequiresPermissions("admin:dictionary:item:delete")
    @RequiresPermissionsDesc(
            menu = {"配置中心", "字典项管理"},
            button = "删除")
    @PostMapping(value = "delete")
    public Object delete(@RequestBody DictionaryItem dictionary) {
        logger.info("【请求开始】配置中心->字典项管理->删除,请求参数:{}", JSONObject.toJSONString(dictionary));
        dictionaryItemService.deleteById(dictionary.getId());
        logger.info("【请求结束】配置中心->字典项管理->删除,响应结果:{}", "成功!");
        return ResponseUtil.ok();
    }
}
