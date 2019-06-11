package com.github.arisan.helper;

import com.github.arisan.model.ArisanFieldModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class FieldUtils {
    public static List<ArisanFieldModel> removeField(String fieldName, List<ArisanFieldModel> list){
        for(ArisanFieldModel model:list){
            if (model.getName().equals(fieldName)){
                list.remove(model);
                break;
            }
        }
        return list;
    }

    public static List<String> convertArrayToList(Object data){
        Gson gson = new Gson();
        String json = gson.toJson(data);
        return gson.fromJson(json,new TypeToken<List<String>>(){}.getType());
    }
}
