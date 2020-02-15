package com.github.arisan;

import com.github.arisan.model.ArisanCustomForm;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class FormConfig {
    public boolean useTitle = true;
    public boolean useSubmit = true;
    public int buttonBackground = 0;
    public int labelColor = 0;
    public int textColor = 0;
    public int buttonTextColor = 0;
    public String submitText = "SUBMIT";
    public String title = "TITLE";
    public String blankMessage;
    public boolean isChild = false;
    public int index_child = -1;
    public List<ArisanCustomForm> customForms = new ArrayList<>();

    public FormConfig renew(){
        String json = new Gson().toJson(this);
        FormConfig config = new Gson().fromJson(json,FormConfig.class);
        return config;
    }
}
