package com.github.arisan;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.github.arisan.helper.ObjectReader;
import com.github.arisan.model.ArisanFieldModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.github.arisan.helper.ObjectReader;
import com.github.arisan.model.ArisanFieldModel;

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
        List<ArisanFieldModel> arisanFields = ObjectReader.getField(object);
        return gson.toJson(arisanFields);
    }

    public static List<ArisanFieldModel> fromJson(Context context){
        Bundle bundle = ((Activity)context).getIntent().getExtras();
        String json;
        List<ArisanFieldModel> arisanFields = new ArrayList<>();
        if(bundle.getString("fieldData")!= null)
        {
            json = bundle.getString("fieldData");
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();
            Type listType = new TypeToken<List<ArisanFieldModel>>(){

            }.getType();
            arisanFields = gson.fromJson(json, listType);
        }

        return arisanFields;
    }
}
