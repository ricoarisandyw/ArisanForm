package com.github.arisan.model;

import com.github.arisan.annotation.Form;

import java.util.HashMap;

public class TypeForm {
    HashMap<String, Integer> type = new HashMap<>();
    public TypeForm(){
        type.put(Form.TEXT,171);
        type.put(Form.NUMBER,172);
        type.put(Form.BOOLEAN,173);
        type.put(Form.SPINNER,174);
        type.put(Form.DATE,175);
        type.put(Form.CHECKBOX,176);
        type.put(Form.PASSWORD,177);
        type.put(Form.TIME,178);
        type.put(Form.FILE,179);
        type.put(Form.DATETIME,180);
        type.put(Form.SEARCH,181);
        type.put(Form.ONETOMANY,182);
    }
    public int get(String name){
        return type.get(name);
    }
}
