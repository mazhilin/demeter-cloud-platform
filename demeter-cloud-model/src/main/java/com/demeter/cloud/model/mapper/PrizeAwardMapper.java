package com.demeter.cloud.model.mapper;

import com.demeter.cloud.model.entity.PrizeAward;
import com.demeter.cloud.model.entity.PrizeAwardExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PrizeAwardMapper {
    long countByExample(PrizeAwardExample example);

    int deleteByExample(PrizeAwardExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PrizeAward record);

    int insertSelective(PrizeAward record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_prize_award
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    PrizeAward selectOneByExample(PrizeAwardExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_prize_award
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    PrizeAward selectOneByExampleSelective(@Param("example") PrizeAwardExample example, @Param("selective") PrizeAward.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_prize_award
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    List<PrizeAward> selectByExampleSelective(@Param("example") PrizeAwardExample example, @Param("selective") PrizeAward.Column ... selective);

    List<PrizeAward> selectByExample(PrizeAwardExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_prize_award
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    PrizeAward selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") PrizeAward.Column ... selective);

    PrizeAward selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_prize_award
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    PrizeAward selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    int updateByExampleSelective(@Param("record") PrizeAward record, @Param("example") PrizeAwardExample example);

    int updateByExample(@Param("record") PrizeAward record, @Param("example") PrizeAwardExample example);

    int updateByPrimaryKeySelective(PrizeAward record);

    int updateByPrimaryKey(PrizeAward record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_prize_award
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int logicalDeleteByExample(@Param("example") PrizeAwardExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_prize_award
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int logicalDeleteByPrimaryKey(Integer id);
}