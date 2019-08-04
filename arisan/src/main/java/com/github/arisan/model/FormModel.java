package com.github.arisan.model;

import android.graphics.Bitmap;

import com.github.arisan.ArisanListener;
import com.github.arisan.adapter.ArisanAdapter;

import java.util.List;

/**
 * Created by wijaya on 3/26/2018.
 */
public class FormModel {
    private String fieldType;
    private String label;
    private String name;
    private int viewType;
    private int position;
    private Object data;
    private Object value;
    private String error_message;
    private String dateFormat;
    private String fileType;
    private boolean require;
    //Config
    private int background;
    private int submit_color;
    private int color;

    private List<List<FormModel>> childFieldModel;
    private ArisanListener.OnCondition arisanListener;
    private ArisanListener.CheckboxCondition checkboxListener;
    private ArisanListener.ViewMod viewMod;

    Bitmap thumbnail;

    public ArisanListener.OnCondition getArisanListener() {
        return arisanListener;
    }

    public void setArisanListener(ArisanListener.OnCondition arisanListener) {
        this.arisanListener = arisanListener;
    }

    public ArisanListener.CheckboxCondition getCheckboxListener() {
        return checkboxListener;
    }

    public ListenerModel doCheckboxListener(String value,List<String> data,ArisanAdapter adapter){
        return this.checkboxListener.onChecked(value,data,adapter);
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

    public List<List<FormModel>> getChildFieldModel() {
        return childFieldModel;
    }

    public int getSubmit_color() {
        return submit_color;
    }

    public void setSubmit_color(int submit_color) {
        this.submit_color = submit_color;
    }

    public void setChildFieldModel(List<List<FormModel>> childFieldModel) {
        this.childFieldModel = childFieldModel;
    }

    public void addCondition(ArisanListener.OnCondition arisanListener) {
        this.arisanListener = arisanListener;
    }

    public void addViewMod(ArisanListener.ViewMod viewMod) {
        this.viewMod = viewMod;
    }

    public Object doViewMod(Object view) {
        return viewMod.modding(view);
    }

    public ListenerModel doListener(String value, ArisanAdapter adapter){
        return arisanListener.onValue(value);
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

    public FormModel() {
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

    public String getFieldType() { return fieldType; }

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

    public Bitmap getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Bitmap thumbnail) {
        this.thumbnail = thumbnail;
    }

    public FormModel renew(){
        FormModel new_model = new FormModel();
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
        new_model.setThumbnail(thumbnail);

        return new_model;
    }
}
