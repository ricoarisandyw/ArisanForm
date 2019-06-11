package com.github.arisan;

import android.content.Context;

import com.github.arisan.adapter.ArisanAdapter;
import com.github.arisan.model.ArisanFieldModel;

import java.util.List;

/**
 * Created by wijaya on 4/22/2018.
 */

public class ArisanForm {
    private ArisanAdapter adapter;
    private Context context;
    private List<ArisanFieldModel> fieldData;
    private ArisanPreparation preparation;
    private ArisanAdapter.OnSubmitListener onSubmitListener;

    public ArisanForm(Context context){
        this.preparation = new ArisanPreparation(context);
        this.fieldData = preparation.getModel();
        this.context = context;
    }

    public List<ArisanFieldModel> getFieldData() {
        return fieldData;
    }

    public void setFieldData(List<ArisanFieldModel> fieldData) {
        this.fieldData = fieldData;
    }

    public ArisanForm addListener(String field_name, ArisanListener.Condition onSomething){
        for(ArisanFieldModel model : fieldData){
            if(model.getName().equals(field_name)){
                model.addCondition(onSomething);
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
        adapter = new ArisanAdapter(context, fieldData);
        adapter.setTitle(preparation.getTitle());
        adapter.setSubmitText(preparation.getSubmit());
        adapter.setSubmitBackground(preparation.getSubmitBackground());
        adapter.setOnSubmitListener(this.onSubmitListener);
        return adapter;
    }
}
