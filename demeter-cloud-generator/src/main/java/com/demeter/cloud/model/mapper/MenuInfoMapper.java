package com.demeter.cloud.model.mapper;

import com.demeter.cloud.model.entity.MenuInfo;
import com.demeter.cloud.model.entity.MenuInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MenuInfoMapper {
    long countByExample(MenuInfoExample example);

    int deleteByExample(MenuInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MenuInfo record);

    int insertSelective(MenuInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_menu_info
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    MenuInfo selectOneByExample(MenuInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_menu_info
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    MenuInfo selectOneByExampleSelective(@Param("example") MenuInfoExample example, @Param("selective") MenuInfo.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_menu_info
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    List<MenuInfo> selectByExampleSelective(@Param("example") MenuInfoExample example, @Param("selective") MenuInfo.Column ... selective);

    List<MenuInfo> selectByExample(MenuInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_menu_info
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    MenuInfo selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") MenuInfo.Column ... selective);

    MenuInfo selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_menu_info
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    MenuInfo selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    int updateByExampleSelective(@Param("record") MenuInfo record, @Param("example") MenuInfoExample example);

    int updateByExample(@Param("record") MenuInfo record, @Param("example") MenuInfoExample example);

    int updateByPrimaryKeySelective(MenuInfo record);

    int updateByPrimaryKey(MenuInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_menu_info
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int logicalDeleteByExample(@Param("example") MenuInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_menu_info
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int logicalDeleteByPrimaryKey(Integer id);
}