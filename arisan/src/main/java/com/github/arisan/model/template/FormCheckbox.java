package com.github.arisan.model.template;

import com.github.arisan.annotation.Form;
import com.github.arisan.helper.ObjectReader;
import com.github.arisan.model.ArisanFieldModel;

import java.util.List;

public class FormCheckbox {
    @Form(type = Form.CHECKBOX)
    List<String> field_name;

    public FormCheckbox() {
    }

    public List<String> getField_name() {
        return field_name;
    }

    public void setField_name(List<String> field_name) {
        this.field_name = field_name;
    }

    public static ArisanFieldModel getModel(){
        return ObjectReader.getField(new FormCheckbox()).get(0);
    }
}
