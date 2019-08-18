package com.github.arisan.helper;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Logger {
    static Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    public static void d(Object object){
        Log.d("__ARISAN LOGGER",gson.toJson(object));
    }

    public static void e(Object object){
        Log.e("__ARISAN LOGGER",gson.toJson(object));
    }

    public static void s(Object... objects) {
        StringBuilder builder = new StringBuilder();
        for(Object o : objects){
            builder.append(gson.toJson(o));
        }
        e(builder.toString().replace("\n",""));
    }
}
