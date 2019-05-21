package com.github.arisan;

import android.content.Context;
import android.util.Log;

import com.github.arisan.helper.ObjectReader;
import com.github.arisan.helper.PreferenceHelper;
import com.github.arisan.model.ArisanFieldModel;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ArisanPreparation {

    private PreferenceHelper preferenceHelper;
    private List<ArisanFieldModel> fieldModels;
    private String MY_MODEL = "MY_MODEL";
    private String MY_TITLE = "TITLE";
    private String MY_SUBMIT = "SUBMIT";
    private String MY_BACKGROUND = "BACKGROUND";
    private String MY_SUBMIT_COLOR = "SUBMIT_COLOR";
    private String MY_COLOR = "COLOR";
    private String USE_TITLE = "USE_TITLE";
    private String USE_SUBMIT_BUTTON = "USE_SUBMIT_BUTTON";

    public ArisanPreparation(Context context) {
        preferenceHelper = new PreferenceHelper(context);
    }

    public void setModel(Object t) {
        fieldModels = ObjectReader.getField(t);
        preferenceHelper.saveObj(MY_MODEL, fieldModels);
    }

    public int getSubmitBackground() {
        try {
            return Integer.parseInt(preferenceHelper.load(MY_BACKGROUND));
        } catch (Exception e) {
            return 0;
        }
    }

    public void useTitle(boolean use) {
        preferenceHelper.save(USE_TITLE, String.valueOf(use));
    }

    public boolean isUseTitle() {
        try {
            return Boolean.valueOf(preferenceHelper.load(USE_TITLE)).booleanValue();
        } catch (Exception e) {
            return true;
        }
    }

    public void useSubmitButton(boolean use) {
        preferenceHelper.save(USE_SUBMIT_BUTTON, String.valueOf(use));
    }

    public boolean isUseSubmitButton() {
        try {
            return Boolean.valueOf(preferenceHelper.load(USE_SUBMIT_BUTTON)).booleanValue();
        } catch (Exception e) {
            return true;
        }
    }

    public void setTitle(String title) {
        preferenceHelper.save(MY_TITLE, title);
    }

    public String getTitle() {
        return preferenceHelper.load(MY_TITLE);
    }

    public String getSubmit() {
        return preferenceHelper.load(MY_SUBMIT);
    }

    public String getSubmitColor() {
        return preferenceHelper.load(MY_SUBMIT_COLOR);
    }

    public int getColor() {
        try {
            return Integer.parseInt(preferenceHelper.load(MY_COLOR));
        } catch (Exception e) {
            return 0;
        }
    }

    public void setSubmit(String submit) {
        preferenceHelper.save(MY_SUBMIT, submit);
    }

    public void setSubmitBackground(int button_background) {
        preferenceHelper.save(MY_BACKGROUND, String.valueOf(button_background));
    }

    public void setSubmitColor(int color) {
        preferenceHelper.save(MY_SUBMIT_COLOR, String.valueOf(color));
    }

    public void setColor(int color) {
        preferenceHelper.save(MY_COLOR, String.valueOf(color));
    }

    public List<ArisanFieldModel> getModel() {
        Type token = new TypeToken<ArrayList<ArisanFieldModel>>() {
        }.getType();
        List<ArisanFieldModel> models = (List) preferenceHelper.loadObjList(MY_MODEL, token);
        return models;
    }

    public void fillData(final String fieldName, String[] data) {
        ArisanFieldModel foundModel = new ArisanFieldModel();
        boolean found = false;
        for (ArisanFieldModel afm : fieldModels) {
            if (afm.getName().equals(fieldName)) {
                foundModel = afm;
                found = true;
                break;
            }
        }
        if (found) {
            fieldModels.remove(foundModel);
            foundModel.setData(data);
            fieldModels.add(foundModel);
            preferenceHelper.saveObj(MY_MODEL, fieldModels);
        } else {
            Log.e("__Arisan Error", "Fill Field with name " + fieldName + " not found!!!");
        }
    }

    public void clearData(){
        preferenceHelper.save(USE_TITLE,null);
        preferenceHelper.save(USE_SUBMIT_BUTTON,null);
        preferenceHelper.save(MY_BACKGROUND,null);
        preferenceHelper.save(MY_COLOR,null);
        preferenceHelper.save(MY_MODEL,null);
        preferenceHelper.save(MY_SUBMIT,null);
        preferenceHelper.save(MY_TITLE,null);
    }
}
