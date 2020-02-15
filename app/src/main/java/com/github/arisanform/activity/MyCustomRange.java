package com.github.arisanform.activity;

import android.widget.TextView;

import com.github.arisan.adapter.FormAdapter;
import com.github.arisan.model.ArisanCustomForm;
import com.github.arisan.model.FormViewHolder;
import com.github.arisanform.R;

public class MyCustomRange implements ArisanCustomForm {
    @Override
    public int getLayout() {
        return R.layout.custom_input_range;
    }

    @Override
    public void onCreated(FormViewHolder holder, FormAdapter adapter) {
        holder.data.setValue(2000);

        ((TextView) holder.view.getView().findViewById(R.id.custom_range_label)).setText(holder.data.getLabel());
    }
}
