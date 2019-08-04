package com.github.arisan.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.github.arisan.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CheckboxAdapter extends RecyclerView.Adapter<CheckboxAdapter.ViewHolder> {
    List<String> mDataset;
    List<String> checkedData;
    OnCheckedListener onCheckedListener;

    public CheckboxAdapter(List<String> mDataset, List<String> checkedData) {
        this.mDataset = mDataset;
        this.checkedData = checkedData;
        if(this.checkedData==null){
            this.checkedData = new ArrayList<String>();
        }
    }

    public void setOnCheckedListener(OnCheckedListener onCheckedListener) {
        this.onCheckedListener = onCheckedListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_checkbox,parent,false);
        CheckboxAdapter.ViewHolder vh = new CheckboxAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final String value = mDataset.get(position);
        holder.vCheckbox.setText(value);
        if(checkedData.contains(value)){
            holder.vCheckbox.setChecked(true);
        }
        holder.vCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    checkedData.add(value);
                    onCheckedListener.onChecked(value,checkedData);
                } else{
                    checkedData.remove(value);
                    onCheckedListener.onChecked(value,checkedData);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        if(mDataset!=null && mDataset.size()>0)
            return mDataset.size();
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox vCheckbox;

        public ViewHolder(View v) {
            super(v);
            vCheckbox = v.findViewById(R.id.arisan_checkbox);
        }
    }

    public interface OnCheckedListener{
        void onChecked(String checked, List<String> already_checked);
    }
}
