package com.demeter.cloud.model.mapper;

import com.demeter.cloud.model.entity.RegionInfo;
import com.demeter.cloud.model.entity.RegionInfoExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RegionInfoMapper {
    long countByExample(RegionInfoExample example);

    int deleteByExample(RegionInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(RegionInfo record);

    int insertSelective(RegionInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_region_info
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    RegionInfo selectOneByExample(RegionInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_region_info
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    RegionInfo selectOneByExampleSelective(@Param("example") RegionInfoExample example, @Param("selective") RegionInfo.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_region_info
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    List<RegionInfo> selectByExampleSelective(@Param("example") RegionInfoExample example, @Param("selective") RegionInfo.Column ... selective);

    List<RegionInfo> selectByExample(RegionInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_region_info
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    RegionInfo selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") RegionInfo.Column ... selective);

    RegionInfo selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_region_info
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    RegionInfo selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    int updateByExampleSelective(@Param("record") RegionInfo record, @Param("example") RegionInfoExample example);

    int updateByExample(@Param("record") RegionInfo record, @Param("example") RegionInfoExample example);

    int updateByPrimaryKeySelective(RegionInfo record);

    int updateByPrimaryKey(RegionInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_region_info
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int logicalDeleteByExample(@Param("example") RegionInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_region_info
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int logicalDeleteByPrimaryKey(Integer id);
}