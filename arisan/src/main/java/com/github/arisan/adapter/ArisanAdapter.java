package com.github.arisan.adapter;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TimePicker;

import com.github.arisan.ArisanPreparation;
import com.github.arisan.R;
import com.github.arisan.annotation.ArisanCode;
import com.github.arisan.annotation.Form;
import com.github.arisan.annotation.Model;
import com.github.arisan.helper.ChildUtils;
import com.github.arisan.helper.DateConverter;
import com.github.arisan.helper.FieldAssembler;
import com.github.arisan.helper.FieldUtils;
import com.github.arisan.helper.SortField;
import com.github.arisan.helper.TwoDigit;
import com.github.arisan.helper.UriUtils;
import com.github.arisan.model.ArisanFieldModel;
import com.github.arisan.model.ListenerModel;
import com.github.arisan.model.RadioModel;
import com.github.arisan.model.TypeForm;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * Created by wijaya on 3/27/2018.
 */

public class ArisanAdapter extends RecyclerView.Adapter<ArisanAdapter.ViewHolder> implements View.OnClickListener{
    List<ArisanFieldModel> mList = new ArrayList<>();
    Context mContext;
    OnSubmitListener onSubmitListener;
    String title;
    String submitText;

    boolean useTitle = true;
    boolean useSubmit = true;

    public ArisanAdapter(Context context, List<ArisanFieldModel> fieldList) {
        this.mContext = context;
        Collections.sort(fieldList,new SortField());
        ArisanPreparation preparation = new ArisanPreparation(mContext);
        useTitle = preparation.isUseTitle();
        useSubmit = preparation.isUseSubmitButton();
        if(preparation.isUseTitle()){
            mList.add(new ArisanFieldModel());//For Title
        }
        mList.addAll(fieldList);
        if(preparation.isUseSubmitButton()){
            mList.add(new ArisanFieldModel());//For Submit Button
            setSubmitBackground(preparation.getSubmitBackground());
        }
        System.out.println("Form Adapter Constructor");
    }

    @Override
    public ArisanAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TypeForm type = new TypeForm();
        System.out.println("On Create"+ viewType);
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v;
        if(viewType==0){
            v = inflater.inflate(com.github.arisan.R.layout.item_text, parent, false);
        }else if(viewType==Model.BUTTON){
            v = inflater.inflate(com.github.arisan.R.layout.item_button, parent, false);
        }else if(viewType==type.get(Form.BOOLEAN)){
            v = inflater.inflate(com.github.arisan.R.layout.item_switch, parent, false);
        }else if(viewType==type.get(Form.DATE)){
            v = inflater.inflate(R.layout.item_date, parent, false);
        }else if(viewType==type.get(Form.DATETIME)){
            v = inflater.inflate(R.layout.item_date, parent, false);
        }else if(viewType== type.get(Form.SPINNER)){
            v = inflater.inflate(R.layout.item_spinner, parent, false);
        }else if(viewType== type.get(Form.CHECKBOX)){
            v = inflater.inflate(R.layout.item_checkbox_parent, parent, false);
        }else if(viewType== type.get(Form.TIME)){
            v = inflater.inflate(R.layout.item_date, parent, false);
        }else if(viewType== type.get(Form.FILE)){
            v = inflater.inflate(R.layout.item_file, parent, false);
        }else if(viewType== type.get(Form.SEARCH)){
            v = inflater.inflate(R.layout.item_search, parent, false);
        }else if(viewType== type.get(Form.ONETOMANY)){
            v = inflater.inflate(R.layout.item_onetomany, parent, false);
        }else if(viewType== type.get(Form.RADIO)){
            v = inflater.inflate(R.layout.item_radio, parent, false);
        }else if(viewType== type.get(Form.SLIDER)){
            v = inflater.inflate(R.layout.item_slide, parent, false);
        }else{
            v = inflater.inflate(R.layout.item_edittext, parent, false);
        }
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        //COMMON
        MyView view;

