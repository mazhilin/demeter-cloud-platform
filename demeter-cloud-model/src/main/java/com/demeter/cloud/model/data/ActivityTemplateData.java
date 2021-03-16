package com.demeter.cloud.model.data;

import java.io.Serializable;

/**
 * <p>封装Dcloud项目ActivityTemplateData类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-03-15 23:48
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
public class ActivityTemplateData implements Serializable {

    private static final long serialVersionUID = 2296796833533991163L;
    /**
     * 模板ID
     */
    private Integer value;

    /**
     * 模板编号
     */
    private String code;
    /**
     * 作者名称
     */
    private String label;

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
}
