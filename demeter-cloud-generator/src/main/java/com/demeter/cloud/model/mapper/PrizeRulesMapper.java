package com.demeter.cloud.model.mapper;

import com.demeter.cloud.model.entity.PrizeRules;
import com.demeter.cloud.model.entity.PrizeRulesExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PrizeRulesMapper {
    long countByExample(PrizeRulesExample example);

    int deleteByExample(PrizeRulesExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PrizeRules record);

    int insertSelective(PrizeRules record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_prize_rules
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    PrizeRules selectOneByExample(PrizeRulesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_prize_rules
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    PrizeRules selectOneByExampleSelective(@Param("example") PrizeRulesExample example, @Param("selective") PrizeRules.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_prize_rules
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    List<PrizeRules> selectByExampleSelective(@Param("example") PrizeRulesExample example, @Param("selective") PrizeRules.Column ... selective);

    List<PrizeRules> selectByExample(PrizeRulesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_prize_rules
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    PrizeRules selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") PrizeRules.Column ... selective);

    PrizeRules selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_prize_rules
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    PrizeRules selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    int updateByExampleSelective(@Param("record") PrizeRules record, @Param("example") PrizeRulesExample example);

    int updateByExample(@Param("record") PrizeRules record, @Param("example") PrizeRulesExample example);

    int updateByPrimaryKeySelective(PrizeRules record);

    int updateByPrimaryKey(PrizeRules record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_prize_rules
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int logicalDeleteByExample(@Param("example") PrizeRulesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_prize_rules
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int logicalDeleteByPrimaryKey(Integer id);
}