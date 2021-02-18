package com.demeter.cloud.model.mapper;

import com.demeter.cloud.model.entity.PermissionInfo;
import com.demeter.cloud.model.entity.PermissionInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PermissionInfoMapper {
    long countByExample(PermissionInfoExample example);

    int deleteByExample(PermissionInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PermissionInfo record);

    int insertSelective(PermissionInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_permission_info
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    PermissionInfo selectOneByExample(PermissionInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_permission_info
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    PermissionInfo selectOneByExampleSelective(@Param("example") PermissionInfoExample example, @Param("selective") PermissionInfo.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_permission_info
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    List<PermissionInfo> selectByExampleSelective(@Param("example") PermissionInfoExample example, @Param("selective") PermissionInfo.Column ... selective);

    List<PermissionInfo> selectByExample(PermissionInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_permission_info
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    PermissionInfo selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") PermissionInfo.Column ... selective);

    PermissionInfo selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_permission_info
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    PermissionInfo selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    int updateByExampleSelective(@Param("record") PermissionInfo record, @Param("example") PermissionInfoExample example);

    int updateByExample(@Param("record") PermissionInfo record, @Param("example") PermissionInfoExample example);

    int updateByPrimaryKeySelective(PermissionInfo record);

    int updateByPrimaryKey(PermissionInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_permission_info
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int logicalDeleteByExample(@Param("example") PermissionInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_permission_info
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int logicalDeleteByPrimaryKey(Integer id);
}