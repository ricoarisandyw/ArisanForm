package com.github.arisan.helper;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

public class PermissionUtils {

    public static void askPermission(Activity activity, String... permission){
        ActivityCompat.requestPermissions(activity,
                permission,
                200);
    }



    public static void checkPermission(Activity activity,Do doing,String... permission){

    }

    public interface Do{
        void doSomething();
    }


}
