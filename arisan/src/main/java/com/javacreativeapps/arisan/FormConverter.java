package com.javacreativeapps.arisan;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.javacreativeapps.arisan.helper.ObjectReader;
import com.javacreativeapps.arisan.model.FieldDetail;

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
        List<FieldDetail> fieldDetails = ObjectReader.getFieldName(object);
        return gson.toJson(fieldDetails);
    }

    public static List<FieldDetail> fromJson(Context context){
        Bundle bundle = ((Activity)context).getIntent().getExtras();
        String json;
        List<FieldDetail> fieldDetails = new ArrayList<>();
        if(bundle.getString("data")!= null)
        {
            json = bundle.getString("data");
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();
            Type listType = new TypeToken<List<FieldDetail>>(){

            }.getType();
            fieldDetails = gson.fromJson(json, listType);
        }

        return fieldDetails;
    }
}
