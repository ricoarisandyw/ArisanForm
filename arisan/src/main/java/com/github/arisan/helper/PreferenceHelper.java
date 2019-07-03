package com.github.arisan.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * Created by wijaya on 3/25/2018.
 */

public class PreferenceHelper {
    private Context context;
    private String MY_DB = "ARISAN_FORM";

    public PreferenceHelper(Context context){
        this.context = context;
    }

    public void save(String key, String value){
        SharedPreferences sharedPref = context.getSharedPreferences(MY_DB, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void saveObj(String key, Object object){
        SharedPreferences sharedPref = context.getSharedPreferences(MY_DB, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        Gson gson = new Gson();
        String value = gson.toJson(object);
        editor.putString(key, value);
        editor.apply();
    }

    public List<Object> loadObjList(String key, Type type){
        SharedPreferences sharedPref = context.getSharedPreferences(MY_DB, Context.MODE_PRIVATE);
        String result = sharedPref.getString(key, "");
        Gson gson = new Gson();
        return gson.fromJson(result, type);
    }

    public Map<Object,Object> loadMap(String key, Type type){
        SharedPreferences sharedPref = context.getSharedPreferences(MY_DB, Context.MODE_PRIVATE);
        String result = sharedPref.getString(key, "");
        Gson gson = new Gson();
        return gson.fromJson(result, type);
    }

    public Object loadObj(String key, Class c){
        SharedPreferences sharedPref = context.getSharedPreferences(MY_DB, Context.MODE_PRIVATE);
        String result = sharedPref.getString(key, "");
        Gson gson = new Gson();
        return gson.fromJson(result,c);
    }

    public String load(String key){
        SharedPreferences sharedPref = context.getSharedPreferences(MY_DB, Context.MODE_PRIVATE);
        return sharedPref.getString(key, "");
    }
}