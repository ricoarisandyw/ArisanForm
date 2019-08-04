package com.github.arisan.helper;

import com.github.arisan.model.FormModel;
import com.github.arisan.model.FormModel;
import com.github.arisan.model.RadioModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class FieldUtils {
    public static List<String> countBlank(List<FormModel> models){
        List<String> blank_field = new ArrayList<>();
        for(FormModel model:models){
            if(model.getValue()==null && model.isRequire()){
                blank_field.add(model.getLabel());
            }else if(model.getChildFieldModel()!=null){
                for(List<FormModel> childList:model.getChildFieldModel()){
                    for(FormModel child:childList){
                        if(child.getValue()==null && child.isRequire()){
                            blank_field.add(child.getLabel()+"["+model.getChildFieldModel().indexOf(childList)+"]");
                        }
                    }
                }
            }
        }
        return blank_field;
    }

    public static List<String> countFormBlank(List<FormModel> models){
        List<String> blank_field = new ArrayList<>();
        for(FormModel model:models){
            if(model.getValue()==null && model.isRequire()){
                blank_field.add(model.getLabel());
            }else if(model.getChildFieldModel()!=null){
                for(List<FormModel> childList:model.getChildFieldModel()){
                    for(FormModel child:childList){
                        if(child.getValue()==null && child.isRequire()){
                            blank_field.add(child.getLabel()+"["+model.getChildFieldModel().indexOf(childList)+"]");
                        }
                    }
                }
            }
        }
        return blank_field;
    }

    public static List<FormModel> removeField(String fieldName, List<FormModel> list){
        for(FormModel model:list){
            if (model.getName().equals(fieldName)){
                list.remove(model);
                break;
            }
        }
        return list;
    }

    public static List<FormModel> insertOrUpdateField(FormModel new_model, List<FormModel> list){
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
        List<String> list = gson.fromJson(json,new TypeToken<List<String>>(){}.getType());
        List<RadioModel> radioModels = new ArrayList<>();
        for(String s : list){
            RadioModel rm = new RadioModel();
            rm.setId(list.indexOf(s));
            rm.setValue(s);
            radioModels.add(rm);
        }
        return radioModels;
    }
}
