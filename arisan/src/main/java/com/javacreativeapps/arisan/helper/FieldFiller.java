package com.javacreativeapps.arisan.helper;

import com.javacreativeapps.arisan.model.ArisanField;

import java.util.List;

/**
 * Created by wijaya on 3/28/2018.
 */
public class FieldFiller {

    public static ArisanField find(List<ArisanField> list, String name){
        for(ArisanField f : list){
            if(f.getName().equals(name)){
                return f;
            }
        }
        return null;
    }

    public static void fill(List<ArisanField> list, String name, Object newData){
        for(ArisanField f : list){
            if(f.getName().equals(name)){
                f.setValue(newData);
                break;
            }
        }
    }
    public static void setError(List<ArisanField> list, String name, String error){
        for(ArisanField f : list){
            if(f.getName().equals(name)){
                f.setError_message(error);
                break;
            }
        }
    }


}
