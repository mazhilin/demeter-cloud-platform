package com.demeter.cloud.model.service.impl;

import com.demeter.cloud.model.entity.ActivityInfo;
import com.demeter.cloud.model.entity.ActivityInfoExample;
import com.demeter.cloud.model.exception.BusinessException;
import com.demeter.cloud.model.mapper.ActivityInfoMapper;
import com.demeter.cloud.persistence.service.BaseService;
import com.demeter.cloud.model.service.ActivityInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>封装Dcloud项目ActivityInfoServiceImp类.<br></p >
 * <p>//TODO...<br></p >
 *
 * @author Powered by Administrator 2021-02-20 19:47
 * @version 1.0.0
 * <p>Copyright  2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p >
 */
@Service("activityInfoService")
@Transactional(rollbackFor = {BusinessException.class, RuntimeException.class, Exception.class})
public class ActivityInfoServiceImpl extends BaseService implements ActivityInfoService {
    @Resource
    private ActivityInfoMapper activityInfoMapper;
    /**
     * 查询活动列表
     *
     * @return 返回列表
     */
    @Override
    public List<ActivityInfo> queryActivityInfoList() {
        ActivityInfoExample example = new ActivityInfoExample();
        example.or().andIsDeleteEqualTo((byte)0).andStatusEqualTo((byte)1);
        return activityInfoMapper.selectByExample(example);
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
    public List<ActivityInfo> queryList(String name, String code, Integer page, Integer limit, String sort, String order) {
        return null;
    }

    /**
     * 查询
     *
     * @param id id
     * @return 返回文件信息
     */
    @Override
    public ActivityInfo queryById(Integer id) {
        return null;
    }

    /**
     * 更新
     *
     * @param activity 信息
     * @return 返回文件信息
     */
    @Override
    public int update(ActivityInfo activity) {
        return 0;
    }

    /**
     * 新增
     *
     * @param activity 文件信息
     */
    @Override
    public void add(ActivityInfo activity) {

    }

    /**
     * 查询
     *
     * @param code 文件索引
     * @return
     */
    @Override
    public ActivityInfo queryByCode(String code) {
        return null;
    }

    /**
     * 删除
     *
     * @param id 文件索引
     */
    @Override
    public void deleteById(Integer id) {

    }
}
