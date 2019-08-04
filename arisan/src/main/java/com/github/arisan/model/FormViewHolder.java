package com.github.arisan.model;

import android.view.View;

import com.github.arisan.adapter.FormAdapter;
import com.github.arisan.adapter.MyView;

import java.util.List;

public class FormViewHolder {
    public MyView view;
    public FormModel data;
    int position;
    int color;

    public List<FormAdapter> childForm;

    public FormViewHolder(View view) {
        this.view = new MyView(view);
    }

    public FormModel getData() { return data; }

    public void setData(FormModel data) { this.data = data; }

    public int getPosition() { return position; }

    public void setPosition(int position) { this.position = position; }

    public List<FormAdapter> getChildForm() { return childForm; }

    public void setChildForm(List<FormAdapter> childForm) { this.childForm = childForm; }


    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
