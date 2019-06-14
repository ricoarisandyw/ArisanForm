package com.github.arisan.model.template;

import com.github.arisan.annotation.Form;
import com.github.arisan.helper.ObjectReader;
import com.github.arisan.model.ArisanFieldModel;

public class FormSpinner {
    @Form(type = Form.SPINNER)
    String field_name;

    public FormSpinner() {
    }

    public String getField_name() {
        return field_name;
    }

    public void setField_name(String field_name) {
        this.field_name = field_name;
    }

    public static ArisanFieldModel getModel(){
        return ObjectReader.getField(new FormSpinner()).get(0);
    }
}
