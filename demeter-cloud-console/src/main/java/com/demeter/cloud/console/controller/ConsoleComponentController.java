package com.demeter.cloud.console.controller;

import com.demeter.cloud.core.util.CheckEmptyUtil;
import com.demeter.cloud.core.util.ResponseUtil;
import com.demeter.cloud.model.entity.RegionInfo;
import com.demeter.cloud.model.persistence.controller.BaseController;
import com.demeter.cloud.model.service.RegionInfoService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>封装Dcloud项目ConsoleComponentController类.<br></p>
 * <p>基础组件控制器<br></p>
 *
 * @author Powered by marklin 2021-02-20 21:50
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
@RestController
@RequestMapping(value = "/console/admin/component/")
@Validated
public class ConsoleComponentController extends BaseController {

    @Autowired
    private RegionInfoService regionInfoService;

    /**
     * 查询区域下拉列表
     *
     * @return 返回列表
     */
    @GetMapping(value = "regionList")
    public Object regionList() {
        List<Map<String, Object>> regionList = Lists.newLinkedList();
        List<RegionInfo> provinceList = regionInfoService.queryProvinceList();
        if (CheckEmptyUtil.isNotEmpty(provinceList)) {
            Objects.requireNonNull(provinceList).parallelStream().forEachOrdered(province -> {
                Map<String, Object> builderProvinceData = Maps.newConcurrentMap();
                builderProvinceData.put("value", province.getId());
                builderProvinceData.put("label", province.getName());
                builderProvinceData.put("code", province.getCode());
                List<Map<String, Object>> cityList = Lists.newLinkedList();
                List<RegionInfo> queryCityList = regionInfoService.queryCityListByParentId(province.getId());
                Objects.requireNonNull(queryCityList).parallelStream().forEachOrdered(city -> {
                    Map<String, Object> builderCityData = Maps.newConcurrentMap();
                    builderCityData.put("value", city.getId());
                    builderCityData.put("label", city.getName());
                    builderCityData.put("code", city.getCode());
                    cityList.add(builderCityData);
                });
                builderProvinceData.put("child", cityList);
                regionList.add(builderProvinceData);
            });
        }
        return ResponseUtil.ok(regionList);
    }
}
