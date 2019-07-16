package com.github.arisan.helper;

import com.github.arisan.model.ArisanFieldModel;
import com.github.arisan.model.RadioModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class FieldUtils {
    public static List<String> countBlank(List<ArisanFieldModel> models){
        List<String> blank_field = new ArrayList<>();
        for(ArisanFieldModel model:models){
            if(model.getValue()==null && model.isRequire()){
                blank_field.add(model.getLabel());
            }else if(model.getChildFieldModel()!=null){
                for(List<ArisanFieldModel> childList:model.getChildFieldModel()){
                    for(ArisanFieldModel child:childList){
                        if(child.getValue()==null && child.isRequire()){
                            blank_field.add(child.getLabel()+"["+model.getChildFieldModel().indexOf(childList)+"]");
                        }
                    }
                }
            }
        }
        return blank_field;
    }

    public static List<ArisanFieldModel> removeField(String fieldName, List<ArisanFieldModel> list){
        for(ArisanFieldModel model:list){
            if (model.getName().equals(fieldName)){
                list.remove(model);
                break;
            }
        }
        return list;
    }

    public static List<ArisanFieldModel> insertOrUpdateField(ArisanFieldModel new_model, List<ArisanFieldModel> list){
        removeField(new_model.getName(),list);
        list.add(new_model);
        return list;
    }

    public static List<String> convertArrayToList(Object data){
        Gson gson = new Gson();
        String json = gson.toJson(data);
        return gson.fromJson(json,new TypeToken<List<String>>(){}.getType());
    }

    public static Object[] convertListToArray(Object data){
        Gson gson = new Gson();
        String json = gson.toJson(data);
        return gson.fromJson(json,Object[].class);
    }

    public static List<RadioModel> convertDataToRadio(Object data){
        Gson gson = new Gson();
        String json = gson.toJson(data);
        return gson.fromJson(json,new TypeToken<List<RadioModel>>(){}.getType());
    }
}
