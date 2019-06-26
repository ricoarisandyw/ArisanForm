package com.github.arisan.helper;

import android.util.Log;

import com.google.gson.Gson;

public class Logger {
    public static void d(Object object){
        Log.d("__ARISAN LOGGER",new Gson().toJson(object));
    }

    public static void f(Object object){
        Log.d("__ARISAN F",new Gson().toJson(object));
    }
}
