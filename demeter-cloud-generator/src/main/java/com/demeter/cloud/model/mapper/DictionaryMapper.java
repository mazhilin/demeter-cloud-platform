package com.demeter.cloud.model.mapper;

import com.demeter.cloud.model.entity.Dictionary;
import com.demeter.cloud.model.entity.DictionaryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DictionaryMapper {
    long countByExample(DictionaryExample example);

    int deleteByExample(DictionaryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Dictionary record);

    int insertSelective(Dictionary record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_dictionary
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    Dictionary selectOneByExample(DictionaryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_dictionary
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    Dictionary selectOneByExampleSelective(@Param("example") DictionaryExample example, @Param("selective") Dictionary.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_dictionary
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    List<Dictionary> selectByExampleSelective(@Param("example") DictionaryExample example, @Param("selective") Dictionary.Column ... selective);

    List<Dictionary> selectByExample(DictionaryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_dictionary
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    Dictionary selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") Dictionary.Column ... selective);

    Dictionary selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_dictionary
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    Dictionary selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    int updateByExampleSelective(@Param("record") Dictionary record, @Param("example") DictionaryExample example);

    int updateByExample(@Param("record") Dictionary record, @Param("example") DictionaryExample example);

    int updateByPrimaryKeySelective(Dictionary record);

    int updateByPrimaryKey(Dictionary record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_dictionary
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int logicalDeleteByExample(@Param("example") DictionaryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_dictionary
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int logicalDeleteByPrimaryKey(Integer id);
}