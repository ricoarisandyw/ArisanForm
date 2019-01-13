package com.github.arisan.model;

/**
 * Created by wijaya on 3/26/2018.
 */
public class ArisanFieldModel {
    String fieldType;
    String label;
    String name;
    int viewType;
    int position;
    Object data;
    Object value;
    String error_message;
    boolean require;

    public ArisanFieldModel() {
    }

    public ArisanFieldModel(String fieldType, String name, int viewType, int position, Object data, Object value, String error_message, boolean require) {
        this.fieldType = fieldType;
        this.name = name;
        this.viewType = viewType;
        this.position = position;
        this.data = data;
        this.value = value;
        this.error_message = error_message;
        this.require = require;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
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

    public boolean isRequire() {
        return require;
    }

    public void setRequire(boolean require) {
        this.require = require;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
