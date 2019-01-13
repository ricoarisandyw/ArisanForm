package com.github.arisan;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.github.arisan.model.ArisanFieldModel;
import com.github.arisan.model.ArisanFieldModel;

import java.util.Collections;
import java.util.List;

/**
 * Created by wijaya on 4/22/2018.
 */

public class ArisanGetter {
    public static List<ArisanFieldModel> getData(Context context){
        Bundle bundle = ((Activity)context).getIntent().getExtras();
        if(bundle.getString("fieldData")!= null)
        {
            return FormConverter.fromJson(context);
        }
        return Collections.emptyList();
    }

    public static String getTitle(Context context){
        Bundle bundle = ((Activity)context).getIntent().getExtras();
        if(bundle.getString("title")!= null)
        {
            return bundle.getString("title");
        }
        return "Untitled";
    }
}
