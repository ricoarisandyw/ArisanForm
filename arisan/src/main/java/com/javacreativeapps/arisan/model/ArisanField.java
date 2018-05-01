package com.javacreativeapps.arisan.model;

import java.util.List;

/**
 * Created by wijaya on 3/26/2018.
 */
public class ArisanField {
    String fieldType;
    String name;
    int viewType;
    Object data;
    Object value;
    String error_message;
    boolean confirm;

    public ArisanField() {
    }

    public ArisanField(String fieldType, String name, int viewType, Object data, Object value, String error_message, boolean confirm) {
        this.fieldType = fieldType;
        this.name = name;
        this.viewType = viewType;
        this.data = data;
        this.value = value;
        this.error_message = error_message;
        this.confirm = confirm;
    }

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
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

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
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
