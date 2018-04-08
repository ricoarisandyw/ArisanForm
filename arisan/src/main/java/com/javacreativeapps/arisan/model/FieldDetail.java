package com.javacreativeapps.arisan.model;

import java.util.List;

/**
 * Created by wijaya on 3/26/2018.
 */
public class FieldDetail {
    String fieldType;
    String name;
    String viewType;
    Object data;
    boolean confirm;

    public FieldDetail() {
    }

    public FieldDetail(String fieldType, String name, String viewType, List<Object> data, boolean confirm) {
        this.fieldType = fieldType;
        this.name = name;
        this.viewType = viewType;
        this.data = data;
        this.confirm = confirm;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getViewType() {
        return viewType;
    }

    public void setViewType(String viewType) {
        this.viewType = viewType;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public boolean isConfirm() {
        return confirm;
    }

    public void setConfirm(boolean confirm) {
        this.confirm = confirm;
    }
}
