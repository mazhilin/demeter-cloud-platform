package com.demeter.cloud.console.controller;

import com.demeter.cloud.core.constant.RedisConstant;
import com.demeter.cloud.core.utils.JsonServerUtil;
import com.demeter.cloud.core.utils.ResponseUtil;
import com.demeter.cloud.model.data.RegionData;
import com.demeter.cloud.model.entity.AdminUser;
import com.demeter.cloud.model.entity.RegionInfo;
import com.demeter.cloud.model.redis.ListOperationsService;
import com.demeter.cloud.model.redis.ValueOperationsService;
import com.demeter.cloud.model.service.RegionInfoService;
import com.demeter.cloud.persistence.controller.BaseController;
import com.demeter.cloud.utils.CheckEmptyUtil;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>封装Dcloud项目ConsoleComponentController类.<br></p>
 * <p>基础组件控制器<br></p>
 *
 * @author Powered by marklin 2021-02-20 21:50
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
@RestController
@RequestMapping(value = "/admin/component/")
@Validated
public class ConsoleComponentController extends BaseController {

    @Autowired
    private RegionInfoService regionInfoService;

    @Autowired
    AdminUser adminUser;

    @Autowired
    private ListOperationsService<String, String> listOperationsService;


    @Autowired
    private ValueOperationsService<String, Object> valueOperationsService;


    protected synchronized void regionTreeList() {
        List<RegionData> provinceList = Lists.newLinkedList();
        List<RegionInfo> queryProvinceList = regionInfoService.queryProvinceList();
        if (CheckEmptyUtil.isNotEmpty(queryProvinceList)) {
            for (RegionInfo province : queryProvinceList) {
                RegionData provinceData = new RegionData();
                provinceData.setValue(province.getId());
                provinceData.setType(province.getType().intValue());
                provinceData.setLevel(province.getLevel());
                provinceData.setLabel(province.getName());
                provinceData.setCode(province.getCode());
                List<RegionData> cityList = Lists.newLinkedList();
                List<RegionInfo> queryCityList = regionInfoService.queryCityListByParentId(province.getId());
                if (CheckEmptyUtil.isNotEmpty(queryCityList)) {
                    for (RegionInfo city : queryCityList) {
                        RegionData cityData = new RegionData();
                        cityData.setValue(city.getId());
                        cityData.setType(city.getType().intValue());
                        cityData.setLevel(city.getLevel());
                        cityData.setLabel(city.getName());
                        cityData.setCode(city.getCode());
                        List<RegionData> districtList = Lists.newLinkedList();
                        List<RegionInfo> queryDistrictList = regionInfoService.queryDistrictListByParentId(city.getId());
                        if (CheckEmptyUtil.isNotEmpty(queryDistrictList)) {
                            for (RegionInfo district : queryDistrictList) {
                                RegionData districtData = new RegionData();
                                districtData.setValue(district.getId());
                                districtData.setType(district.getType().intValue());
                                districtData.setLevel(district.getLevel());
                                districtData.setLabel(district.getName());
                                districtData.setCode(district.getCode());
                                districtList.add(districtData);
                            }
                        }
                        cityData.setChild(districtList);
                        cityList.add(cityData);
                    }
                }
                provinceData.setChild(cityList);
                provinceList.add(provinceData);
            }
        }
        valueOperationsService.set(RedisConstant.APP_SYSTEM_REGION_LIST, JsonServerUtil.getInstance().parseToJson(provinceList));
    }

    /**
     * 查询区域-省份下拉列表
     *
     * @return 返回列表
     */
    @GetMapping(value = "regionList")
    public Object regionList() {
        Object dataList = valueOperationsService.get(RedisConstant.APP_SYSTEM_REGION_LIST);
        if (CheckEmptyUtil.isEmpty(dataList)) {
            regionTreeList();
            dataList = valueOperationsService.get(RedisConstant.APP_SYSTEM_REGION_LIST);
        }
        return ResponseUtil.ok(JsonServerUtil.getInstance().convertToList((String) dataList,RegionData.class));
    }

    /**
     * 查询区域-省份下拉列表
     *
     * @return 返回列表
     */
    @GetMapping(value = "provinceList")
    public Object provinceList() {
        List<RegionData> provinceList = Lists.newLinkedList();
        List<RegionInfo> queryProvinceList = regionInfoService.queryProvinceList();
        if (CheckEmptyUtil.isNotEmpty(queryProvinceList)) {
            queryProvinceList.parallelStream().forEachOrdered(province -> {
                RegionData region = new RegionData();
                region.setValue(province.getId());
                region.setCode(province.getCode());
                region.setLevel(province.getLevel());
                region.setType(province.getType().intValue());
                region.setLabel(province.getName());
                provinceList.add(region);
            });
        }
        return ResponseUtil.ok(provinceList);
    }

    /**
     * 查询区域-城市下拉列表
     *
     * @return 返回列表
     */
    @GetMapping(value = "cityList")
    public Object cityList(@RequestParam(value = "parentId") Integer parentId) {
        List<RegionData> cityList = Lists.newLinkedList();
        List<RegionInfo> queryCityList = regionInfoService.queryCityListByParentId(parentId);
        if (CheckEmptyUtil.isNotEmpty(queryCityList)) {
            queryCityList.parallelStream().forEachOrdered(province -> {
                RegionData region = new RegionData();
                region.setValue(province.getId());
                region.setCode(province.getCode());
                region.setLevel(province.getLevel());
                region.setType(province.getType().intValue());
                region.setLabel(province.getName());
                cityList.add(region);
            });
        }
        return ResponseUtil.ok(cityList);
    }

    /**
     * 查询区域-区县下拉列表
     *
     * @return 返回列表
     */
    @GetMapping(value = "districtList")
    public Object districtList(@RequestParam(value = "parentId") Integer parentId) {
        List<RegionData> districtList = Lists.newLinkedList();
        List<RegionInfo> queryDistrictList = regionInfoService.queryDistrictListByParentId(parentId);
        if (CheckEmptyUtil.isNotEmpty(queryDistrictList)) {
            queryDistrictList.parallelStream().forEachOrdered(province -> {
                RegionData region = new RegionData();
                region.setValue(province.getId());
                region.setCode(province.getCode());
                region.setLevel(province.getLevel());
                region.setType(province.getType().intValue());
                region.setLabel(province.getName());
                districtList.add(region);
            });
        }
        return ResponseUtil.ok(districtList);
    }

}
