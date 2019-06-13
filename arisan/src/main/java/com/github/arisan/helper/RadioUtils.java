package com.github.arisan.helper;

import android.view.View;

import com.github.arisan.model.RadioModel;

import java.util.ArrayList;
import java.util.List;

public class RadioUtils {

    public static List<RadioModel> convertToRadio(String... arg){
        List<RadioModel> models = new ArrayList<>();
        for(String a:arg){
            RadioModel radioModel = new RadioModel();
            radioModel.setId(View.generateViewId());
            radioModel.setValue(a);
            models.add(radioModel);
        }
        return models;
    }

}
