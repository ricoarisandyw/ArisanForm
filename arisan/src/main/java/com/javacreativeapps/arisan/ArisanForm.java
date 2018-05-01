package com.javacreativeapps.arisan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.javacreativeapps.arisan.annotation.FormVar;
import com.javacreativeapps.arisan.model.ArisanField;

import java.util.List;

/**
 * Created by wijaya on 4/22/2018.
 */

public class ArisanForm {
    GsonBuilder gsonBuilder = new GsonBuilder();
    Gson gson = gsonBuilder.create();
    Context context;
    Intent intent;
    Class objectClass;
    public void intent(Context context, Class targetActivity){
        this.context = context;
        intent = new Intent(context,targetActivity);
    }

    public void setTitle(String title){
        intent.putExtra("title",title);
    }

    public void setData(List<ArisanField> data){
        intent.putExtra("data",gson.toJson(data));
        intent.putExtra("class",data.getClass().getCanonicalName());
    }

    public void run(){
        ((Activity)context).startActivityForResult(intent, FormVar.REQUEST);
    }
}
