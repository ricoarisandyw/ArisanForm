package com.github.arisan.helper;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by wijaya on 3/28/2018.
 */
public class ObjectGetter {
    Object object;
    Map<String, Object> myMap;

    public ObjectGetter() {
    }

    public ObjectGetter(String json){
        Type type = new TypeToken<Map<String, Object>>(){}.getType();
        myMap = new Gson().fromJson(json, type);
    }

    ObjectGetter(Object object) {
        this.object = object;
        Type type = new TypeToken<Map<String, Object>>(){}.getType();
        String json = new Gson().toJson(this.object);
        myMap = new Gson().fromJson(json, type);
    }

    public List<Object> getList(Object object){
        Type type = new TypeToken<List<Object>>(){}.getType();
        List<Object> childs;
        if(object!=null) childs = new Gson().fromJson(new Gson().toJson(object), type);
        else {
            childs = new ArrayList<>();
            childs.add(new Object());
        }
        return childs;
    }

    public Object runGetterArray(String parent,String fieldName){
        Type type = new TypeToken<Map<String, Object>>(){}.getType();
        Map<String, Object> mapChild = new Gson().fromJson(new Gson().toJson(this.object), type);
        String childJson = new Gson().toJson(myMap.get(parent));
        Map<String, Object> child = new Gson().fromJson(childJson, type);
        return child.get(fieldName);
    }

    public Object runGetter(String fieldName)
    {
        try{
            return myMap.get(fieldName);
        }catch (Exception e){
            return null;
        }
    }
}
