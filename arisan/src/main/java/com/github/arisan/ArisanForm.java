package com.github.arisan;

import android.content.Context;

import com.github.arisan.adapter.ArisanAdapter;
import com.github.arisan.helper.ObjectReader;
import com.github.arisan.model.ArisanFieldModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wijaya on 4/22/2018.
 */

public class ArisanForm {
    private Context context;
    private List<ArisanFieldModel> fieldData;
    private ArisanPreparation preparation;
    private ArisanAdapter.OnSubmitListener onSubmitListener;

    private boolean use_title = true;
    private boolean use_submit = true;
    private int submit_background;
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

    public int getSubmit_background() {
        return submit_background;
    }

    public void setSubmit_background(int submit_background) {
        this.submit_background = submit_background;
    }

    public ArisanForm(Context context){
        this.preparation = new ArisanPreparation(context);
        this.fieldData = preparation.getModel();
        this.context = context;
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
        ArisanAdapter adapter = new ArisanAdapter(context, this);
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


}
