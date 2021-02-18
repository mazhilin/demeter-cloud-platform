package com.demeter.cloud.model.mapper;

import com.demeter.cloud.model.entity.CustomerUser;
import com.demeter.cloud.model.entity.CustomerUserExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CustomerUserMapper {
    long countByExample(CustomerUserExample example);

    int deleteByExample(CustomerUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CustomerUser record);

    int insertSelective(CustomerUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_customer_user
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    CustomerUser selectOneByExample(CustomerUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_customer_user
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    CustomerUser selectOneByExampleSelective(@Param("example") CustomerUserExample example, @Param("selective") CustomerUser.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_customer_user
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    List<CustomerUser> selectByExampleSelective(@Param("example") CustomerUserExample example, @Param("selective") CustomerUser.Column ... selective);

    List<CustomerUser> selectByExample(CustomerUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_customer_user
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    CustomerUser selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") CustomerUser.Column ... selective);

    CustomerUser selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_customer_user
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    CustomerUser selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    int updateByExampleSelective(@Param("record") CustomerUser record, @Param("example") CustomerUserExample example);

    int updateByExample(@Param("record") CustomerUser record, @Param("example") CustomerUserExample example);

    int updateByPrimaryKeySelective(CustomerUser record);

    int updateByPrimaryKey(CustomerUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_customer_user
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int logicalDeleteByExample(@Param("example") CustomerUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_customer_user
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int logicalDeleteByPrimaryKey(Integer id);
}