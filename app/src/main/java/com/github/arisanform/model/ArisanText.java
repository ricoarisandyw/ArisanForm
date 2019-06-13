package com.github.arisanform.model;

import com.github.arisan.annotation.Form;
import com.github.arisan.helper.ObjectReader;
import com.github.arisan.model.ArisanFieldModel;

public class ArisanText {
    @Form(type = Form.TEXT)
    String radio;

    public static ArisanFieldModel getField(){
        return ObjectReader.getField(new ArisanText()).get(0);
    }

    public ArisanText() {
    }

    public String getRadio() {
        return radio;
    }

    public void setRadio(String radio) {
        this.radio = radio;
    }
}
