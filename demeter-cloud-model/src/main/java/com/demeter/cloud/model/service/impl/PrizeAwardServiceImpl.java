package com.demeter.cloud.model.service.impl;

import com.demeter.cloud.model.entity.OrderInfoExample;
import com.demeter.cloud.model.entity.PrizeAward;
import com.demeter.cloud.model.entity.PrizeAwardExample;
import com.demeter.cloud.model.exception.BusinessException;
import com.demeter.cloud.model.mapper.PrizeAwardMapper;
import com.demeter.cloud.model.service.PrizeAwardService;
import com.demeter.cloud.persistence.service.BaseService;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>封装Dcloud项目PrizeAwardServiceImpl类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-03-10 11:22
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */

@Service("prizeAwardService")
@Transactional(rollbackFor = {BusinessException.class, RuntimeException.class, Exception.class})
public class PrizeAwardServiceImpl extends BaseService implements PrizeAwardService {

    @Resource
    private PrizeAwardMapper prizeAwardMapper;

    /**
     * 查询列表
     *
     * @return 返回列表
     */
    @Override
    public List<PrizeAward> queryOrderList() {
        PrizeAwardExample example = new PrizeAwardExample();
        example.or().andIsDeleteEqualTo(false).andStatusEqualTo(true);
        return prizeAwardMapper.selectByExample(example);
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
    public List<PrizeAward> queryList(String name, String code, Integer page, Integer limit, String sort, String order) {
        PrizeAwardExample example = new PrizeAwardExample();
        PrizeAwardExample.Criteria criteria = example.createCriteria();

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
        return prizeAwardMapper.selectByExample(example);
    }

    /**
     * 查询
     *
     * @param id id
     * @return 返回文件信息
     */
    @Override
    public PrizeAward queryById(Integer id) {
        return prizeAwardMapper.selectByPrimaryKey(id);
    }

    /**
     * 更新
     *
     * @param prizeAward 信息
     * @return 返回文件信息
     */
    @Override
    public int update(PrizeAward prizeAward) {
        prizeAward.setUpdateTime(LocalDateTime.now());
        return prizeAwardMapper.updateByPrimaryKeySelective(prizeAward);
    }

    /**
     * 新增
     *
     * @param prizeAward 文件信息
     */
    @Override
    public void add(PrizeAward prizeAward) {
        prizeAward.setCreateTime(LocalDateTime.now());
        prizeAward.setUpdateTime(LocalDateTime.now());
        prizeAwardMapper.insertSelective(prizeAward);
    }

    /**
     * 删除
     *
     * @param id 文件索引
     */
    @Override
    public void deleteById(Integer id) {
        prizeAwardMapper.logicalDeleteByPrimaryKey(id);
    }
}
