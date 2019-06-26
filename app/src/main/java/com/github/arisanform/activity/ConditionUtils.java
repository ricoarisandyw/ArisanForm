package com.github.arisanform.activity;

import com.github.arisan.helper.FieldUtils;
import com.github.arisan.model.ArisanFieldModel;

import java.util.List;

public class ConditionUtils {
    public static void whenShow(boolean correct, List<ArisanFieldModel> existing, ArisanFieldModel... model){
        if(correct){
            for(ArisanFieldModel m:model){
                FieldUtils.INSTANCE.insertOrUpdateField(m,existing);
            }
        }else{
            for(ArisanFieldModel m:model){
                FieldUtils.INSTANCE.removeField(m.getName(),existing);
            }
        }
    }
}
