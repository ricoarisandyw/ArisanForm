package com.github.arisanform.helper;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

/**
 * Created by wijaya on 3/25/2018.
 */

public class PreferenceHelper {
    public Context mContext;
    static SharedPreferences sharedPref;
    GsonBuilder builder = new GsonBuilder();
    Gson gson = builder.create();


    public PreferenceHelper(Context context){
        this.mContext = context;
        sharedPref = ((Activity)mContext).getSharedPreferences("preference", Context.MODE_PRIVATE);
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public Object load(String key,Type type){
        String response = sharedPref.getString(key, null);
        return gson.fromJson(response,type);
    }

    public void save(String key,Object value){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key,gson.toJson(value));
        editor.apply();
    }

    public void remove(String key){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove(key);
    }

    public void update(String key,Object value){
        remove(key);
        save(key,value);
    }
}
