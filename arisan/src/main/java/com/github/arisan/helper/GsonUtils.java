package com.github.arisan.helper;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.util.Map;

public class GsonUtils {
    public static Map<String,String> convertToMap(String json){
        return new Gson().fromJson(json,new TypeToken<Map<String,String>>(){}.getType());
    }
}
