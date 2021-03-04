package com.demeter.cloud.console.controller;

import com.demeter.cloud.core.util.CheckEmptyUtil;
import com.demeter.cloud.core.util.ResponseUtil;
import com.demeter.cloud.model.data.RegionData;
import com.demeter.cloud.model.entity.RegionInfo;
import com.demeter.cloud.model.persistence.controller.BaseController;
import com.demeter.cloud.model.service.RegionInfoService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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


    /**
     * 查询区域下拉列表
     *
     * @return 返回列表
     */
    @GetMapping(value = "regionList")
    public Object regionList() {
        List<RegionData> regionList = regionTreeList();
        return ResponseUtil.ok(regionList);
    }

    protected List<RegionData> regionTreeList() {
        List<RegionData> regionList = Lists.newLinkedList();
        List<RegionInfo> provinceList = regionInfoService.queryProvinceList();
        if (CheckEmptyUtil.isNotEmpty(provinceList)) {
            for (RegionInfo province : provinceList) {
                RegionData provinceData = new RegionData();
                provinceData.setValue(province.getId());
                provinceData.setType(province.getType().intValue());
                provinceData.setLevel(province.getLevel());
                provinceData.setLabel(province.getName());
                provinceData.setCode(province.getCode().toString());
                List<RegionInfo> cityList = regionInfoService.queryCityListByParentId(province.getId());
                if (CheckEmptyUtil.isNotEmpty(cityList)) {
                    for (RegionInfo city : cityList) {
                        RegionData cityData = new RegionData();
                        cityData.setValue(city.getId());
                        cityData.setType(city.getType().intValue());
                        cityData.setLevel(city.getLevel());
                        cityData.setLabel(city.getName());
                        cityData.setCode(city.getCode().toString());
                        List<RegionInfo> districtList = regionInfoService.queryDistrictListByParentId(city.getId());
                        if (CheckEmptyUtil.isNotEmpty(districtList)) {
                            for (RegionInfo district : districtList) {
                                RegionData districtData = new RegionData();
                                districtData.setValue(district.getId());
                                districtData.setType(district.getType().intValue());
                                districtData.setLevel(district.getLevel());
                                districtData.setLabel(district.getName());
                                districtData.setCode(district.getCode().toString());
                                cityData.getChild().add(districtData);
                            }
                        }
                        provinceData.getChild().add(cityData);
                    }
                }
                regionList.add(provinceData);
            }
        }
        return regionList;
    }
}
