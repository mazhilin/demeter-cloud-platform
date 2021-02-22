package com.demeter.cloud.model.service.impl;

import com.demeter.cloud.model.entity.Company;
import com.demeter.cloud.model.entity.CompanyExample;
import com.demeter.cloud.model.exception.BusinessException;
import com.demeter.cloud.model.mapper.CompanyMapper;
import com.demeter.cloud.model.persistence.service.BaseService;
import com.demeter.cloud.model.service.CompanyService;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>封装Dcloud项目CompanyServiceImpl类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-18 13:18
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
@Service("companyService")
@Transactional(rollbackFor = {BusinessException.class, RuntimeException.class, Exception.class})
public class CompanyServiceImpl extends BaseService implements CompanyService {

    @Resource
    private CompanyMapper companyMapper;

    /**
     * 查询列表
     *
     * @return 返回列表
     */
    @Override
    public List<Company> queryCompanyList() {
        CompanyExample example = new CompanyExample();
        example.or();
        return companyMapper.selectByExample(example);
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
    public List<Company> queryList(String name, String code, Integer page, Integer limit, String sort, String order) {
        CompanyExample example = new CompanyExample();
        CompanyExample.Criteria criteria = example.createCriteria();

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
        return companyMapper.selectByExample(example);
    }


    /**
     * 查询
     *
     * @param id id
     * @return 返回文件信息
     */
    @Override
    public Company queryById(Integer id) {
        CompanyExample example = new CompanyExample();
        example.or().andIsDeleteEqualTo((byte)0).andStatusEqualTo((byte)1);
        return companyMapper.selectOneByExample(example);
    }

    /**
     * 更新
     *
     * @param company 信息
     * @return 返回文件信息
     */
    @Override
    public int update(Company company) {
        company.setUpdateTime(LocalDateTime.now());
        return companyMapper.updateByPrimaryKeySelective(company);
    }

    /**
     * 新增
     *
     * @param company 文件信息
     */
    @Override
    public void add(Company company) {
        company.setCreateTime(LocalDateTime.now());
        company.setUpdateTime(LocalDateTime.now());
        companyMapper.insertSelective(company);
    }

    /**
     * 查询
     *
     * @param code 文件索引
     * @return
     */
    @Override
    public Company queryByCode(String code) {
        return companyMapper.findByCode(code);
    }

    /**
     * 删除
     *
     * @param id 文件索引
     */
    @Override
    public void deleteById(Integer id) {
        companyMapper.logicalDeleteByPrimaryKey(id);
    }
}
