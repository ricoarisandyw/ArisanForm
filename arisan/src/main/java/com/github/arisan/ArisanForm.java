package com.github.arisan;

import android.app.Activity;

import com.github.arisan.adapter.ArisanAdapter;
import com.github.arisan.helper.ObjectReader;
import com.github.arisan.model.ArisanFieldModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wijaya on 4/22/2018.
 */

public class ArisanForm {
    private Activity activity;
    private List<ArisanFieldModel> fieldData;
    private ArisanPreparation preparation;
    private ArisanAdapter.OnSubmitListener onSubmitListener;

    private boolean use_title = true;
    private boolean use_submit = true;
    private int background;
    private int labelColor;
    private int buttonColor;
    private String submitText = "SUBMIT";
    private String title;
    private String blankMessage;

    public String getBlankMessage() {
        return blankMessage;
    }

    public void setBlankMessage(String blankMessage) {
        this.blankMessage = blankMessage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubmitText() {
        return submitText;
    }

    public void setSubmitText(String submitText) {
        this.submitText = submitText;
    }

    public int getBackground() {
        return background;
    }

    public void setBackground(int background) {
        this.background = background;
    }

    public ArisanForm(Activity activity){
        this.preparation = new ArisanPreparation(activity);
        this.fieldData = preparation.getModel();
        this.activity = activity;
    }

    public boolean isUse_title() {
        return use_title;
    }

    public void setUse_title(boolean use_title) {
        this.use_title = use_title;
    }

    public boolean isUse_submit() {
        return use_submit;
    }

    public void setUse_submit(boolean use_submit) {
        this.use_submit = use_submit;
    }

    public List<ArisanFieldModel> getFieldData() {
        return fieldData;
    }

    public void setFieldData(List<ArisanFieldModel> fieldData) {
        this.fieldData = fieldData;
    }

    public void setModel(Object object){
        this.fieldData = ObjectReader.getField(object);
    }

    public int getLabelColor() {
        return labelColor;
    }

    public void setLabelColor(int labelColor) {
        this.labelColor = labelColor;
    }

    public ArisanForm addListener(String field_name, ArisanListener.Condition onSomething){
        for(ArisanFieldModel model : fieldData){
            if(model.getName().equals(field_name)){
                model.setArisanListener(onSomething);
                break;
            }
        }
        return this;
    }

    public ArisanForm addChildListener(String parent_field,String child_field, ArisanListener.Condition onSomething){
        for(ArisanFieldModel model : fieldData){
            if(model.getName().equals(parent_field)){
                for(ArisanFieldModel childModel :model.getChildFieldModel().get(0)){
                    if(childModel.getName().equals(child_field)){
                        childModel.setArisanListener(onSomething);
                        break;
                    }
                }
            }
        }
        return this;
    }

    public ArisanForm addCheckboxListener(String field_name, ArisanListener.CheckboxCondition onSomething){
        for(ArisanFieldModel model : fieldData){
            if(model.getName().equals(field_name)){
                model.setCheckboxListener(onSomething);
                break;
            }
        }
        return this;
    }

    public ArisanForm addViewMod(String field_name,ArisanListener.ViewMod onSomething){
        for(ArisanFieldModel model : fieldData){
            if(model.getName().equals(field_name)){
                model.addViewMod(onSomething);
                break;
            }
        }
        return this;
    }

    public ArisanForm addSearchListener(String field_name,ArisanListener.SearchCondition onSomething){

        return this;
    }

    public ArisanForm setOnSubmitListener(ArisanAdapter.OnSubmitListener onSubmitListener){
        this.onSubmitListener = onSubmitListener;
        return this;
    }

    public ArisanAdapter buildAdapter(){
        ArisanAdapter adapter = new ArisanAdapter(activity, this);
        adapter.setSubmitBackground(preparation.getSubmitBackground());
        adapter.setOnSubmitListener(this.onSubmitListener);
        return adapter;
    }

    public void copyFieldFromAdapter(ArisanAdapter arisanAdapter){
        List<ArisanFieldModel> new_model = new ArrayList<>();
        for (ArisanFieldModel a:arisanAdapter.getListField()){
            new_model.add(a.renew());
        }
        setFieldData(new_model);
    }

    public void fillData(String field_name,Object value){
        for (ArisanFieldModel fieldModel:fieldData){
            if(fieldModel.equals(field_name)){
                fieldModel.setValue(value);
                break;
            }
        }
    }

    public int getButtonColor() {
        return buttonColor;
    }

    public void setButtonColor(int buttonColor) {
        this.buttonColor = buttonColor;
    }
}
