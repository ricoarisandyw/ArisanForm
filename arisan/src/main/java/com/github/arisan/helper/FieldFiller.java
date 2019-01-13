package com.github.arisan.helper;

import com.github.arisan.model.ArisanFieldModel;

import java.util.List;

/**
 * Created by wijaya on 3/28/2018.
 */
public class FieldFiller {

    public static ArisanFieldModel find(List<ArisanFieldModel> list, String name){
        for(ArisanFieldModel f : list){
            if(f.getName().equals(name)){
                return f;
            }
        }
        return null;
    }

    public static void fill(List<ArisanFieldModel> list, String name, Object newData){
        for(ArisanFieldModel f : list){
            if(f.getName().equals(name)){
                f.setValue(newData);
                break;
            }
        }
    }
    public static void setError(List<ArisanFieldModel> list, String name, String error){
        for(ArisanFieldModel f : list){
            if(f.getName().equals(name)){
                f.setError_message(error);
                break;
            }
        }
    }


}
