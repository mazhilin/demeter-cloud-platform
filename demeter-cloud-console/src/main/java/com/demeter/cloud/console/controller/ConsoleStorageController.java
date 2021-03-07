package com.demeter.cloud.console.controller;

import com.alibaba.fastjson.JSONObject;
import com.demeter.cloud.console.annotation.RequiresPermissionsDesc;
import com.demeter.cloud.core.storage.StorageServerService;
import com.demeter.cloud.core.utils.ResponseUtil;
import com.demeter.cloud.core.validator.Order;
import com.demeter.cloud.core.validator.Sort;
import com.demeter.cloud.model.entity.StorageFile;
import com.demeter.cloud.framework.persistence.controller.BaseController;
import com.demeter.cloud.model.service.StorageFileService;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>封装Dcloud项目ConsoleStorageController类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-17 01:12
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
@RestController
@RequestMapping(value = "/admin/storage/")
@Validated
public class ConsoleStorageController extends BaseController {
    @Autowired
    private StorageServerService storageService;
    @Autowired
    private StorageFileService fileService;

    /**
     * 资源管理
     *
     * @param key   文件key
     * @param name  文件名称
     * @param page  页码
     * @param limit 条目数
     * @param sort  排序字段
     * @param order 排序
     * @return 返回列表
     */
    @RequiresPermissions("admin:storage:list")
    @RequiresPermissionsDesc(menu = {"系统中心", "资源管理"}, button = "列表")
    @GetMapping(value = "list")
    public Object list(
            String key,
            String name,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer limit,
            @Sort @RequestParam(defaultValue = "create_time") String sort,
            @Order @RequestParam(defaultValue = "desc") String order) {
        logger.info("【请求开始】系统中心->资源管理->列表,请求参数,name:{},key:{},page:{}", name, key, page);

        List<StorageFile> storageFileList =
                fileService.queryFileList(key, name, page, limit, sort, order);
        long total = PageInfo.of(storageFileList).getTotal();
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", storageFileList);

        logger.info("【请求结束】系统中心->资源管理->列表:响应结果:{}", JSONObject.toJSONString(data));
        return ResponseUtil.ok(data);
    }

    /**
     * 新增
     * @param file
     * @return
     * @throws IOException
     */
    @RequiresPermissions("admin:storage:create")
    @RequiresPermissionsDesc(
            menu = {"系统中心", "资源管理"},
            button = "新增")
    @PostMapping(value = "create")
    public Object create(@RequestParam("file") MultipartFile file) throws IOException {
        logger.info("【请求开始】系统中心->资源管理->新增,请求参数,file:{}", file.getOriginalFilename());

        String originalFilename = file.getOriginalFilename();
        String url = storageService.store(
                file.getInputStream(), file.getSize(), file.getContentType(), originalFilename, request);
        Map<String, Object> data = Maps.newConcurrentMap();
        data.put("url", url);
        logger.info("【请求结束】系统中心->资源管理->新增:响应结果:{}", JSONObject.toJSONString(data));
        return ResponseUtil.ok(data);
    }

    @RequiresPermissions("admin:storage:show")
    @RequiresPermissionsDesc(
            menu = {"系统中心", "资源管理"},
            button = "详情")
    @PostMapping(value = "show")
    public Object show(@NotNull Integer id) {
        logger.info("【请求开始】系统中心->资源管理->详情,请求参数,id:{}", id);

        StorageFile storageFileInfo = fileService.queryFileById(id);
        if (storageFileInfo == null) {
            return ResponseUtil.badArgumentValue();
        }

        logger.info("【请求结束】系统中心->资源管理->详情:响应结果:{}", JSONObject.toJSONString(storageFileInfo));
        return ResponseUtil.ok(storageFileInfo);
    }


    @RequiresPermissions("admin:storage:edit")
    @RequiresPermissionsDesc(
            menu = {"系统中心", "资源管理"},
            button = "编辑")
    @PostMapping(value = "edit")
    public Object edit(@RequestBody StorageFile storageFile) {
        logger.info("【请求开始】系统中心->资源管理->编辑,请求参数:{}", JSONObject.toJSONString(storageFile));

        if (fileService.update(storageFile) == 0) {
            logger.error("系统中心->资源管理->编辑 错误:{}", "更新数据失败!");
            return ResponseUtil.updatedDataFailed();
        }

        logger.info("【请求结束】系统中心->资源管理->编辑 响应结果:{}", JSONObject.toJSONString(storageFile));
        return ResponseUtil.ok(storageFile);
    }

    @RequiresPermissions("admin:storage:update")
    @RequiresPermissionsDesc(
            menu = {"系统中心", "资源管理"},
            button = "更新")
    @PostMapping(value = "update")
    public Object update(@RequestBody StorageFile storageFile) {
        logger.info("【请求开始】系统中心->资源管理->更新,请求参数:{}", JSONObject.toJSONString(storageFile));

        if (fileService.update(storageFile) == 0) {
            logger.error("系统中心->资源管理->更新 错误:{}", "更新数据失败!");
            return ResponseUtil.updatedDataFailed();
        }

        logger.info("【请求结束】系统中心->资源管理->更新 响应结果:{}", JSONObject.toJSONString(storageFile));
        return ResponseUtil.ok(storageFile);
    }

    @RequiresPermissions("admin:storage:delete")
    @RequiresPermissionsDesc(
            menu = {"系统中心", "资源管理"},
            button = "删除")
    @PostMapping(value = "delete")
    public Object delete(@RequestBody StorageFile StorageFile) {
        logger.info("【请求开始】系统中心->资源管理->删除,请求参数:{}", JSONObject.toJSONString(StorageFile));

        String key = StorageFile.getKey();
        if (StringUtils.isEmpty(key)) {
            return ResponseUtil.badArgument();
        }
        fileService.deleteByKey(key);
        storageService.delete(key);

        logger.info("【请求结束】系统中心->资源管理->删除:响应结果:{}", "成功!");
        return ResponseUtil.ok();
    }
}
