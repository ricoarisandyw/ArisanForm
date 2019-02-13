package com.github.arisan.helper;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class ValueUpdater<T> {
    public T update(T old_data,T new_data,Class<T> data_class){
        Type type = new TypeToken<Map<String, Object>>(){}.getType();
        Map<String, Object> oldModel = new Gson().fromJson(new Gson().toJson(old_data), type);
        Map<String, Object> newModel = new Gson().fromJson(new Gson().toJson(new_data), type);
        Map<String,Object> mergeModel = new HashMap<>();
        mergeModel.putAll(oldModel);
        mergeModel.putAll(newModel);
        Type type_data = new TypeToken<T>(){}.getType();
        T merge_data = new Gson().fromJson(FieldAssembler.mapToJson(mergeModel),data_class);
        return merge_data;
    }
}
