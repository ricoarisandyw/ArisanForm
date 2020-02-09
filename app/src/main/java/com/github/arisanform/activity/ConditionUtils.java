package com.github.arisanform.activity;

import com.github.arisan.helper.FieldUtils;
import com.github.arisan.model.FormModel;

import java.util.List;

public class ConditionUtils {
    public static void whenShow(boolean correct, List<FormModel> existing, FormModel... model){
        if(correct){
            for(FormModel m:model){
                FieldUtils.insertOrUpdateField(m,existing);
            }
        }else{
            for(FormModel m:model){
                FieldUtils.removeField(m.getName(),existing);
            }
        }
    }
}
