package com.github.arisan.helper;

import android.util.Log;

import com.github.arisan.model.ArisanFieldModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ChildUtils {
    public static ArisanFieldModel valueRemover(ArisanFieldModel fieldModel){
        fieldModel.setValue(new Object());
        return fieldModel;
    }

    public static List<ArisanFieldModel> listValueRemover(List<List<ArisanFieldModel>> fieldModelList){
        List<ArisanFieldModel> blank_list = new ArrayList<>();
        for(ArisanFieldModel f : fieldModelList.get(0)){
            ArisanFieldModel new_model = f.renew();
            new_model.setValue(null);
            blank_list.add(new_model);
        }
        return blank_list;
    }
}
