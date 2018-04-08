package com.javacreativeapps.arisan.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.javacreativeapps.arisan.R;
import com.javacreativeapps.arisan.helper.ObjectSetter;
import com.javacreativeapps.arisan.model.FieldDetail;

import java.util.List;

/**
 * Created by wijaya on 3/27/2018.
 */

public class FormAdapter extends RecyclerView.Adapter<FormAdapter.ViewHolder> implements View.OnClickListener{
    List<FieldDetail> mList;
    Context mContext;
    public Object mResult;
    int counter = 0;

    public FormAdapter(List<FieldDetail> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    public void setObject(Object o,List<FieldDetail> fieldDetailList){
        this.mResult = o;
        this.mList = fieldDetailList;
    }

    @Override
    public void onClick(View v) {

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView mInputTitle;
        EditText mEditText;
        public ViewHolder(View v) {
            super(v);
            mInputTitle = v.findViewById(R.id.arisan_input_text_title);
            mEditText = v.findViewById(R.id.arisan_input_text);
        }
    }

    @Override
    public FormAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v;
        ViewHolder vh;
        v = inflater.inflate(R.layout.item_edittext, parent, false);
        vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final FormAdapter.ViewHolder holder, int position) {

        final FieldDetail data = mList.get(position);

        holder.mInputTitle.setText(data.getName());
        holder.mEditText.setHint(mList.get(position).getName());
        holder.mEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                input(data,holder.mEditText.getText().toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        if(mList!=null)
            return mList.size();
        else
            return 0;
    }

    public interface OnResponse<T>{
        public void response(T response);
    }

    public void setOnResponse(OnResponse onResponse){

    }

    public void input(FieldDetail f,Object value){
            try {
                if (f.getFieldType().equals(String.valueOf(String.class.getCanonicalName()))){
                    ObjectSetter.set(mResult, f.getName(), value);
                } else if (f.getFieldType().equals(String.valueOf(boolean.class))) {
                    ObjectSetter.set(mResult, f.getName(), value);
                } else if (f.getFieldType().equals(String.valueOf(int.class))) {
                    ObjectSetter.set(mResult, f.getName(), value);
                } else if (f.getFieldType().equals(String.valueOf(String.class))) {

                }
            }catch (Exception e){

            }
    }

    public Object getResult(){
        return mResult;
    }
}
