package com.github.arisan.helper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ObjectUtils {

    public Object renewObject(Object object){
        Gson gson = new Gson();
        String json = gson.toJson(object);
        Object t = gson.fromJson(json,typeBuilder());
        return t;
    }

    public List<Object> renewList(List<Object> list){
        List<Object> new_list = new ArrayList<>();
        for(Object t:list){
            new_list.add(renewObject(t));
        }
        return new_list;
    }

    public Type typeBuilder(){
        return new TypeToken<Object>(){}.getType();
    }
}
