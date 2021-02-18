package com.demeter.cloud.model.mapper;

import com.demeter.cloud.model.entity.DictionaryItem;
import com.demeter.cloud.model.entity.DictionaryItemExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DictionaryItemMapper {
    long countByExample(DictionaryItemExample example);

    int deleteByExample(DictionaryItemExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DictionaryItem record);

    int insertSelective(DictionaryItem record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_dictionary_item
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    DictionaryItem selectOneByExample(DictionaryItemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_dictionary_item
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    DictionaryItem selectOneByExampleSelective(@Param("example") DictionaryItemExample example, @Param("selective") DictionaryItem.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_dictionary_item
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    List<DictionaryItem> selectByExampleSelective(@Param("example") DictionaryItemExample example, @Param("selective") DictionaryItem.Column ... selective);

    List<DictionaryItem> selectByExample(DictionaryItemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_dictionary_item
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    DictionaryItem selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") DictionaryItem.Column ... selective);

    DictionaryItem selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_dictionary_item
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    DictionaryItem selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    int updateByExampleSelective(@Param("record") DictionaryItem record, @Param("example") DictionaryItemExample example);

    int updateByExample(@Param("record") DictionaryItem record, @Param("example") DictionaryItemExample example);

    int updateByPrimaryKeySelective(DictionaryItem record);

    int updateByPrimaryKey(DictionaryItem record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_dictionary_item
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int logicalDeleteByExample(@Param("example") DictionaryItemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_dictionary_item
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int logicalDeleteByPrimaryKey(Integer id);
}