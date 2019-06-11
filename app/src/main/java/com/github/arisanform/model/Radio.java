package com.github.arisanform.model;

import com.github.arisan.annotation.Form;
import com.github.arisan.helper.ObjectReader;
import com.github.arisan.model.ArisanFieldModel;

public class Radio {
    @Form(type = Form.RADIO)
    String radio;

    public static ArisanFieldModel getField(){
        return ObjectReader.getField(new Radio()).get(0);
    }
}
