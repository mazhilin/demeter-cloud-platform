package com.demeter.cloud.model.service.impl;

import com.demeter.cloud.model.entity.PrizeRules;
import com.demeter.cloud.model.entity.PrizeRulesExample;
import com.demeter.cloud.model.exception.BusinessException;
import com.demeter.cloud.model.mapper.PrizeRulesMapper;
import com.demeter.cloud.model.service.PrizeRulesService;
import com.demeter.cloud.persistence.service.BaseService;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>封装Dcloud项目PrizeRulesServiceImpl类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-03-10 11:26
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
@Service("prizeRulesService")
@Transactional(rollbackFor = {BusinessException.class, RuntimeException.class, Exception.class})
public class PrizeRulesServiceImpl extends BaseService implements PrizeRulesService {

    @Resource
    private PrizeRulesMapper prizeRulesMapper;

    /**
     * 查询列表
     *
     * @return 返回列表
     */
    @Override
    public List<PrizeRules> queryOrderList() {
        PrizeRulesExample example = new PrizeRulesExample();
        example.or().andIsDeleteEqualTo(false).andStatusEqualTo(true);
        return prizeRulesMapper.selectByExample(example);
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
    public List<PrizeRules> queryList(String name, String code, Integer page, Integer limit, String sort, String order) {
        PrizeRulesExample example = new PrizeRulesExample();
        PrizeRulesExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(name)) {

        }
        if (!StringUtils.isEmpty(code)) {

        }
        criteria.andIsDeleteEqualTo(false);
        criteria.andStatusEqualTo(true);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return prizeRulesMapper.selectByExample(example);
    }

    /**
     * 查询
     *
     * @param id id
     * @return 返回文件信息
     */
    @Override
    public PrizeRules queryById(Integer id) {
        return prizeRulesMapper.selectByPrimaryKey(id);
    }

    /**
     * 更新
     *
     * @param prizeRules 信息
     * @return 返回文件信息
     */
    @Override
    public int update(PrizeRules prizeRules) {
        prizeRules.setUpdateTime(LocalDateTime.now());
        return prizeRulesMapper.updateByPrimaryKeySelective(prizeRules);
    }

    /**
     * 新增
     *
     * @param prizeRules 文件信息
     */
    @Override
    public void add(PrizeRules prizeRules) {
        prizeRules.setCreateTime(LocalDateTime.now());
        prizeRules.setUpdateTime(LocalDateTime.now());
        prizeRulesMapper.insertSelective(prizeRules);
    }

    /**
     * 删除
     *
     * @param id 文件索引
     */
    @Override
    public void deleteById(Integer id) {
        prizeRulesMapper.logicalDeleteByPrimaryKey(id);
    }
}
