package com.github.arisan.adapter;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.arisan.ArisanForm;
import com.github.arisan.R;
import com.github.arisan.model.ArisanFieldModel;

import java.util.List;

public class ChildAdapter extends RecyclerView.Adapter<ChildAdapter.ViewHolder> {

    List<List<ArisanFieldModel>> mList;
    private Activity activity;
    private ArisanForm form;

    ChildAdapter(List<List<ArisanFieldModel>> mList, Activity activity, ArisanForm form) {
        this.mList = mList;
        this.activity = activity;
        this.form = form;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        RecyclerView vChild;

        ViewHolder(View v) {
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
        holder.vChild.setLayoutManager(new LinearLayoutManager(activity));

        ArisanForm arisanForm = new ArisanForm(activity);
        arisanForm.setLabelColor(form.getLabelColor());
        arisanForm.setBackground(form.getBackground());
        arisanForm.setUse_title(false);
        arisanForm.setSubmitText("DELETE");
        arisanForm.setFieldData(mList.get(position));
        arisanForm.setOnSubmitListener(new ArisanAdapter.OnSubmitListener() {
            @Override
            public void onSubmit(String response) {
                if(mList.size()!=1){
                    mList.remove(mList.get(position));
                    notifyDataSetChanged();
                }else{
                    Toast.makeText(activity,"Cannot remove all",Toast.LENGTH_SHORT).show();
                }
            }
        });
        ArisanAdapter arisanAdapter = arisanForm.buildAdapter();
        arisanAdapter.setChild(true);
        arisanAdapter.setIndex_child(position);
        holder.vChild.setAdapter(arisanAdapter);
    }

    @Override
    public int getItemCount() {
        if (mList != null)
            return mList.size();
        return 0;
    }


}
