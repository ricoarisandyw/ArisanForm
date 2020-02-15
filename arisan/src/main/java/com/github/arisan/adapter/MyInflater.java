package com.github.arisan.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.arisan.FormConfig;
import com.github.arisan.R;
import com.github.arisan.annotation.Form;
import com.github.arisan.model.FormModel;

public class MyInflater {
    public static View inflate(ViewGroup parent, FormModel model, FormConfig config){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v;
        switch (model.getViewType()){
            case Form.BUTTON:
                    if (config.isChild)
                        v = inflater.inflate(R.layout.item_delete, parent, false);
                    else
                        v = inflater.inflate(com.github.arisan.R.layout.item_button, parent, false);
            break;
            case Form.BOOLEAN:v = inflater.inflate(com.github.arisan.R.layout.item_switch, parent, false);break;
            case Form.DATE:v = inflater.inflate(R.layout.item_date, parent, false);break;
            case Form.DATETIME: v = inflater.inflate(R.layout.item_date, parent, false);break;
            case Form.SPINNER: v = inflater.inflate(R.layout.item_spinner, parent, false);break;
            case Form.CHECKBOX: v = inflater.inflate(R.layout.item_checkbox_parent, parent, false);break;
            case Form.TIME: v = inflater.inflate(R.layout.item_date, parent, false);break;
            case Form.FILE: v = inflater.inflate(R.layout.item_file, parent, false);break;
            case Form.SEARCH: v = inflater.inflate(R.layout.item_search, parent, false);break;
            case Form.ONETOMANY:v = inflater.inflate(R.layout.item_many, parent, false);break;
            case Form.RADIO:v = inflater.inflate(R.layout.item_radio, parent, false);break;
            case Form.SLIDER:v = inflater.inflate(R.layout.item_slide, parent, false);break;
            case Form.PASSWORD:v = inflater.inflate(R.layout.item_password, parent, false);break;
            case Form.IMAGE:v = inflater.inflate(R.layout.item_image, parent, false);break;
            case Form.AUTOCOMPLETE:v = inflater.inflate(R.layout.form_autocomplete, parent, false);break;
            case Form.ONELINETEXT:v = inflater.inflate(R.layout.item_onelinetext, parent, false);break;
            case Form.FLOWTEXT:v = inflater.inflate(R.layout.item_flow_text, parent, false);break;
            case Form.TITLE:v = inflater.inflate(R.layout.item_text, parent, false);break;
            case Form.CUSTOM:v = inflater.inflate(model.getCustomForm().getLayout(),parent,false);break;
            default:v = inflater.inflate(R.layout.item_edittext, parent, false);break;
        }
//        Log.d("_PROCESS","INFLATE "+model.getViewType());
        return v;
    }
}
