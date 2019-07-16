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

        path = uri.toString();
        path = path.replace("%2F","/");

        filename_with_ex = path.substring(path.lastIndexOf("/")+1);
        if (filename_with_ex.indexOf(".") > 0) {
            filename = filename_with_ex.substring(0, filename_with_ex.lastIndexOf("."));
        } else {
            filename = filename_with_ex;
        }

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
        return filename_with_ex.replace("%2f","\"");
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
