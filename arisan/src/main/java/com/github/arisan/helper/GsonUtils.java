package com.github.arisan.helper;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Map;

public class GsonUtils {
    public static Map<String,String> convertToMap(String json){
        Gson gson = new Gson();
        Map<String,Object> new_obj = gson.fromJson(json,new TypeToken<Map<String,Object>>(){}.getType());
        Map<String,String> map = new HashMap<>();
        for(String key:new_obj.keySet()){
            map.put(key,gson.toJson(new_obj.get(key)));
        }

        return map;
    }

    public static Map<String,String> convertToMap(Object object){
        Gson gson = new Gson();
        String json = gson.toJson(object);
        Map<String,Object> new_obj = gson.fromJson(json,new TypeToken<Map<String,Object>>(){}.getType());
        Map<String,String> map = new HashMap<>();
        for(String key:new_obj.keySet()){
            map.put(key,gson.toJson(new_obj.get(key)));
        }

        return map;
    }
}
