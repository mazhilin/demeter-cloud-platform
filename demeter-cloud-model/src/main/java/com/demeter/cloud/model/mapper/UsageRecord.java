package com.demeter.cloud.model.mapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class UsageRecord {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table demeter_usage_record
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    public static final Boolean NOT_DELETED = false;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table demeter_usage_record
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    public static final Boolean IS_DELETED = true;

    private String id;

    private String code;

    private Integer activityId;

    private String activityCode;

    private String activityName;

    private Integer customerId;

    private String customerName;

    private Integer prizeItemId;

    private String prizeItemName;

    private Integer prizeAwardId;

    private String prizeAwardName;

    private Integer prizeAwardNumber;

    private Boolean isDelete;

    private String createBy;

    private LocalDateTime createTime;

    private String updateBy;

    private LocalDateTime updateTime;

    private Boolean status;

    private String remark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public String getActivityCode() {
        return activityCode;
    }

    public void setActivityCode(String activityCode) {
        this.activityCode = activityCode;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Integer getPrizeItemId() {
        return prizeItemId;
    }

    public void setPrizeItemId(Integer prizeItemId) {
        this.prizeItemId = prizeItemId;
    }

    public String getPrizeItemName() {
        return prizeItemName;
    }

    public void setPrizeItemName(String prizeItemName) {
        this.prizeItemName = prizeItemName;
    }

    public Integer getPrizeAwardId() {
        return prizeAwardId;
    }

    public void setPrizeAwardId(Integer prizeAwardId) {
        this.prizeAwardId = prizeAwardId;
    }

    public String getPrizeAwardName() {
        return prizeAwardName;
    }

    public void setPrizeAwardName(String prizeAwardName) {
        this.prizeAwardName = prizeAwardName;
    }

    public Integer getPrizeAwardNumber() {
        return prizeAwardNumber;
    }

    public void setPrizeAwardNumber(Integer prizeAwardNumber) {
        this.prizeAwardNumber = prizeAwardNumber;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", code=").append(code);
        sb.append(", activityId=").append(activityId);
        sb.append(", activityCode=").append(activityCode);
        sb.append(", activityName=").append(activityName);
        sb.append(", customerId=").append(customerId);
        sb.append(", customerName=").append(customerName);
        sb.append(", prizeItemId=").append(prizeItemId);
        sb.append(", prizeItemName=").append(prizeItemName);
        sb.append(", prizeAwardId=").append(prizeAwardId);
        sb.append(", prizeAwardName=").append(prizeAwardName);
        sb.append(", prizeAwardNumber=").append(prizeAwardNumber);
        sb.append(", isDelete=").append(isDelete);
        sb.append(", createBy=").append(createBy);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateBy=").append(updateBy);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", status=").append(status);
        sb.append(", remark=").append(remark);
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        UsageRecord other = (UsageRecord) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCode() == null ? other.getCode() == null : this.getCode().equals(other.getCode()))
            && (this.getActivityId() == null ? other.getActivityId() == null : this.getActivityId().equals(other.getActivityId()))
            && (this.getActivityCode() == null ? other.getActivityCode() == null : this.getActivityCode().equals(other.getActivityCode()))
            && (this.getActivityName() == null ? other.getActivityName() == null : this.getActivityName().equals(other.getActivityName()))
            && (this.getCustomerId() == null ? other.getCustomerId() == null : this.getCustomerId().equals(other.getCustomerId()))
            && (this.getCustomerName() == null ? other.getCustomerName() == null : this.getCustomerName().equals(other.getCustomerName()))
            && (this.getPrizeItemId() == null ? other.getPrizeItemId() == null : this.getPrizeItemId().equals(other.getPrizeItemId()))
            && (this.getPrizeItemName() == null ? other.getPrizeItemName() == null : this.getPrizeItemName().equals(other.getPrizeItemName()))
            && (this.getPrizeAwardId() == null ? other.getPrizeAwardId() == null : this.getPrizeAwardId().equals(other.getPrizeAwardId()))
            && (this.getPrizeAwardName() == null ? other.getPrizeAwardName() == null : this.getPrizeAwardName().equals(other.getPrizeAwardName()))
            && (this.getPrizeAwardNumber() == null ? other.getPrizeAwardNumber() == null : this.getPrizeAwardNumber().equals(other.getPrizeAwardNumber()))
            && (this.getIsDelete() == null ? other.getIsDelete() == null : this.getIsDelete().equals(other.getIsDelete()))
            && (this.getCreateBy() == null ? other.getCreateBy() == null : this.getCreateBy().equals(other.getCreateBy()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateBy() == null ? other.getUpdateBy() == null : this.getUpdateBy().equals(other.getUpdateBy()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCode() == null) ? 0 : getCode().hashCode());
        result = prime * result + ((getActivityId() == null) ? 0 : getActivityId().hashCode());
        result = prime * result + ((getActivityCode() == null) ? 0 : getActivityCode().hashCode());
        result = prime * result + ((getActivityName() == null) ? 0 : getActivityName().hashCode());
        result = prime * result + ((getCustomerId() == null) ? 0 : getCustomerId().hashCode());
        result = prime * result + ((getCustomerName() == null) ? 0 : getCustomerName().hashCode());
        result = prime * result + ((getPrizeItemId() == null) ? 0 : getPrizeItemId().hashCode());
        result = prime * result + ((getPrizeItemName() == null) ? 0 : getPrizeItemName().hashCode());
        result = prime * result + ((getPrizeAwardId() == null) ? 0 : getPrizeAwardId().hashCode());
        result = prime * result + ((getPrizeAwardName() == null) ? 0 : getPrizeAwardName().hashCode());
        result = prime * result + ((getPrizeAwardNumber() == null) ? 0 : getPrizeAwardNumber().hashCode());
        result = prime * result + ((getIsDelete() == null) ? 0 : getIsDelete().hashCode());
        result = prime * result + ((getCreateBy() == null) ? 0 : getCreateBy().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateBy() == null) ? 0 : getUpdateBy().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        return result;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table demeter_usage_record
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    public void andLogicalDeleted(boolean deleted) {
        setIsDelete(deleted ? IS_DELETED : NOT_DELETED);
    }

    /**
     * This enum was generated by MyBatis Generator.
     * This enum corresponds to the database table demeter_usage_record
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    public enum Column {
        id("id", "id", "VARCHAR", false),
        code("code", "code", "VARCHAR", false),
        activityId("activity_id", "activityId", "INTEGER", false),
        activityCode("activity_code", "activityCode", "VARCHAR", false),
        activityName("activity_name", "activityName", "VARCHAR", false),
        customerId("customer_id", "customerId", "INTEGER", false),
        customerName("customer_name", "customerName", "VARCHAR", false),
        prizeItemId("prize_item_id", "prizeItemId", "INTEGER", false),
        prizeItemName("prize_item_name", "prizeItemName", "VARCHAR", false),
        prizeAwardId("prize_award_id", "prizeAwardId", "INTEGER", false),
        prizeAwardName("prize_award_name", "prizeAwardName", "VARCHAR", false),
        prizeAwardNumber("prize_award_number", "prizeAwardNumber", "INTEGER", false),
        isDelete("is_delete", "isDelete", "BIT", false),
        createBy("create_by", "createBy", "VARCHAR", false),
        createTime("create_time", "createTime", "TIMESTAMP", false),
        updateBy("update_by", "updateBy", "VARCHAR", false),
        updateTime("update_time", "updateTime", "TIMESTAMP", false),
        status("status", "status", "BIT", true),
        remark("remark", "remark", "VARCHAR", false);

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table demeter_usage_record
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private static final String BEGINNING_DELIMITER = "`";

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table demeter_usage_record
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private static final String ENDING_DELIMITER = "`";

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table demeter_usage_record
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final String column;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table demeter_usage_record
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final boolean isColumnNameDelimited;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table demeter_usage_record
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final String javaProperty;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table demeter_usage_record
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final String jdbcType;

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table demeter_usage_record
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String value() {
            return this.column;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table demeter_usage_record
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getValue() {
            return this.column;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table demeter_usage_record
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getJavaProperty() {
            return this.javaProperty;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table demeter_usage_record
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getJdbcType() {
            return this.jdbcType;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table demeter_usage_record
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        Column(String column, String javaProperty, String jdbcType, boolean isColumnNameDelimited) {
            this.column = column;
            this.javaProperty = javaProperty;
            this.jdbcType = jdbcType;
            this.isColumnNameDelimited = isColumnNameDelimited;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table demeter_usage_record
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String desc() {
            return this.getEscapedColumnName() + " DESC";
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table demeter_usage_record
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String asc() {
            return this.getEscapedColumnName() + " ASC";
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table demeter_usage_record
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public static Column[] excludes(Column ... excludes) {
            ArrayList<Column> columns = new ArrayList<>(Arrays.asList(Column.values()));
            if (excludes != null && excludes.length > 0) {
                columns.removeAll(new ArrayList<>(Arrays.asList(excludes)));
            }
            return columns.toArray(new Column[]{});
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table demeter_usage_record
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getEscapedColumnName() {
            if (this.isColumnNameDelimited) {
                return new StringBuilder().append(BEGINNING_DELIMITER).append(this.column).append(ENDING_DELIMITER).toString();
            } else {
                return this.column;
            }
        }
    }
}