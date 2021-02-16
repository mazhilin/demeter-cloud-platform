package com.demeter.cloud.model.mapper;

import com.demeter.cloud.model.entity.AdminUser;
import com.demeter.cloud.model.entity.AdminUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AdminUserMapper {
    long countByExample(AdminUserExample example);

    int deleteByExample(AdminUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AdminUser record);

    int insertSelective(AdminUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_admin_user
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    AdminUser selectOneByExample(AdminUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_admin_user
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    AdminUser selectOneByExampleSelective(@Param("example") AdminUserExample example, @Param("selective") AdminUser.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_admin_user
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    List<AdminUser> selectByExampleSelective(@Param("example") AdminUserExample example, @Param("selective") AdminUser.Column ... selective);

    List<AdminUser> selectByExample(AdminUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_admin_user
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    AdminUser selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") AdminUser.Column ... selective);

    AdminUser selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_admin_user
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    AdminUser selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    int updateByExampleSelective(@Param("record") AdminUser record, @Param("example") AdminUserExample example);

    int updateByExample(@Param("record") AdminUser record, @Param("example") AdminUserExample example);

    int updateByPrimaryKeySelective(AdminUser record);

    int updateByPrimaryKey(AdminUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_admin_user
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int logicalDeleteByExample(@Param("example") AdminUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_admin_user
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int logicalDeleteByPrimaryKey(Integer id);
}