        public ViewHolder(View v) {
            super(v);
            view = new MyView(v);
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public int getItemViewType(int position) {
        TypeForm type = new TypeForm();
        if(position==0&&useTitle){
            return 0;
        }else if(position==mList.size()-1&&useSubmit) {
            return Model.BUTTON;
        }else{
            return type.get(mList.get(position).getViewType());
        }
    }

    @Override
    public void onBindViewHolder(final ArisanAdapter.ViewHolder holder, final int position) {
        final ArisanFieldModel data = mList.get(position);
        int color = data.getColor();
//        System.out.println("On Bind");
        if(position==0&&useTitle){
            //TITLE
            holder.view.mTitle.setText(title);
            if(data.getColor()!=0) {
                holder.view.mTitle.setTextColor(mContext.getResources().getColor(color));
            }
        }else if(position==mList.size()-1&&useSubmit){
            //SUBMIT BUTTON
            if(data.getSubmit_color()!=0){
                holder.view.mSubmit.setBackgroundResource(data.getSubmit_color());
            }
            holder.view.mSubmit.setText(submitText);
            holder.view.mSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String json = FieldAssembler.toJson(getListModel());
                    Log.d("__Result ",json);
                    onSubmitListener.onSubmit(json);
                }
            });
            if(data.getColor()!=0) {
                holder.view.mSubmit.setTextColor(mContext.getResources().getColor(color));
            }
        }else if(data.getViewType().equals(Form.BOOLEAN)) {
            holder.view.aSwitch.setText(data.getLabel());
            if (data.getValue() != null) {
                if (data.getValue().toString().equals("true")) {
                    holder.view.aSwitch.setChecked(true);
                    data.setValue(true);
                } else {
                    holder.view.aSwitch.setChecked(false);
                    data.setValue(false);
                }
            } else {
                holder.view.aSwitch.setChecked(false);
            }
            holder.view.aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    data.setValue(isChecked);
                }
            });
            if (data.getColor() != 0) {
                holder.view.aSwitch.setTextColor(mContext.getResources().getColor(color));
            }
        }else if(data.getViewType().equals(Form.SLIDER)){
            try {
                Double dbl = (Double) data.getValue();
                int value = (int) Math.round(dbl);
                if (value != 0)
                    holder.view.mSlide.setProgress(value);
            }catch(Exception ignore){ }

            holder.view.mSlideLabel.setText(data.getLabel());

            holder.view.mSlide.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    data.setValue(progress);
                    holder.view.mSlideValue.setText(String.valueOf(progress));
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) { }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) { }
            });
        }else if(data.getViewType().equals(Form.RADIO)){
            String value = (String) data.getValue();
            List<RadioModel> dataList = FieldUtils.convertDataToRadio(data.getData());
            holder.view.mRadioGroup.removeAllViews();
            for(RadioModel mData:dataList){
                RadioButton btn = new RadioButton(mContext);
                btn.setId(mData.getId());
                Log.d("__ID",String.valueOf(mData.getId()));
                btn.setText(mData.getValue());
                Log.d("__VAL",mData.getValue());
                if(mData.getValue().equals(value)){
                    btn.setChecked(true);
                }
                btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked) {
                            String result = buttonView.getText().toString();
                            //SHOW OTHERS CHECKBOX
                            if (result.equals(Model.OTHERS)) {
                                Log.d("Arisan","Show others form");

                                holder.view.mRadioText.setVisibility(View.VISIBLE);

                                data.setValue(result);
                                holder.view.mRadioText.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                        String value = holder.view.mRadioText.getText().toString();
                                        data.setValue(value);
                                    }

                                    @Override
                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

                                    @Override
                                    public void afterTextChanged(Editable s) { }
                                });
                            } else {
                                holder.view.mRadioText.setVisibility(View.GONE);
                            }
                            //CONDITION
                            try {
                                data.setValue(result);
                                data.doListener(result);
                            } catch (Exception ignored){}
                        }
                    }
                });
                holder.view.mRadioGroup.addView(btn);
            }
            holder.view.mRadioLabel.setText(data.getLabel());
        }else if(data.getViewType().equals(Form.DATE)) {
            holder.view.mDateLabel.setText(data.getLabel());
            final Calendar calendar;
            if (data.getValue() != null) {
                calendar = new DateConverter(data.getValue().toString()).from("MMMM dd, yyyy").calendar;
                holder.view.mDate.setText(new DateConverter(calendar).to(data.getDateFormat()));
            } else {
                calendar = Calendar.getInstance();
                holder.view.mDate.setText(new DateConverter(calendar).to(data.getDateFormat()));
            }
            holder.view.mDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            String result = TwoDigit.from(dayOfMonth) + "-" + TwoDigit.from(month + 1) + "-" + year;
                            holder.view.mDate.setText(new DateConverter(result).from("dd-MM-yyyy").to(data.getDateFormat()));
                            data.setValue(new DateConverter(result).from("dd-MM-yyyy").to(data.getDateFormat()));
                        }
                    }, calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)).show();
                    }
            });
            if(data.getBackground()!=0){
                holder.view.mDate.setBackgroundResource(data.getBackground());
            }
            if(data.getColor()!=0) {
                holder.view.mDate.setTextColor(mContext.getResources().getColor(color));
            }
        }else if(data.getViewType().equals(Form.TIME)){
            final Calendar calendar;
            if (data.getValue() != null) {
                calendar = new DateConverter(data.getValue().toString()).from("MMMM dd, yyyy HH:mm:ss").calendar;
                holder.view.mDate.setText(new DateConverter(calendar).to(data.getDateFormat()));
            } else {
                calendar = Calendar.getInstance();
                holder.view.mDate.setText("12:34");
            }
            holder.view.mDateLabel.setText(data.getLabel());
            holder.view.mDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            String result = TwoDigit.from(hourOfDay)+":"+TwoDigit.from(minute);
                            String convertedResult = new DateConverter(result).from("HH:mm").to(data.getDateFormat());
                            holder.view.mDate.setText(convertedResult);
                            data.setValue(convertedResult);
                        }
                    },calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),DateFormat.is24HourFormat(mContext)).show();
                }
            });
            if(data.getBackground()!=0){
                holder.view.mDate.setBackgroundResource(data.getBackground());
            }
            if(data.getColor()!=0) {
                holder.view.mDate.setTextColor(mContext.getResources().getColor(color));
            }
        }else if(data.getViewType().equals(Form.DATETIME)) {
            holder.view.mDateLabel.setText(data.getLabel());
            final Calendar calendar;
            if (data.getValue() != null) {
                calendar = new DateConverter(data.getValue().toString()).from("MMMM dd, yyyy").calendar;
                holder.view.mDate.setText(new DateConverter(calendar).to(data.getDateFormat()));
            } else {
                calendar = Calendar.getInstance();
                holder.view.mDate.setText(new DateConverter(calendar).to(data.getDateFormat()));
            }
            String result = "";
            holder.view.mDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            String result = TwoDigit.from(dayOfMonth) + "-" + TwoDigit.from(month + 1) + "-" + year;
                            data.setValue(new DateConverter(result).from("dd-MM-yyyy").to("dd-MM-yyyy"));
                            new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                    String result = data.getValue()+" "+TwoDigit.from(hourOfDay)+":"+TwoDigit.from(minute);
                                    String convertedResult = new DateConverter(result).from("dd-MM-yyyy HH:mm").to(data.getDateFormat());
                                    holder.view.mDate.setText(convertedResult);
                                    data.setValue(convertedResult);
                                }
                            },calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),DateFormat.is24HourFormat(mContext)).show();
                        }
                    }, calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            });
            if(data.getBackground()!=0){
                holder.view.mDate.setBackgroundResource(data.getBackground());
            }
            if(data.getColor()!=0) {
                holder.view.mDate.setTextColor(mContext.getResources().getColor(color));
            }
        }else if(data.getViewType().equals(Form.SPINNER)){
            holder.view.mSpinnerLabel.setText(data.getLabel());
            //ArrayAdapter mAdapter = new ArrayAdapter(mContext,android.R.layout.simple_spinner_item,(List)data.getData());
            final ArrayList<String> dataArray = (ArrayList<String>) data.getData();
            ArrayAdapter mAdapter = new ArrayAdapter<>(mContext,android.R.layout.simple_spinner_item,dataArray);
            holder.view.mSpinner.setAdapter(mAdapter);
            holder.view.mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    data.setValue(dataArray.get(pos));
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            if(data.getValue() != null){
                holder.view.mSpinner.setSelection(dataArray.indexOf(data.getValue()));
            }
        }else if(data.getViewType().equals(Form.FILE)){
            holder.view.mFileLabel.setText(data.getLabel());
            if(data.getData()!=null)
                holder.view.mFileName.setText((String)data.getData());
            holder.view.mFile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("file/*");
                    ((Activity)mContext).startActivityForResult(intent, ArisanCode.REQUEST_FILE);
                }
            });
            if(data.getBackground()!=0){
                holder.view.mFileName.setBackgroundResource(data.getBackground());
            }
            if(data.getColor()!=0) {
                holder.view.mFileName.setTextColor(mContext.getResources().getColor(color));
            }
        }else if(data.getViewType().equals(Form.CHECKBOX)) {
            List<String> dataList = FieldUtils.convertArrayToList(data.getData());
            List<String> valueList = FieldUtils.convertArrayToList(data.getValue());
            holder.view.mCheckboxParent.setLayoutManager(new LinearLayoutManager(mContext));
            CheckboxAdapter adapter = new CheckboxAdapter(dataList, valueList);
            adapter.setOnCheckedListener(new CheckboxAdapter.OnCheckedListener() {
                @Override
                public void onChecked(String value,List<String> checked) {
                    data.setValue(checked);
                    try{
                        data.doCheckboxListener(value,checked);
                    }catch (Exception ignore){}
                }
            });
            holder.view.mCheckboxLabel.setText(data.getLabel());
            holder.view.mCheckboxParent.setAdapter(adapter);
        }else if(data.getViewType().equals(Form.ONETOMANY)){
            holder.view.mOnetoManyLabel.setText(data.getLabel());
            final ChildAdapter childAdapter = new ChildAdapter(data.getChildFieldModel(),mContext);
            holder.view.mOnetoManyList.setLayoutManager(new LinearLayoutManager(mContext));
            holder.view.mOnetoManyList.setAdapter(childAdapter);
            holder.view.mOnetoManyAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Add blank child to adapter
//                    Log.d("__Child Data Before ",new Gson().toJson(childAdapter.mList));
                    childAdapter.mList.add(ChildUtils.listValueRemover(childAdapter.mList));
                    childAdapter.notifyDataSetChanged();
//                    Log.d("__Child Data After ",new Gson().toJson(childAdapter.mList));
                }
            });
        }else if(data.getViewType().equals(Form.SEARCH)){
            holder.view.mSearchLabel.setText(data.getLabel());
            try {
                holder.view.mEditTextSearch = (EditText) data.doViewMod(holder.view.mEditTextSearch);
            }catch (Exception e){ }

            holder.view.mEditTextSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String value = holder.view.mEditTextSearch.getText().toString();
                    data.setValue(value);
                    //ERROR CONDITION
                    try{
                        ListenerModel listenerModel = data.doListener(value);
                        if(!listenerModel.isCondition()){
                            holder.view.mEditTextSearch.setError(listenerModel.message);
                        }
                    }catch (Exception ignored){ }
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

                @Override
                public void afterTextChanged(Editable s) { }
            });

        }else{
            holder.view.mInputTextLabel.setText(data.getLabel());
            try{
                holder.view.mEditTextSearch = (EditText) data.doViewMod(holder.view.mEditTextSearch);
            }catch (Exception ignore){ }

            if(data.getColor()!=0) {
                holder.view.mEditText.setTextColor(mContext.getResources().getColor(color));
            }
            switch (data.getViewType()) {
                case Form.PASSWORD:
                    holder.view.mEditText.setInputType(InputType.TYPE_CLASS_TEXT |
                            InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    break;
                case Form.NUMBER:
                    holder.view.mEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
                    if(data.getValue()!=null&&data.getValue().toString().equals("0.0")){
                        holder.view.mEditText.setText("0");
                        data.setValue(0);
                    } else if (data.getValue() != null) {
                        holder.view.mEditText.setText(data.getValue().toString().replace(".0",""));
                    }
                    break;
                case Form.EMAIL:
                    holder.view.mEditText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                    if (data.getValue() != null) {
                        holder.view.mEditText.setText(data.getValue().toString());
                    }
                    break;
                default:
                    //Input Type Text
                    if (data.getValue() != null && data.getValue() != "") {
                        holder.view.mEditText.setText(data.getValue().toString());
                    }
                    if (data.getError_message() != null && data.getValue() != "") {
                        holder.view.mEditText.setError(data.getError_message());
                    }
                    break;
            }

            holder.view.mEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String value = holder.view.mEditText.getText().toString();
                    data.setValue(value);
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

                @Override
                public void afterTextChanged(Editable s) { }
            });
        }
    }

    public String getResult(){
        return FieldAssembler.toJson(mList);
    }

    @Override
    public int getItemCount() {
        if(mList!=null)
            return mList.size();
        else
            return 0;
    }

    public void setOnSubmitListener(OnSubmitListener onSubmitListener){
        this.onSubmitListener = onSubmitListener;
    }

    public interface OnSubmitListener{
        void onSubmit(String response);
    }

    public void updateFile(String fieldName, Uri uri){
        UriUtils utils = new UriUtils(mContext,uri);
        for(ArisanFieldModel a:mList) {
            if(a.getName()!=null)
                if(a.getName().equals(fieldName)){
                    a.setData(utils.getFilename());
                    a.setValue(utils.getPath());
                    this.notifyDataSetChanged();
                    break;
                }
        }
    }

    public List<ArisanFieldModel> getListModel() {
        if(useSubmit){
            mList.remove(mList.size()-1);
        }
        if(useTitle){
            mList.remove(0);
        }
        return mList;
    }

    public void addModel(ArisanFieldModel add_model){
        List<ArisanFieldModel> new_model = new ArrayList<>(getListModel());
        new_model.add(add_model);
        setmList(new_model);
    }

    public void removeModel(String name){
        FieldUtils.removeField(name,mList);
    }

    public void setmList(List<ArisanFieldModel> mList) {
        this.mList = mList;
    }

    public void setSubmitBackground(int submitBackground) {
        mList.get(mList.size()-1).setBackground(submitBackground);
    }

    public void setSubmitText(String submitText) {
        this.submitText = submitText;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
