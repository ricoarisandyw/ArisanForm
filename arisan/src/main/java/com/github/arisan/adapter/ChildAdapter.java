package com.github.arisan.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.arisan.ArisanForm;
import com.github.arisan.ArisanPreparation;
import com.github.arisan.R;
import com.github.arisan.helper.FieldAssembler;
import com.github.arisan.model.ArisanFieldModel;
import com.google.gson.Gson;

import java.util.List;

public class ChildAdapter extends RecyclerView.Adapter<ChildAdapter.ViewHolder> {

    List<List<ArisanFieldModel>> mList;
    Context mContext;

    public ChildAdapter(List<List<ArisanFieldModel>> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        RecyclerView vChild;

        public ViewHolder(View v) {
            super(v);
            vChild = v.findViewById(R.id.arisan_item_child);
        }
    }

    @Override
    public ChildAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_child, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ChildAdapter.ViewHolder holder, final int position) {
        holder.vChild.setLayoutManager(new LinearLayoutManager(mContext));

        ArisanPreparation arisanPreparation = new ArisanPreparation(mContext);
        arisanPreparation.setSubmit("DELETE");
        arisanPreparation.setSubmitBackground(R.drawable.btn_primary);
        arisanPreparation.useTitle(false);
        arisanPreparation.useSubmitButton(true);

        ArisanForm arisanForm = new ArisanForm(mContext);
        arisanForm.setFieldData(mList.get(position));
        arisanForm.setOnSubmitListener(new ArisanAdapter.OnSubmitListener() {
            @Override
            public void onSubmit(String response) {
                if(mList.size()!=1){
                    mList.remove(mList.get(position));
                    notifyDataSetChanged();
                }else{
                    Toast.makeText(mContext,"Cannot remove all",Toast.LENGTH_SHORT).show();
                }
            }
        });
        ArisanAdapter arisanAdapter = arisanForm.buildAdapter();

        holder.vChild.setAdapter(arisanAdapter);
        arisanPreparation.clearData();
    }

    @Override
    public int getItemCount() {
        if (mList != null)
            return mList.size();
        return 0;
    }


}
