package com.github.arisan.helper;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;


public class UriUtils {
    Context context;
    String path;
    String filename_with_ex;
    String filename;
    Uri uri;

    public UriUtils(Context context,Uri uri) {
        this.uri = uri;
        this.context = context;

        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(context, uri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        path = cursor.getString(column_index);
        cursor.close();

        filename_with_ex = path.substring(path.lastIndexOf("/")+1);
        if (filename_with_ex.indexOf(".") > 0) {
            filename = filename_with_ex.substring(0, filename_with_ex.lastIndexOf("."));
        } else {
            filename = filename_with_ex;
        }
//        String TAG = "__URI";
//        Log.d(TAG, "Real Path: " + path);
//        Log.d(TAG, "Filename With Extension: " + filename_with_ex);
//        Log.d(TAG, "File Without Extension: " + filename);
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public String getRealPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFilename_with_ex() {
        return filename_with_ex;
    }

    public void setFilename_with_ex(String filename_with_ex) {
        this.filename_with_ex = filename_with_ex;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
