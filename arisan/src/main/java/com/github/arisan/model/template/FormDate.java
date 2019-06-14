package com.github.arisan.model.template;

import com.github.arisan.annotation.Form;
import com.github.arisan.helper.ObjectReader;
import com.github.arisan.model.ArisanFieldModel;

import java.util.Date;

public class FormDate {
    @Form(type = Form.DATE)
    Date field_name;

    public FormDate() {
    }

    public Date getField_name() {
        return field_name;
    }

    public void setField_name(Date field_name) {
        this.field_name = field_name;
    }

    public static ArisanFieldModel getModel(){
        return ObjectReader.getField(new FormDate()).get(0);
    }
}
