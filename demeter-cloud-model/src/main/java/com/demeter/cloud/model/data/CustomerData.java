package com.demeter.cloud.model.data;

import java.io.Serializable;

/**
 * <p>封装Dcloud项目CustomerData类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-03-15 20:24
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
public class CustomerData implements Serializable {
    private static final long serialVersionUID = -2960314400653102805L;
    /**
     *作者ID
     */
    private Integer value;
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
}
