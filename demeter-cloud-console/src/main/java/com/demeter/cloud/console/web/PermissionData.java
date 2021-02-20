package com.demeter.cloud.console.web;

import java.util.List;

/**
 * <p>封装Dcloud项目PermissionData类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-17 16:03
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
public class PermissionData {
    private String id;
    private String label;
    private String api;
    private List<PermissionData> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public String getApi() {
        return api;
    }

    public List<PermissionData> getChildren() {
        return children;
    }

    public void setChildren(List<PermissionData> children) {
        this.children = children;
    }
}
