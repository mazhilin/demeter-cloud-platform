package com.demeter.cloud.model.data;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>封装Dcloud项目Region类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-03-04 22:42
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
public class RegionData implements Serializable {
    private static final long serialVersionUID = 7207505273143285013L;
    /**
     * 区域ID
     */
    private Integer value;
    /**
     * 区域名称
     */
    private String label;
    /**
     * 区域编码
     */
    private String code;
    /**
     * 区域类型
     */
    private Integer type;
    /**
     * 区域层级
     */
    private String level;
    /**
     * 区域子集
     */
    private List<RegionData> child;

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public List<RegionData> getChild() {
        return child;
    }

    public void setChild(List<RegionData> child) {
        this.child = child;
    }
}
