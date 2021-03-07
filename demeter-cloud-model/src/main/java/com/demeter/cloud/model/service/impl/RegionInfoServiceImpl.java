package com.demeter.cloud.model.service.impl;

import com.demeter.cloud.model.entity.RegionInfo;
import com.demeter.cloud.model.entity.RegionInfoExample;
import com.demeter.cloud.model.exception.BusinessException;
import com.demeter.cloud.model.mapper.RegionInfoMapper;
import com.demeter.cloud.persistence.service.BaseService;
import com.demeter.cloud.model.service.RegionInfoService;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>封装Dcloud项目RegionInfoServiceImpl类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-20 19:37
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
@Service("regionInfoService")
@Transactional(rollbackFor = {BusinessException.class, RuntimeException.class, Exception.class})
public class RegionInfoServiceImpl extends BaseService implements RegionInfoService {

    @Resource
    private RegionInfoMapper regionInfoMapper;

    /**
     * 查询区域列表
     *
     * @return 返回列表
     */
    @Override
    public List<RegionInfo> queryRegionList() {
        RegionInfoExample example = new RegionInfoExample();
        example.or().andIsDeleteEqualTo((byte) 0).andStatusEqualTo((byte) 1);
        return regionInfoMapper.selectByExample(example);
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
    public List<RegionInfo> queryList(String name, Integer code, Integer page, Integer limit, String sort, String order) {
        RegionInfoExample example = new RegionInfoExample();
        RegionInfoExample.Criteria criteria = example.createCriteria();

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

        PageHelper.startPage(page, limit
        );
        return regionInfoMapper.selectByExample(example);
    }

    /**
     * 查询
     *
     * @param id id
     * @return 返回文件信息
     */
    @Override
    public RegionInfo queryById(Integer id) {
        return regionInfoMapper.selectByPrimaryKey(id);
    }

    /**
     * 更新
     *
     * @param region 信息
     * @return 返回文件信息
     */
    @Override
    public int update(RegionInfo region) {
        region.setUpdateTime(LocalDateTime.now());
        return regionInfoMapper.updateByPrimaryKeySelective(region);
    }

    /**
     * 新增
     *
     * @param region 文件信息
     */
    @Override
    public void add(RegionInfo region) {
        region.setCreateTime(LocalDateTime.now());
        region.setUpdateTime(LocalDateTime.now());
        regionInfoMapper.insertSelective(region);
    }

    /**
     * 查询
     *
     * @param code 文件索引
     * @return
     */
    @Override
    public RegionInfo queryByCode(Integer code) {
        RegionInfoExample example = new RegionInfoExample();
        example.or().andCodeEqualTo(code).andIsDeleteEqualTo((byte) 0).andStatusEqualTo((byte) 1);
        return regionInfoMapper.selectOneByExample(example);
    }

    /**
     * 删除
     *
     * @param id 文件索引
     */
    @Override
    public void deleteById(Integer id) {
        regionInfoMapper.logicalDeleteByPrimaryKey(id);
    }

    /**
     * 查询省份列表-queryProvinceList
     *
     * @return 返回省份列表
     */
    @Override
    public List<RegionInfo> queryProvinceList() {
        RegionInfoExample example = new RegionInfoExample();
        byte province = 1;
        example.or().andParentIdEqualTo(0).andTypeEqualTo(province).andIsDeleteEqualTo((byte) 0).andStatusEqualTo((byte) 1);
        return regionInfoMapper.selectByExample(example);
    }

    /**
     * 根据父级编码查询城市列表
     *
     * @param parentId 父级编码
     * @return 返回列表
     */
    @Override
    public List<RegionInfo> queryCityListByParentId(Integer parentId) {
        RegionInfoExample example = new RegionInfoExample();
        byte city = 2;
        example.or().andParentIdEqualTo(parentId).andTypeEqualTo(city).andIsDeleteEqualTo((byte) 0).andStatusEqualTo((byte) 1);
        return regionInfoMapper.selectByExample(example);
    }

    /**
     * 根据父级编码查询区县列表
     *
     * @param parentId 父级编码
     * @return 返回列表
     */
    @Override
    public List<RegionInfo> queryDistrictListByParentId(Integer parentId) {
        RegionInfoExample example = new RegionInfoExample();
        byte district = 3;
        example.or().andParentIdEqualTo(parentId).andTypeEqualTo(district).andIsDeleteEqualTo((byte) 0).andStatusEqualTo((byte) 1);
        return regionInfoMapper.selectByExample(example);
    }
}
