package com.github.arisan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.github.arisan.model.ArisanFieldModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

/**
 * Created by wijaya on 4/22/2018.
 */

public class ArisanForm {
    GsonBuilder gsonBuilder = new GsonBuilder();
    Gson gson = gsonBuilder.create();
    Context context;
    Intent intent;
    List<ArisanFieldModel> fieldData;

    public ArisanForm setIntent(Context context, Class targetActivity){
        this.context = context;
        intent = new Intent(context,targetActivity);
        return this;
    }

    public ArisanForm setTitle(String title){
        intent.putExtra("title",title);
        return this;
    }

    public ArisanForm fillData(final String fieldName, String[] data){
        ArisanFieldModel foundModel = new ArisanFieldModel();
        boolean found = false;
        for(ArisanFieldModel afm : fieldData){
            if(afm.getName().equals(fieldName)){
                foundModel = afm;
                found = true;
                break;
            }
        }
        if(found) {
            fieldData.remove(foundModel);
            foundModel.setData(data);
            fieldData.add(foundModel);
        }else{
            Log.e("__Arisan Form Error","Field with name "+fieldName+" not found!!!");
        }
        intent.putExtra("fieldData",gson.toJson(fieldData));
        return this;
    }

    public ArisanForm setModel(List<ArisanFieldModel> fieldData){
        this.fieldData = fieldData;
        intent.putExtra("fieldData",gson.toJson(fieldData));
        intent.putExtra("class",fieldData.getClass().getCanonicalName());
        return this;
    }

    public void run(int REQUEST){
        ((Activity)context).startActivityForResult(intent, REQUEST);
    }
}
