package com.github.arisan.helper;

import android.util.Log;

import com.github.arisan.annotation.Form;
import com.github.arisan.model.FormModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ChildUtils {
    public static FormModel valueRemover(FormModel fieldModel){
        fieldModel.setValue(new Object());
        return fieldModel;
    }

    public static List<FormModel> listValueRemover(List<List<FormModel>> fieldModelList){
        List<FormModel> blank_list = new ArrayList<>();
        for(FormModel f : fieldModelList.get(0)){
            FormModel new_model = f.renew();

            new_model.setValue(null);
            new_model.setThumbnail(null);
            if(f.getViewType()==Form.IMAGE) new_model.setData(null);

            blank_list.add(new_model);
        }
        return blank_list;
    }
    /*

    

    public static List<FormModel> removeValue(List<List<FormModel>> fieldModelList){
        List<FormModel> blank_list = new ArrayList<>();
        for(FormModel f : fieldModelList.get(0)){
            FormModel new_model = f.renew();

            new_model.setValue(null);
            new_model.setThumbnail(null);
            if(f.getViewType()==Form.IMAGE) new_model.setData(null);

            blank_list.add(new_model);
        }
        return blank_list;
    }*/
}
