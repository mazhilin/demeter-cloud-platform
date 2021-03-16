package com.demeter.cloud.model.service.impl;

import com.demeter.cloud.model.entity.ActivityTemplate;
import com.demeter.cloud.model.entity.ActivityTemplateExample;
import com.demeter.cloud.model.exception.BusinessException;
import com.demeter.cloud.model.mapper.ActivityTemplateMapper;
import com.demeter.cloud.model.service.ActivityTemplateService;
import com.demeter.cloud.persistence.service.BaseService;
import com.demeter.cloud.utils.CheckEmptyUtil;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>封装Qicloud项目ActivityTemplateService类.<br></p >
 * <p>//TODO...<br></p >
 *
 * @author Powered by Administrator 2021-02-22 21:05
 * @version 1.0.0
 * <p>Copyright  2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p >
 */
@Service("activitytemplateservice")
@Transactional(rollbackFor = {BusinessException.class, RuntimeException.class, Exception.class})
public class ActivityTemplateServiceImpl extends BaseService implements ActivityTemplateService {
    @Resource
    private ActivityTemplateMapper activityTemplateMapper;
    /**
     * 查询活动列表
     *
     * @return 返回列表
     */
    @Override
    public List<ActivityTemplate> queryActivityTemplateList() {
        ActivityTemplateExample example = new ActivityTemplateExample();
        example.or().andIsDeleteEqualTo((byte) 0).andStatusEqualTo((byte) 1);
        return activityTemplateMapper.selectByExample(example);
    }

    /**
     * 查询列表
     *
     * @param name  名称
     * @param code
     * @param page  页码
     * @param limit 条数
     * @param sort  排序
     * @param order 排序
     * @return 返回列表
     */
    @Override
    public List<ActivityTemplate> queryList(String name, String code, Integer page, Integer limit, String sort, String order) {
        ActivityTemplateExample example = new ActivityTemplateExample();
        ActivityTemplateExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        if (!StringUtils.isEmpty(code)) {
            criteria.andCodeEqualTo(code);
        }
        criteria.andIsDeleteEqualTo((byte) 0);
        criteria.andStatusEqualTo((byte) 1);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return activityTemplateMapper.selectByExample(example);
    }

    /**
     * 查询
     *
     * @param id id
     * @return 返回文件信息
     */
    @Override
    public ActivityTemplate queryById(Integer id) {
        return activityTemplateMapper.selectByPrimaryKey(id);
    }

    /**
     * 更新
     *
     * @param template 信息
     * @return 返回文件信息
     */
    @Override
    public int update(ActivityTemplate template) {
        template.setUpdateTime(LocalDateTime.now());
        return activityTemplateMapper.updateByPrimaryKeySelective(template);
    }

    /**
     * 新增
     *
     * @param template 文件信息
     */
    @Override
    public void add(ActivityTemplate template) {
        template.setCreateTime(LocalDateTime.now());
        template.setUpdateTime(LocalDateTime.now());
        activityTemplateMapper.insertSelective(template);
    }

    /**
     * 查询
     *
     * @param code 文件索引
     * @return
     */
    @Override
    public ActivityTemplate queryByCode(String code) {
        ActivityTemplateExample example = new ActivityTemplateExample();
        ActivityTemplateExample.Criteria criteria = example.createCriteria();
        criteria.andIsDeleteEqualTo((byte) 0);
        criteria.andStatusEqualTo((byte) 1);
        if (CheckEmptyUtil.isNotEmpty(code)) {
            criteria.andCodeEqualTo(code);
        }
        return activityTemplateMapper.selectOneByExample(example);
    }

    /**
     * 删除
     *
     * @param id 文件索引
     */
    @Override
    public void deleteById(Integer id) {
        activityTemplateMapper.logicalDeleteByPrimaryKey(id);
    }

    /**
     * 删除
     *
     * @param id 模板ID
     */
    @Override
    public ActivityTemplate queryActivityTemplateById(int id) {
        ActivityTemplateExample example = new ActivityTemplateExample();
        example.or().andIdEqualTo(id).andIsDeleteEqualTo((byte)0).andStatusEqualTo((byte)1);
        return activityTemplateMapper.selectOneByExample(example);
    }
}
