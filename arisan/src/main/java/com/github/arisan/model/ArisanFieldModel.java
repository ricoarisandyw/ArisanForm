package com.github.arisan.model;

import android.view.View;
import android.widget.TextView;

import com.github.arisan.ArisanListener;

import java.util.List;

/**
 * Created by wijaya on 3/26/2018.
 */
public class ArisanFieldModel {
    private String fieldType;
    private String label;
    private String name;
    private String viewType;
    private int position;
    private Object data;
    private Object value;
    private String error_message;
    private String dateFormat;
    private String fileType;
    private boolean require;
    private int background;
    private int submit_color;
    private int color;
    private List<List<ArisanFieldModel>> childFieldModel;
    private ArisanListener.Condition arisanListener;
    private ArisanListener.CheckboxCondition checkboxListener;
    private ArisanListener.ViewMod viewMod;

    public ArisanListener.Condition getArisanListener() {
        return arisanListener;
    }

    public void setArisanListener(ArisanListener.Condition arisanListener) {
        this.arisanListener = arisanListener;
    }

    public ArisanListener.CheckboxCondition getCheckboxListener() {
        return checkboxListener;
    }

    public ListenerModel doCheckboxListener(String value,List<String> data){
        return this.checkboxListener.onChecked(value,data);
    }

    public void setCheckboxListener(ArisanListener.CheckboxCondition checkboxListener) {
        this.checkboxListener = checkboxListener;
    }

    public ArisanListener.ViewMod getViewMod() {
        return viewMod;
    }

    public void setViewMod(ArisanListener.ViewMod viewMod) {
        this.viewMod = viewMod;
    }

    public List<List<ArisanFieldModel>> getChildFieldModel() {
        return childFieldModel;
    }

    public int getSubmit_color() {
        return submit_color;
    }

    public void setSubmit_color(int submit_color) {
        this.submit_color = submit_color;
    }

    public void setChildFieldModel(List<List<ArisanFieldModel>> childFieldModel) {
        this.childFieldModel = childFieldModel;
    }

    public void addCondition(ArisanListener.Condition arisanListener) {
        this.arisanListener = arisanListener;
    }

    public void addViewMod(ArisanListener.ViewMod viewMod) {
        this.viewMod = viewMod;
    }

    public Object doViewMod(Object view) {
        return viewMod.modding(view);
    }

    public ListenerModel doListener(String value){
        return arisanListener.onValue(value);
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public ArisanFieldModel() {
    }

    public int getBackground() {
        return background;
    }

    public void setBackground(int background) {
        this.background = background;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
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

    public ArisanFieldModel renew(){
        ArisanFieldModel new_model = new ArisanFieldModel();
        new_model.setValue(value);
        new_model.setChildFieldModel(childFieldModel);
        new_model.setBackground(background);
        new_model.setColor(color);
        new_model.setData(data);
        new_model.setDateFormat(dateFormat);
        new_model.setName(name);
        new_model.setLabel(label);
        new_model.setFieldType(fieldType);
        new_model.setViewType(viewType);
        new_model.setPosition(position);
        new_model.setError_message(error_message);
        new_model.setFileType(fileType);
        new_model.setRequire(require);
        new_model.addCondition(arisanListener);
        new_model.setCheckboxListener(checkboxListener);
        new_model.addViewMod(viewMod);

        return new_model;
    }
}
