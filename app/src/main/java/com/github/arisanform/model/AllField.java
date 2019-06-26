package com.github.arisanform.model;

import android.net.Uri;

import com.github.arisan.annotation.Form;

import java.net.URI;
import java.util.List;

public class AllField {
    @Form(required = true)
    String edit_text;

    @Form(type = Form.IMAGE)
    URI image;

    @Form(type = Form.SPINNER)
    String spinner;

    @Form(type = Form.PASSWORD)
    String password;

    @Form(type = Form.BOOLEAN)
    boolean bool;

    @Form(type = Form.SEARCH)
    String search;

    @Form(type = Form.SLIDER)
    int slider;

    @Form(type = Form.AUTOCOMPLETE)
    String autocomplete;

    @Form(type = Form.DATE)
    String date;

    @Form(type = Form.FILE)
    Uri file;

    @Form(type = Form.RADIO)
    String radio;

    @Form(type = Form.CHECKBOX)
    List<String> checkbox;

    @Form(type = Form.ONETOMANY,relation = KK.class)
    List<KK> one_to_many;

    public AllField() {
    }

    public String getEdit_text() {
        return edit_text;
    }

    public void setEdit_text(String edit_text) {
        this.edit_text = edit_text;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isBool() {
        return bool;
    }

    public void setBool(boolean bool) {
        this.bool = bool;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public int getSlider() {
        return slider;
    }

    public void setSlider(int slider) {
        this.slider = slider;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Uri getFile() {
        return file;
    }

    public void setFile(Uri file) {
        this.file = file;
    }

    public String getRadio() {
        return radio;
    }

    public void setRadio(String radio) {
        this.radio = radio;
    }

    public List<String> getCheckbox() {
        return checkbox;
    }

    public void setCheckbox(List<String> checkbox) {
        this.checkbox = checkbox;
    }

    public List<KK> getOne_to_many() {
        return one_to_many;
    }

    public void setOne_to_many(List<KK> one_to_many) {
        this.one_to_many = one_to_many;
    }
}
