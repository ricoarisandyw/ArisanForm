package com.github.arisanform.model;

import com.github.arisan.annotation.Form;

import io.realm.RealmObject;

public class ChildModel extends RealmObject {

    @Form(type = Form.IMAGE,required = true)
    private String image;

    @Form(type = Form.IMAGE)
    private String image2;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }
}
