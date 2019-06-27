package com.github.arisan.helper;

import android.app.Activity;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.ArrayList;
import java.util.List;

public class PermissionUtils {

    public static void askPermission(final Activity activity, String... PERMISSIONS){
        Dexter.withActivity(activity).withPermissions(
                PERMISSIONS
        ).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                Toast.makeText(activity, "Permission complete", Toast.LENGTH_SHORT).show();
                Logger.d("REPORT");
                Logger.d(report);
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                String[] unaccepted_permissions = new String[permissions.size()];
                for(PermissionRequest permissionRequest:permissions){
                    unaccepted_permissions[permissions.indexOf(permissionRequest)]=permissionRequest.getName();
                }

                //SHOW PERMISSION DIALOG
                ActivityCompat.requestPermissions(activity,unaccepted_permissions, 200);
                Logger.d("RATIONAL PERMISSION");
                Logger.d(permissions);
                askPermission(activity,unaccepted_permissions);
            }
        }).check();
    }



    public static void checkPermission(Activity activity,Do doing,String... permission){

    }

    public interface Do{
        void doSomething();
    }


}
