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

    public ArisanForm(Context context){
        ArisanPreparation preparation = new ArisanPreparation(context);
        List<ArisanFieldModel> fieldData = preparation.getModel();
        adapter = new ArisanAdapter(context, fieldData);
        adapter.setTitle(preparation.getTitle());
        adapter.setSubmitText(preparation.getSubmit());
    }

    public ArisanForm setOnSubmitListener(ArisanAdapter.OnSubmitListener onSubmitListener){
        adapter.setOnSubmitListener(onSubmitListener);
        return this;
    }

    public ArisanAdapter buildAdapter(){
        return adapter;
    }
}
