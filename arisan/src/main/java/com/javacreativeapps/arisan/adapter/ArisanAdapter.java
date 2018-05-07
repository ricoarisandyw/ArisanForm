package com.javacreativeapps.arisan.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.javacreativeapps.arisan.ArisanClosing;
import com.javacreativeapps.arisan.ArisanGetter;
import com.javacreativeapps.arisan.annotation.Form;
import com.javacreativeapps.arisan.helper.ObjectSetter;
import com.javacreativeapps.arisan.model.ArisanField;
import com.javacreativeapps.arisan.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wijaya on 3/27/2018.
 */

public class ArisanAdapter extends RecyclerView.Adapter<ArisanAdapter.ViewHolder> implements View.OnClickListener{
    List<ArisanField> mList = new ArrayList<>();
    Context mContext;
    public Object mResult;
    GsonBuilder gsonBuilder = new GsonBuilder();
    Gson gson = gsonBuilder.create();

    public ArisanAdapter(Context context) {
        this.mContext = context;
        mList.add(new ArisanField());
        mList.addAll(ArisanGetter.getData(context));
        mList.add(new ArisanField());
        System.out.println("Form Adapter Constructor");
    }

    @Override
    public ArisanAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        System.out.println("On Create"+viewType);
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v;
        if(viewType==0){
            v = inflater.inflate(R.layout.item_text, parent, false);
        }else if(viewType==mList.size()){
            v = inflater.inflate(R.layout.item_button, parent, false);
        }else if(viewType==Form.BOOLEAN){
            v = inflater.inflate(R.layout.item_switch, parent, false);
        }else{
            v = inflater.inflate(R.layout.item_edittext, parent, false);
        }
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView mInputTitle;
        EditText mEditText;
        TextView mTitle;
        TextView mSwitchTitle;
        Button mSubmit;
        Switch aSwitch;
        public ViewHolder(View v) {
            super(v);
            System.out.println("ViewHolder");
            mInputTitle = v.findViewById(R.id.arisan_input_text_title);
            mEditText = v.findViewById(R.id.arisan_input_text);
            mTitle = v.findViewById(R.id.arisan_text);
            mSubmit = v.findViewById(R.id.arisan_input_button);
            aSwitch = v.findViewById(R.id.arisan_input_switch);

        }
    }

    public void setObject(List<ArisanField> arisanFieldList){
        this.mList = arisanFieldList;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return 0;
        }else if(position==mList.size()-1){
            return mList.size();
        }else if(mList.get(position).getViewType()==Form.DATE){
            return Form.DATE;
        }else if(mList.get(position).getViewType()==Form.PASSWORD){
            return Form.PASSWORD;
        }else if(mList.get(position).getViewType()==Form.CHECKBOX){
            return Form.CHECKBOX;
        }else if(mList.get(position).getViewType()==Form.NUMBER){
            return Form.NUMBER;
        }else if(mList.get(position).getViewType()==Form.RADIO){
            return Form.RADIO;
        }else if(mList.get(position).getViewType()==Form.TIME){
            return Form.TIME;
        }else if(mList.get(position).getViewType()==Form.BOOLEAN){
            return Form.BOOLEAN;
        }else{
            return Form.TEXT;
        }
    }



    @Override
    public void onBindViewHolder(final ArisanAdapter.ViewHolder holder, final int position) {

        final ArisanField data = mList.get(position);
//        System.out.println("On Bind");
        if(position==0){
            //TITLE
            holder.mTitle.setText(ArisanGetter.getTitle(mContext));
        }else if(position==mList.size()-1){
            //SUBMIT BUTTON
            holder.mSubmit.setText("Submit");
            holder.mSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mList.remove(0);
                    mList.remove(mList.size()-1);
                    ArisanClosing.submit(mContext,mList);
                }
            });
        }else if(data.getViewType()==Form.BOOLEAN){
            holder.aSwitch.setText(data.getName());
            if(data.getValue()!= null) {
                if (data.getValue().toString().equals("true")) {
                    holder.aSwitch.setChecked(true);
                } else {
                    holder.aSwitch.setChecked(false);
                }
            }
            holder.aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    mList.get(position).setValue(isChecked);
                }
            });
        }else{
            //Edit Text
            holder.mInputTitle.setText(data.getName());
            if(data.getViewType()==Form.PASSWORD){
                holder.mEditText.setInputType(InputType.TYPE_CLASS_TEXT |
                        InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }else if(data.getViewType()== Form.NUMBER){
                holder.mEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
                if(data.getValue()!=null&&data.getValue()!=""){
                    holder.mEditText.setText(data.getValue().toString());
                }
            }else{
                //Input Type Text
                if(data.getValue()!=null&&data.getValue()!=""){
                    holder.mEditText.setText(data.getValue().toString());
                }
                if(data.getError_message()!=null&&data.getValue()!=""){
                    holder.mEditText.setError(data.getError_message());
                }
            }

            //holder.mEditText.setHint(mList.get(position).getName());
            holder.mEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    mList.get(position).setValue(holder.mEditText.getText().toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
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

    public void input(ArisanField f, Object value){
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


    public List<ArisanField> getResult(){
        return mList;
    }
}
