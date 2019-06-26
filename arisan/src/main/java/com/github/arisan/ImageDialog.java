package com.github.arisan;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;

public class ImageDialog extends DialogFragment {
    public static void build(Activity activity, final OnImageDialog onImageDialog){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("Pick Mode");
        builder.setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onImageDialog.selected(true);
            }
        });
        builder.setNegativeButton("Camera", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onImageDialog.selected(false);
            }
        });
        builder.create().show();
    }

    public interface OnImageDialog{
        void selected(boolean gallery);
    }
}
