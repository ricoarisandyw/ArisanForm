package com.github.arisan.helper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ObjectUtils {

    public static Object renewObject(Object object,Class cls){
        Gson gson = new Gson();
        String json = gson.toJson(object);
        Object t = gson.fromJson(json,cls);
        return t;
    }

    public static List<Object> renewList(List<Object> list,Class cls){
        List<Object> new_list = new ArrayList<>();
        for(Object t:list){
            new_list.add(renewObject(t,cls));
        }
        return new_list;
    }
}
