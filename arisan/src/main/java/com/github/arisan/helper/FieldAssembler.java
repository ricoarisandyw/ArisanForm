package com.github.arisan.helper;

import android.util.Log;

import com.github.arisan.model.ArisanFieldModel;
import com.github.arisan.model.ArisanFieldModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wijaya on 4/22/2018.
 */

public class FieldAssembler {
    public static String toJson(List<ArisanFieldModel> data){
        StringBuilder result = new StringBuilder("{");
        Gson gson = new Gson();
        for(int i = 0;i<data.size();i++){
            ArisanFieldModel model = data.get(i);
            if(model.getValue()!=null){
                result.append("\"").append(model.getName()).append("\":");
                result.append(gson.toJson(model.getValue()));
                if(i<data.size()-1){
                    result.append(",");
                }
            }
        }
        result.append("}");
        Log.d("__Result",result.toString());
        return result.toString();
    }

    public static String mapToJson(Map<String,Object> data){
        StringBuilder result = new StringBuilder("{");
        Gson gson = new Gson();
        int i = 0;
        for(Map.Entry<String, Object> entry: data.entrySet()){
            String key = entry.getKey();
            Object value = entry.getValue();
            if(entry.getValue()!=null){
                result.append("\"").append(key).append("\":");
                result.append(gson.toJson(value));
                if(i<data.size()-1){
                    result.append(",");
                }
            }
            i++;
        }
        result.append("}");
        Log.d("__Result map",result.toString());
        return result.toString();
    }
}