package com.demeter.cloud.model.mapper;

import com.demeter.cloud.model.entity.RoleInfo;
import com.demeter.cloud.model.entity.RoleInfoExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleInfoMapper {
    long countByExample(RoleInfoExample example);

    int deleteByExample(RoleInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(RoleInfo record);

    int insertSelective(RoleInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_role_info
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    RoleInfo selectOneByExample(RoleInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_role_info
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    RoleInfo selectOneByExampleSelective(@Param("example") RoleInfoExample example, @Param("selective") RoleInfo.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_role_info
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    List<RoleInfo> selectByExampleSelective(@Param("example") RoleInfoExample example, @Param("selective") RoleInfo.Column ... selective);

    List<RoleInfo> selectByExample(RoleInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_role_info
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    RoleInfo selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") RoleInfo.Column ... selective);

    RoleInfo selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_role_info
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    RoleInfo selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    int updateByExampleSelective(@Param("record") RoleInfo record, @Param("example") RoleInfoExample example);

    int updateByExample(@Param("record") RoleInfo record, @Param("example") RoleInfoExample example);

    int updateByPrimaryKeySelective(RoleInfo record);

    int updateByPrimaryKey(RoleInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_role_info
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int logicalDeleteByExample(@Param("example") RoleInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_role_info
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int logicalDeleteByPrimaryKey(Integer id);
}