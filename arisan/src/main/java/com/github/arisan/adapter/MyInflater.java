package com.github.arisan.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.arisan.R;
import com.github.arisan.annotation.Form;
import com.github.arisan.annotation.Model;

import static com.github.arisan.annotation.Form.BOOLEAN;

public class MyInflater {
    public static View inflate(ViewGroup parent, int viewType,boolean isChild){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v;
        if(viewType==0){
            v = inflater.inflate(com.github.arisan.R.layout.item_text, parent, false);
        }else if(viewType== Model.BUTTON){
            if(isChild)
                v = inflater.inflate(R.layout.item_delete, parent, false);
            else
                v = inflater.inflate(com.github.arisan.R.layout.item_button, parent, false);
        }else if(viewType==BOOLEAN){
            v = inflater.inflate(com.github.arisan.R.layout.item_switch, parent, false);
        }else if(viewType==Form.DATE){
            v = inflater.inflate(R.layout.item_date, parent, false);
        }else if(viewType==Form.DATETIME){
            v = inflater.inflate(R.layout.item_date, parent, false);
        }else if(viewType== Form.SPINNER){
            v = inflater.inflate(R.layout.item_spinner, parent, false);
        }else if(viewType== Form.CHECKBOX){
            v = inflater.inflate(R.layout.item_checkbox_parent, parent, false);
        }else if(viewType== Form.TIME){
            v = inflater.inflate(R.layout.item_date, parent, false);
        }else if(viewType== Form.FILE){
            v = inflater.inflate(R.layout.item_file, parent, false);
        }else if(viewType== Form.SEARCH){
            v = inflater.inflate(R.layout.item_search, parent, false);
        }else if(viewType== Form.ONETOMANY){
            v = inflater.inflate(R.layout.item_onetomany, parent, false);
        }else if(viewType== Form.RADIO){
            v = inflater.inflate(R.layout.item_radio, parent, false);
        }else if(viewType== Form.SLIDER){
            v = inflater.inflate(R.layout.item_slide, parent, false);
        }else if(viewType== Form.PASSWORD){
            v = inflater.inflate(R.layout.item_password, parent, false);
        }else if(viewType== Form.IMAGE){
            v = inflater.inflate(R.layout.item_image, parent, false);
        }else if(viewType== Form.AUTOCOMPLETE){
            v = inflater.inflate(R.layout.form_autocomplete, parent, false);
        }else if(viewType== Form.ONELINETEXT){
            v = inflater.inflate(R.layout.item_onelinetext, parent, false);
        }else if(viewType== Form.TEXT2){
            v = inflater.inflate(R.layout.item_text2, parent, false);
        }else{
            v = inflater.inflate(R.layout.item_edittext, parent, false);
        }
        return v;
    }
}
