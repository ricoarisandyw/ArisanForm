package com.javacreativeapps.arisan;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.javacreativeapps.arisan.helper.ObjectReader;
import com.javacreativeapps.arisan.model.ArisanField;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wijaya on 4/5/2018.
 */

public class FormConverter {

    public static String toJson(Object object){
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        List<ArisanField> arisanFields = ObjectReader.getField(object);
        return gson.toJson(arisanFields);
    }

    public static List<ArisanField> fromJson(Context context){
        Bundle bundle = ((Activity)context).getIntent().getExtras();
        String json;
        List<ArisanField> arisanFields = new ArrayList<>();
        if(bundle.getString("data")!= null)
        {
            json = bundle.getString("data");
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();
            Type listType = new TypeToken<List<ArisanField>>(){

            }.getType();
            arisanFields = gson.fromJson(json, listType);
        }

        return arisanFields;
    }
}
