package com.github.arisan.helper;

import android.content.Context;
import android.net.Uri;


public class UriUtils {
    Context context;
    String file_path;
    String filename_with_ex;
    String filename;
    Uri uri;

    public UriUtils(Context context,Uri uri) {
        this.uri = uri;
        this.context = context;

        file_path = uri.toString();
        file_path = file_path.replace("%2F","/");

        filename_with_ex = file_path.substring(file_path.lastIndexOf("/")+1);
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
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
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
