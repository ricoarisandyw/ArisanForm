package com.github.arisan.model;

import android.view.View;

import com.github.arisan.ArisanListener;
import com.github.arisan.adapter.FormAdapter;

public interface ArisanCustomForm{
    int getLayout();
    void onCreated(FormViewHolder holder, FormAdapter adapter);
}
