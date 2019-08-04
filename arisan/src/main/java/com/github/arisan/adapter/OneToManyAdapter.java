package com.github.arisan.adapter;

import android.view.ViewGroup;
import android.widget.Toast;

import com.github.arisan.FormConfig;
import com.github.arisan.helper.ChildUtils;
import com.github.arisan.model.FormModel;

import java.util.ArrayList;
import java.util.List;

public class OneToManyAdapter {

    private List<List<FormModel>> mList;
    private FormConfig config;
    private ViewGroup parent_view;
    private List<FormAdapter> listForm = new ArrayList<>();
    private String parent_field_name;

    public OneToManyAdapter(ViewGroup parent_view) {
        this.parent_view = parent_view;
    }

    public void init(){

    }

    public FormConfig convertToChildConfig(FormConfig config){
        FormConfig childConfig = config;
        childConfig.submitText = "DELETE";
        childConfig.useTitle = false;
        childConfig.isChild = true;
        return childConfig;
    }

    public void processData(){
        for(final List<FormModel> models : mList){
            final FormAdapter adapter = new FormAdapter(parent_view.getContext());
            adapter.setParent_field_name(parent_field_name);
            adapter.setOnSubmitListener(new FormAdapter.OnSubmitListener() {
                @Override
                public void onSubmit(String result) {
                    int idx = mList.indexOf(models);
                    if(mList.size()!=1) {
                        listForm.remove(adapter);
                        mList.remove(idx);
                        adapter.explode();
                        reindex();
                    }else{
                        Toast.makeText(adapter.getContext(), "Cannot Remove all", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            adapter.setConfig(convertToChildConfig(config));
            adapter.getConfig().index_child = mList.indexOf(models);
            adapter.setFieldModels(models);
            adapter.setParent_view(parent_view);
            adapter.processData();
            listForm.add(adapter);
        }
    }

    public void addNewData(){
        final List<FormModel> new_data = ChildUtils.listValueRemover(mList);
        mList.add(new_data);
        //Add form to parent

        final FormAdapter new_adapter = new FormAdapter(parent_view.getContext());
        new_adapter.setParent_field_name(parent_field_name);
        new_adapter.setOnSubmitListener(new FormAdapter.OnSubmitListener() {
            @Override
            public void onSubmit(String result) {
                int idx = mList.indexOf(new_data);
                if(mList.size()!=1) {
                    listForm.remove(new_adapter);
                    mList.remove(idx);
                    new_adapter.explode();
                    reindex();
                }else{
                    Toast.makeText(new_adapter.getContext(), "Cannot Remove all", Toast.LENGTH_SHORT).show();
                }
            }
        });
        new_adapter.setConfig(convertToChildConfig(config));
        new_adapter.getConfig().index_child = mList.indexOf(new_data);
        new_adapter.setFieldModels(new_data);
        new_adapter.setParent_view(parent_view);
        new_adapter.processData();

        listForm.add(new_adapter);
    }

    void reindex(){
        for(FormAdapter form:listForm){
            form.getConfig().index_child = listForm.indexOf(form);
        }
    }

    //=======SETTER GETTER============//

    public ViewGroup getParent_view() {
        return parent_view;
    }

    public void setParent_view(ViewGroup parent_view) {
        this.parent_view = parent_view;
    }

    public List<List<FormModel>> getList() {
        return mList;
    }

    public void setList(List<List<FormModel>> mList) {
        this.mList = mList;
    }

    public FormConfig getConfig() {
        return config;
    }

    public void setConfig(FormConfig config) {
        this.config = config.renew();
    }

    public List<FormAdapter> getListForm() {
        return listForm;
    }

    public void setListForm(List<FormAdapter> listForm) {
        this.listForm = listForm;
    }

    public String getParent_field_name() {
        return parent_field_name;
    }

    public void setParent_field_name(String parent_field_name) {
        this.parent_field_name = parent_field_name;
    }
}
