package com.github.arisanform.activity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.arisan.ArisanListener;
import com.github.arisan.adapter.FormAdapter;
import com.github.arisan.model.ArisanCustomForm;
import com.github.arisan.model.FormModel;
import com.github.arisan.model.FormViewHolder;
import com.github.arisanform.R;

public class MyCustomToggleForm implements ArisanCustomForm {
    String result;
    boolean is_active = true;

    @Override
    public int getLayout() {
        return R.layout.custom_form;
    }

    @Override
    public void onCreated(FormViewHolder holder, FormAdapter adapter) {
        //============ FormModel =============//
        // gain FormModel from holder.data
        // FormModel contain Annotation properties and Field properties
        // Such as : value, label
        //====================================//

        //get view from holder which inflate this layout.
        View view = holder.view.getView();
        Button button = view.findViewById(R.id.custom_form_toggle);
        TextView label = view.findViewById(R.id.custom_form_label);
        label.setText(holder.data.getLabel());

        button.setOnClickListener(view1 -> {
            if(is_active){
                button.setBackgroundColor(view.getContext().getResources().getColor(R.color.colorDanger));
                button.setText("OFF");
                is_active = false;

                //Condition Excecuted
                holder.data.getCondition().onValue("OFF",adapter);

                //Set Value for Submit
                holder.data.setValue(false);
            }else{
                button.setBackgroundColor(view.getContext().getResources().getColor(R.color.colorPrimary));
                button.setText("ON");
                is_active = true;

                //Condition Excecuted
                holder.data.getCondition().onValue("ON",adapter);

                //Set Value for Submit
                holder.data.setValue(true);
            }
        });
        //1. I need context here to do something.
    }
}
