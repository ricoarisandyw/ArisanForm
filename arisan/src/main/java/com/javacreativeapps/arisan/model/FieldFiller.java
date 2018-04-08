package com.javacreativeapps.arisan.model;

import java.util.List;

/**
 * Created by wijaya on 3/28/2018.
 */
public class FieldFiller {

    public static FieldDetail find(List<FieldDetail> list, String name){
        for(FieldDetail f : list){
            if(f.getName().equals(name)){
                return f;
            }
        }
        return null;
    }

    public static void fill(List<FieldDetail> list, String name, Object newData){
        for(FieldDetail f : list){
            if(f.getName().equals(name)){
                f.setData(newData);
                break;
            }
        }
    }


}
