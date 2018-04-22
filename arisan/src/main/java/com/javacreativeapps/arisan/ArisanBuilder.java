package com.javacreativeapps.arisan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/**
 * Created by wijaya on 4/22/2018.
 */

public class ArisanIntent {
    public void init(Context context, Class c){
        Intent intent = new Intent(context,c);
        ((Activity)context).startActivity(intent);
    }
}
