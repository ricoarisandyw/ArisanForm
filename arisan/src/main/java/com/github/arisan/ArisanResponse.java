package com.github.arisan;

import android.net.Uri;

import java.util.Map;

public class ArisanResponse {
    String response;
    Map<String, Uri> files;

    public ArisanResponse() {
    }

    public ArisanResponse(String response, Map<String, Uri> files) {
        this.response = response;
        this.files = files;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Map<String, Uri> getFiles() {
        return files;
    }

    public void setFiles(Map<String, Uri> files) {
        this.files = files;
    }
}
