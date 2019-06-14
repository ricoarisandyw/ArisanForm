package com.github.arisan.model.template;

import com.github.arisan.annotation.Form;
import com.github.arisan.helper.ObjectReader;
import com.github.arisan.model.ArisanFieldModel;

public class FormNumber {
    @Form(type = Form.NUMBER)
    int field_name;

    public FormNumber() {
    }

    public int getField_name() {
        return field_name;
    }

    public void setField_name(int field_name) {
        this.field_name = field_name;
    }

    public static ArisanFieldModel getModel(){
        return ObjectReader.getField(new FormNumber()).get(0);
    }
}
