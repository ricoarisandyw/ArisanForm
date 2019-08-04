package com.github.arisan.helper;

import com.github.arisan.annotation.Form;
import com.github.arisan.model.FormModel;

import java.util.List;

public class DummyCreator {
    public static String[] DUMMY_ARRAY = {"Banana","Watermelon","Mango","Orange","Apple"};

    public static List<FormModel> fillDummyArray(List<FormModel> models){
        for(FormModel model:models){
            int view_type = model.getViewType();
            /*if()
                model.setData(RadioUtils.convertToRadio(DUMMY_ARRAY));
            else */
            if(view_type==Form.CHECKBOX||view_type==Form.SPINNER||view_type==Form.AUTOCOMPLETE||view_type==Form.RADIO)
                model.setData(DUMMY_ARRAY);
        }
        return models;
    }

}
