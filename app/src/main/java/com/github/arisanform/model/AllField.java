package com.github.arisanform.model;

import android.net.Uri;

import com.github.arisan.annotation.Form;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class AllField extends RealmObject {

    @PrimaryKey
    int id;

    @Form(required = true)
    String edit_text;

    @Form(type = Form.IMAGE)
    String image;

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

    @Form(type = Form.DATE,format = "yyyy-MM-dd")
    String date;

    @Form(type = Form.FILE)
    String file;

    @Form(type = Form.RADIO)
    String radio;

    @Form(type = Form.CHECKBOX)
    RealmList<String> checkbox;

    @Form(type = Form.ONETOMANY,relation = KK.class)
    RealmList<KK> one_to_many;

    @Form(type = Form.ONELINETEXT)
    String oneline_text;

    @Form(type = Form.TEXT2)
    String text2;

    public AllField() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getRadio() {
        return radio;
    }

    public void setRadio(String radio) {
        this.radio = radio;
    }

    public RealmList<String> getCheckbox() {
        return checkbox;
    }

    public void setCheckbox(RealmList<String> checkbox) {
        this.checkbox = checkbox;
    }

    public void setOne_to_many(RealmList<KK> one_to_many) {
        this.one_to_many = one_to_many;
    }

    public RealmList<KK> getOne_to_many() {
        return one_to_many;
    }



    public String getSpinner() {
        return spinner;
    }

    public void setSpinner(String spinner) {
        this.spinner = spinner;
    }

    public String getAutocomplete() {
        return autocomplete;
    }

    public void setAutocomplete(String autocomplete) {
        this.autocomplete = autocomplete;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getOneline_text() {
        return oneline_text;
    }

    public void setOneline_text(String oneline_text) {
        this.oneline_text = oneline_text;
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }
}
