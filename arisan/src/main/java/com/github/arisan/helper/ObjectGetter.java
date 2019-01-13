package com.github.arisan.helper;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by wijaya on 3/28/2018.
 */
public class ObjectGetter {
    Object object;

    ObjectGetter(Object object) {
        this.object = object;
    }

    public Object runGetter(String fieldName)
    {
        Type type = new TypeToken<Map<String, Object>>(){}.getType();
        Map<String, Object> myMap = new Gson().fromJson(new Gson().toJson(this.object), type);
        try{
            return myMap.get(fieldName);
        }catch (Exception e){
            return null;
        }
    }
}
