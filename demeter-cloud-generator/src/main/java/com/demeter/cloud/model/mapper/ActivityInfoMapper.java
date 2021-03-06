package com.demeter.cloud.model.mapper;

import com.demeter.cloud.model.entity.ActivityInfo;
import com.demeter.cloud.model.entity.ActivityInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ActivityInfoMapper {
    long countByExample(ActivityInfoExample example);

    int deleteByExample(ActivityInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ActivityInfo record);

    int insertSelective(ActivityInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_activity_info
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    ActivityInfo selectOneByExample(ActivityInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_activity_info
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    ActivityInfo selectOneByExampleSelective(@Param("example") ActivityInfoExample example, @Param("selective") ActivityInfo.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_activity_info
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    List<ActivityInfo> selectByExampleSelective(@Param("example") ActivityInfoExample example, @Param("selective") ActivityInfo.Column ... selective);

    List<ActivityInfo> selectByExample(ActivityInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_activity_info
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    ActivityInfo selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") ActivityInfo.Column ... selective);

    ActivityInfo selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_activity_info
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    ActivityInfo selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    int updateByExampleSelective(@Param("record") ActivityInfo record, @Param("example") ActivityInfoExample example);

    int updateByExample(@Param("record") ActivityInfo record, @Param("example") ActivityInfoExample example);

    int updateByPrimaryKeySelective(ActivityInfo record);

    int updateByPrimaryKey(ActivityInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_activity_info
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int logicalDeleteByExample(@Param("example") ActivityInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_activity_info
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int logicalDeleteByPrimaryKey(Integer id);
}