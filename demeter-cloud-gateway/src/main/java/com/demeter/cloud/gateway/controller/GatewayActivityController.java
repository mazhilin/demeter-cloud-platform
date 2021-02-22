package com.demeter.cloud.gateway.controller;

import com.demeter.cloud.model.entity.ActivityInfo;
import com.demeter.cloud.model.entity.ActivityTemplate;
import com.demeter.cloud.model.entity.Company;
import com.demeter.cloud.model.persistence.controller.BaseController;
import com.demeter.cloud.model.service.ActivityInfoService;
import com.demeter.cloud.model.service.ActivityTemplateService;
import com.demeter.cloud.model.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

/**
 * <p>封装Dcloud项目GatewayActivityController类.<br></p>
 * <p>活动相关<br></p>
 *
 * @author Powered by marklin 2021-02-19 03:28
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
@RestController
@RequestMapping("/api/gateway/activity")
@Validated
public class GatewayActivityController extends BaseController {
    @Autowired
    private ActivityInfoService activityInfoService;
    @Autowired
    private ActivityTemplateService activityTemplateService;
    @Autowired
    private CompanyService companyService;
    @GetMapping("/index")
    public Object activityInfoList(){
        List<ActivityTemplate> target = new ArrayList<>();
        Map templateMap = new HashMap();
        Map resultMap = new HashMap();
        Map companyMap = new HashMap();

        LocalDateTime rTime = LocalDateTime.now(ZoneOffset.UTC);
        logger.info("【请求开始】活动信息列表查询");

        List<ActivityInfo> activityInfoList = activityInfoService.queryActivityInfoList();
        for (int i = 0;i<activityInfoList.size();i++){
            LocalDateTime bTime = activityInfoList.get(i).getBeginTime();
            LocalDateTime eTime = activityInfoList.get(i).getEndTime();
            Byte aStatus = activityInfoList.get(i).getActivityStatus();
            if (rTime.isBefore(eTime)&&rTime.isAfter(bTime)&&aStatus == 1){
                /*获取进行中的活动*/
                ActivityInfo activtity = activityInfoList.get(i);
                resultMap.put("id",activtity.getId());
                resultMap.put("name",activtity.getName());
                resultMap.put("introduction",activtity.getIntroduction());
                resultMap.put("content",activtity.getContent());
                resultMap.put("regulation",activtity.getRegulation());
                resultMap.put("beginTime",activtity.getBeginTime());
                resultMap.put("endTime",activtity.getEndTime());
                resultMap.put("templateId",activtity.getTemplateId());

                /*获取活动模板*/
                ActivityTemplate template = activityTemplateService.queryActivityTemplateById(activtity.getTemplateId());
                templateMap.put("rotatePictures",template.getRotatePictures());
                templateMap.put("coverPicture",template.getCoverPicture());
                templateMap.put("backgroundMusic",template.getBackgroundMusic());

                /*获取公司*/
                int companyId = activtity.getCompanyId();
                Company company = companyService.queryById(companyId);
                companyMap.put("companyName",company.getName());
                companyMap.put("companyAddress",company.getAddress());
                companyMap.put("companyMobile",company.getMobile());

                /*嵌套map*/
                resultMap.putAll(companyMap);
                resultMap.putAll(templateMap);

            }else{
                return "近期没有活动";
            }
        }
        return resultMap;
    }
}
