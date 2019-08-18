package com.github.arisan;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.github.arisan.adapter.ArisanAdapter;
import com.github.arisan.adapter.FormAdapter;
import com.github.arisan.helper.ImagePickerUtils;
import com.github.arisan.helper.KotlinFilter;
import com.github.arisan.helper.ObjectReader;
import com.github.arisan.helper.PreferenceHelper;
import com.github.arisan.model.FormModel;
import com.github.arisan.model.FormViewHolder;
import com.github.arisan.model.ListenerModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ArisanForm extends ScrollView {
    private List<FormModel> fieldModels = new ArrayList<>();
    private FormConfig config = new FormConfig();
    private FormAdapter.OnSubmitListener onSubmitListener;
    private PreferenceHelper preference;
    private String blank_message = "cannot blank!!!";
    private boolean no_blank = false;
    private boolean isChild = false;
    private int index_child = -1;
    private ArisanListener.ProgressListener progressListener;

    private FormAdapter formAdapter;
    LinearLayout child;
    public ViewGroup.LayoutParams LAYOUT_PARAMS = new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

    void init(){
        //WRITE CODE HERE
        Log.d("_PROCESS","INIT FORM VIEW");
        child = new LinearLayout(getContext());
        child.setOrientation(LinearLayout.VERTICAL);
        child.setLayoutParams(LAYOUT_PARAMS);
        addView(child);
    }

    public List<FormModel> getFieldModels() {
        //TODO: Special Threatment
        return fieldModels;
    }

    public void setModels(Object object) {
        this.fieldModels = ObjectReader.getField(object);
    }

    public void buildForm(){
        child.removeAllViews();
        formAdapter = new FormAdapter(getContext());
        formAdapter.setOnSubmitListener(onSubmitListener);
        if(config!=null) formAdapter.setConfig(config);
        formAdapter.setParent_view(child);
        formAdapter.setFieldModels(fieldModels);
        formAdapter.processData();
    }

    public void notifyValue(){
        formAdapter.setFieldModels(this.fieldModels);
        formAdapter.notifyValue();
    }

    public void updateImage(ImagePickerUtils utils){
        formAdapter.updateImage(utils);
    }

    //========CONTEXT INIT============//

    public ArisanForm(Context context) {
        super(context);
        Log.d("INIT A","");
        init();
    }

    public ArisanForm(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Log.d("INIT","B");
        init();
    }

    public ArisanForm(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.d("INIT","C");
        init();
    }

    public ArisanForm(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        Log.d("INIT","D");
        init();
    }

    //============SET GET=============/


    public FormAdapter getFormAdapter() {
        return formAdapter;
    }

    public void setFormAdapter(FormAdapter formAdapter) {
        this.formAdapter = formAdapter;
    }

    //Before processed
    public void addListener(String field_name, ArisanListener.OnCondition condition){
        if(condition!=null) {
            FormModel model = new KotlinFilter().findFieldByName(field_name, fieldModels);
            if(model!=null) model.addCondition(condition);
        } else
            Log.e("Arisan","No Condition");
    }

    public void addChildListener(String parent_name, String field_name, ArisanListener.OnCondition condition){
        if(condition!=null){
            FormModel model = new KotlinFilter().findFieldByName(parent_name, fieldModels);
            if(model!=null&&model.getChildFieldModel()!=null)
                for(List<FormModel> m:model.getChildFieldModel()){
                    FormModel mm = new KotlinFilter().findFieldByName(field_name, m);
                    if(mm!=null) mm.setArisanListener(condition);
                }
        }
    }

    public void updateChildListener(String parent_name, String field_name, ArisanListener.OnCondition condition){
        if(condition!=null){
            FormModel model = new KotlinFilter().findFieldByName(field_name, fieldModels);
            FormViewHolder holder = new KotlinFilter().filterViewHolder(parent_name,formAdapter.getHolderList());
            if(model!=null) {
                for(FormAdapter adtr:holder.adapter.getListForm())
                    adtr.updateListener(field_name,condition);
            }
        }
    }
    //After processed
    public void updateListener(String field_name,ArisanListener.OnCondition condition){
        this.formAdapter.updateListener(field_name,condition);
    }

    public void setFieldModels(List<FormModel> fieldModels) {
        this.fieldModels.addAll(fieldModels);
    }

    public FormConfig getConfig() {
        return config;
    }

    public void setConfig(FormConfig config) {
        this.config = config;
    }

    public FormAdapter.OnSubmitListener getOnSubmitListener() {
        return onSubmitListener;
    }

    public void setOnSubmitListener(FormAdapter.OnSubmitListener onSubmitListener) {
        this.onSubmitListener = onSubmitListener;
    }

    //=========UPDATER==========//

    public void updateValue(String field_name,Object data){
        this.formAdapter.updateValue(field_name,data);
    }

    public void updateValue(String parent_field,String field_name,Object data){
        this.formAdapter.updateValue(field_name,data);
    }

    public void updateData(String field_name,Object data){
        this.formAdapter.updateData(field_name,data);
    }

    public void fillData(String field_name,Object data){
        FormModel model = new KotlinFilter().findFieldByName(field_name,this.fieldModels);
        if(model!=null) model.setData(data);
    }

    public void notifyValueSetChanged(){
        for(FormModel new_model : this.fieldModels){
            FormModel old_model = new KotlinFilter().findFieldByName(new_model.getName(),formAdapter.getFieldModels());
            if(old_model!=null&&old_model.getValue()!=new_model.getValue()){
                updateValue(new_model.getName(),new_model.getValue());
            }
        }
    }

    public void showSubmitProgress(boolean show) {
        formAdapter.showSubmitProgress(show);
    }

    public void updateConfig(FormConfig config){
        formAdapter.setConfig(config);
    }
}
