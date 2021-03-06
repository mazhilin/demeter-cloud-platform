package com.demeter.cloud.model.mapper;

import com.demeter.cloud.model.entity.RoleExt;
import com.demeter.cloud.model.entity.RoleExtExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleExtMapper {
    long countByExample(RoleExtExample example);

    int deleteByExample(RoleExtExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(RoleExt record);

    int insertSelective(RoleExt record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_role_ext
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    RoleExt selectOneByExample(RoleExtExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_role_ext
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    RoleExt selectOneByExampleSelective(@Param("example") RoleExtExample example, @Param("selective") RoleExt.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_role_ext
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    List<RoleExt> selectByExampleSelective(@Param("example") RoleExtExample example, @Param("selective") RoleExt.Column ... selective);

    List<RoleExt> selectByExample(RoleExtExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_role_ext
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    RoleExt selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") RoleExt.Column ... selective);

    RoleExt selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_role_ext
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    RoleExt selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    int updateByExampleSelective(@Param("record") RoleExt record, @Param("example") RoleExtExample example);

    int updateByExample(@Param("record") RoleExt record, @Param("example") RoleExtExample example);

    int updateByPrimaryKeySelective(RoleExt record);

    int updateByPrimaryKey(RoleExt record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_role_ext
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int logicalDeleteByExample(@Param("example") RoleExtExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_role_ext
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int logicalDeleteByPrimaryKey(Integer id);
}