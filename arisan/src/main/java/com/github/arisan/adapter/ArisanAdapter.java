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
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

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
import com.github.arisan.model.TypeForm;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Arrays;
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

    boolean useTitle;
    boolean useSubmit;

    public void setSubmitBackground(int submitBackground) {
        mList.get(mList.size()-1).setBackground(submitBackground);
    }

    public void setSubmitText(String submitText) {
        this.submitText = submitText;
    }

    public void setTitle(String title) {
        this.title = title;
    }

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
        }else if(viewType==mList.size()){
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
        TextView mTitle;
        Button mSubmit;
        //TEXT
        TextView mInputTextLabel;
        TextView mDateLabel;
        TextView mSpinnerLabel;
        EditText mEditText;
        //SEARCH
        EditText mEditTextSearch;
        TextView mSearchLabel;
        Button mSearchButton;
        //BOOLEAN
        Switch aSwitch;
        //SPINNER
        Spinner mSpinner;
        //FILE
        Button mFile;
        TextView mFileName;
        TextView mFileLabel;
        //DATETIME
        Button mDate;
        //CHECKBOX
        RecyclerView mCheckboxParent;
        //ONETOMANY
        RecyclerView mOnetoManyList;
        TextView mOnetoManyLabel;
        Button mOnetoManyAdd;
        //RADIO BUTTON
        RadioGroup mRadioGroup;
        TextView mRadioLabel;
        EditText mRadioText;
        //SLIDER
        TextView mSlideLabel;
        SeekBar mSlide;

        public ViewHolder(View v) {
            super(v);
            mInputTextLabel = v.findViewById(com.github.arisan.R.id.arisan_text_label);
            mEditText = v.findViewById(com.github.arisan.R.id.arisan_text);
            mDateLabel = v.findViewById(R.id.arisan_date_label);
            mDate = v.findViewById(R.id.arisan_date);
            mSpinnerLabel = v.findViewById(R.id.arisan_spinner_label);
            mSpinner = v.findViewById(R.id.arisan_spinner);
            mTitle = v.findViewById(com.github.arisan.R.id.arisan_title);
            mSubmit = v.findViewById(com.github.arisan.R.id.arisan_button);
            aSwitch = v.findViewById(com.github.arisan.R.id.arisan_switch);
            mFile = v.findViewById(R.id.arisan_upload);
            mFileLabel = v.findViewById(R.id.arisan_file_label);
            mFileName = v.findViewById(R.id.arisan_file_name);
            mCheckboxParent = v.findViewById(R.id.arisan_checkbox_list);
            mEditTextSearch = v.findViewById(R.id.arisan_search_name);
            mSearchButton = v.findViewById(R.id.arisan_search_button);
            mSearchLabel = v.findViewById(R.id.arisan_search_label);
            mOnetoManyAdd = v.findViewById(R.id.arisan_onetomany_add);
            mOnetoManyLabel = v.findViewById(R.id.arisan_onetomany_label);
            mOnetoManyList = v.findViewById(R.id.arisan_onetomany_list);
            mRadioGroup = v.findViewById(R.id.arisan_radio);
            mRadioLabel = v.findViewById(R.id.arisan_radio_label);
            mSlideLabel = v.findViewById(R.id.arisan_slide_label);
            mSlide = v.findViewById(R.id.arisan_slide);
            mRadioText = v.findViewById(R.id.arisan_radio_others);
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
            return mList.size();
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
            holder.mTitle.setText(title);
            if(data.getColor()!=0) {
                holder.mTitle.setTextColor(mContext.getResources().getColor(color));
            }
        }else if(position==mList.size()-1&&useSubmit){
            //SUBMIT BUTTON
            if(data.getSubmit_color()!=0){
                holder.mSubmit.setBackgroundResource(data.getSubmit_color());
            }
            holder.mSubmit.setText(submitText);
            holder.mSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (useTitle) mList.remove(0);
                    if (useSubmit) mList.remove(mList.size()-1);
                    String json = FieldAssembler.toJson(mList);
                    Log.d("__Result ",json);
                    onSubmitListener.onSubmit(json);
                }
            });
            if(data.getColor()!=0) {
                holder.mSubmit.setTextColor(mContext.getResources().getColor(color));
            }
        }else if(data.getViewType().equals(Form.BOOLEAN)) {
            holder.aSwitch.setText(data.getLabel());
            if (data.getValue() != null) {
                if (data.getValue().toString().equals("true")) {
                    holder.aSwitch.setChecked(true);
                    mList.get(position).setValue(true);
                } else {
                    holder.aSwitch.setChecked(false);
                    mList.get(position).setValue(false);
                }
            } else {
                holder.aSwitch.setChecked(false);
            }
            holder.aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    mList.get(position).setValue(isChecked);
                }
            });
            if (data.getColor() != 0) {
                holder.aSwitch.setTextColor(mContext.getResources().getColor(color));
            }
        }else if(data.getViewType().equals(Form.SLIDER)){
            Double dbl = (Double) data.getValue();
            int value = (int) Math.round(dbl);

            holder.mSlideLabel.setText(data.getLabel());

            if(value!=0)
                holder.mSlide.setProgress(value);

            holder.mSlide.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    mList.get(position).setValue(progress);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
        }else if(data.getViewType().equals(Form.RADIO)){
            String value = (String) data.getValue();
            List<String> dataList = FieldUtils.convertArrayToList(data.getData());
            for(String mData:dataList){
                RadioButton btn = new RadioButton(mContext);
                btn.setId(View.generateViewId());
                btn.setText(mData);
                if(mData.equals(value)){
                    btn.setChecked(true);
                }
                holder.mRadioGroup.addView(btn);
            }
            holder.mRadioLabel.setText(data.getLabel());
            holder.mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    //ERROR CONDITION
                    RadioButton radioButton = holder.mRadioGroup.findViewById(checkedId);
                    try{
                        mList.get(position).setValue(radioButton.getText().toString());
                        ListenerModel listenerModel = mList.get(position).doListener(radioButton.getText().toString());
                    }catch (Exception ignored){ }
                    //OTHER CONDITION
                    if(radioButton.getText().toString().equals(Model.OTHERS)){
                        holder.mRadioText.setVisibility(View.VISIBLE);
                        mList.get(position).setValue(radioButton.getText().toString());
                        holder.mRadioText.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                String value = holder.mRadioText.getText().toString();
                                mList.get(position).setValue(value);
                            }

                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

                            @Override
                            public void afterTextChanged(Editable s) { }
                        });
                    }else{
                        holder.mRadioText.setVisibility(View.GONE);
                    }
                }
            });

        }else if(data.getViewType().equals(Form.DATE)) {
            holder.mDateLabel.setText(data.getLabel());
            final Calendar calendar;
            if (data.getValue() != null) {
                calendar = new DateConverter(data.getValue().toString()).from("MMMM dd, yyyy").calendar;
                holder.mDate.setText(new DateConverter(calendar).to(data.getDateFormat()));
            } else {
                calendar = Calendar.getInstance();
                holder.mDate.setText(new DateConverter(calendar).to(data.getDateFormat()));
            }
            holder.mDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            String result = TwoDigit.from(dayOfMonth) + "-" + TwoDigit.from(month + 1) + "-" + year;
                            holder.mDate.setText(new DateConverter(result).from("dd-MM-yyyy").to(data.getDateFormat()));
                            mList.get(position).setValue(new DateConverter(result).from("dd-MM-yyyy").to(data.getDateFormat()));
                        }
                    }, calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            });
            if(data.getBackground()!=0){
                holder.mDate.setBackgroundResource(data.getBackground());
            }
            if(data.getColor()!=0) {
                holder.mDate.setTextColor(mContext.getResources().getColor(color));
            }
        }else if(data.getViewType().equals(Form.TIME)){
            final Calendar calendar;
            if (data.getValue() != null) {
                calendar = new DateConverter(data.getValue().toString()).from("MMMM dd, yyyy HH:mm:ss").calendar;
                holder.mDate.setText(new DateConverter(calendar).to(data.getDateFormat()));
            } else {
                calendar = Calendar.getInstance();
                holder.mDate.setText("12:34");
            }
            holder.mDateLabel.setText(data.getLabel());
            holder.mDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            String result = TwoDigit.from(hourOfDay)+":"+TwoDigit.from(minute);
                            String convertedResult = new DateConverter(result).from("HH:mm").to(data.getDateFormat());
                            holder.mDate.setText(convertedResult);
                            mList.get(position).setValue(convertedResult);
                        }
                    },calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),DateFormat.is24HourFormat(mContext)).show();
                }
            });
            if(data.getBackground()!=0){
                holder.mDate.setBackgroundResource(data.getBackground());
            }
            if(data.getColor()!=0) {
                holder.mDate.setTextColor(mContext.getResources().getColor(color));
            }
        }else if(data.getViewType().equals(Form.DATETIME)) {
            holder.mDateLabel.setText(data.getLabel());
            final Calendar calendar;
            if (data.getValue() != null) {
                calendar = new DateConverter(data.getValue().toString()).from("MMMM dd, yyyy").calendar;
                holder.mDate.setText(new DateConverter(calendar).to(data.getDateFormat()));
            } else {
                calendar = Calendar.getInstance();
                holder.mDate.setText(new DateConverter(calendar).to(data.getDateFormat()));
            }
            String result = "";
            holder.mDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            String result = TwoDigit.from(dayOfMonth) + "-" + TwoDigit.from(month + 1) + "-" + year;
                            mList.get(position).setValue(new DateConverter(result).from("dd-MM-yyyy").to("dd-MM-yyyy"));
                            new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                    String result = mList.get(position).getValue()+" "+TwoDigit.from(hourOfDay)+":"+TwoDigit.from(minute);
                                    String convertedResult = new DateConverter(result).from("dd-MM-yyyy HH:mm").to(data.getDateFormat());
                                    holder.mDate.setText(convertedResult);
                                    mList.get(position).setValue(convertedResult);
                                }
                            },calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),DateFormat.is24HourFormat(mContext)).show();
                        }
                    }, calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            });
            if(data.getBackground()!=0){
                holder.mDate.setBackgroundResource(data.getBackground());
            }
            if(data.getColor()!=0) {
                holder.mDate.setTextColor(mContext.getResources().getColor(color));
            }
        }else if(data.getViewType().equals(Form.SPINNER)){
            holder.mSpinnerLabel.setText(data.getLabel());
            //ArrayAdapter mAdapter = new ArrayAdapter(mContext,android.R.layout.simple_spinner_item,(List)data.getData());
            final ArrayList<String> dataArray = (ArrayList<String>) data.getData();
            ArrayAdapter mAdapter = new ArrayAdapter<>(mContext,android.R.layout.simple_spinner_item,dataArray);
            holder.mSpinner.setAdapter(mAdapter);
            holder.mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    mList.get(position).setValue(dataArray.get(pos));
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            if(data.getValue() != null){
                holder.mSpinner.setSelection(dataArray.indexOf(data.getValue()));
            }
        }else if(data.getViewType().equals(Form.FILE)){
            holder.mFileLabel.setText(data.getLabel());
            if(data.getData()!=null)
                holder.mFileName.setText((String)data.getData());
            holder.mFile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("file/*");
                    ((Activity)mContext).startActivityForResult(intent, ArisanCode.REQUEST_FILE);
                }
            });
            if(data.getBackground()!=0){
                holder.mFileName.setBackgroundResource(data.getBackground());
            }
            if(data.getColor()!=0) {
                holder.mFileName.setTextColor(mContext.getResources().getColor(color));
            }
        }else if(data.getViewType().equals(Form.CHECKBOX)) {
            List<String> dataList = FieldUtils.convertArrayToList(data.getData());
            List<String> valueList = FieldUtils.convertArrayToList(data.getValue());
            holder.mCheckboxParent.setLayoutManager(new LinearLayoutManager(mContext));
            CheckboxAdapter adapter = new CheckboxAdapter(dataList, valueList);
            adapter.setOnCheckedListener(new CheckboxAdapter.OnCheckedListener() {
                @Override
                public void onChecked(List<String> checked) {
                    mList.get(position).setValue(checked);
                }
            });
            holder.mCheckboxParent.setAdapter(adapter);
        }else if(data.getViewType().equals(Form.ONETOMANY)){
            holder.mOnetoManyLabel.setText(mList.get(position).getLabel());
            final ChildAdapter childAdapter = new ChildAdapter(mList.get(position).getChildFieldModel(),mContext);
            holder.mOnetoManyList.setLayoutManager(new LinearLayoutManager(mContext));
            holder.mOnetoManyList.setAdapter(childAdapter);
            holder.mOnetoManyAdd.setOnClickListener(new View.OnClickListener() {
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
            holder.mSearchLabel.setText(data.getLabel());
            try {
                holder.mEditTextSearch = (EditText) mList.get(position).doViewMod(holder.mEditTextSearch);
            }catch (Exception e){ }

            holder.mEditTextSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String value = holder.mEditTextSearch.getText().toString();
                    mList.get(position).setValue(value);
                    //ERROR CONDITION
                    try{
                        ListenerModel listenerModel = mList.get(position).doListener(value);
                        if(!listenerModel.isCondition()){
                            holder.mEditTextSearch.setError(listenerModel.message);
                        }
                    }catch (Exception ignored){ }
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

                @Override
                public void afterTextChanged(Editable s) { }
            });

        }else{
            //Edit Text
            holder.mInputTextLabel.setText(data.getLabel());
            try{
                holder.mEditTextSearch = (EditText) mList.get(position).doViewMod(holder.mEditTextSearch);
            }catch (Exception e){ }

//            if(data.getBackground()!= 0) {
//                holder.mInputTextLabel.setTextColor(mContext.getResources().getColor(data.getBackground()));
//            }
            if(data.getColor()!=0) {
                holder.mEditText.setTextColor(mContext.getResources().getColor(color));
            }
            switch (data.getViewType()) {
                case Form.PASSWORD:
                    holder.mEditText.setInputType(InputType.TYPE_CLASS_TEXT |
                            InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    break;
                case Form.NUMBER:
                    holder.mEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
                    if(data.getValue()!=null&&data.getValue().toString().equals("0.0")){
                        holder.mEditText.setText("0");
                        mList.get(position).setValue(0);
                    } else if (data.getValue() != null) {
                        holder.mEditText.setText(data.getValue().toString().replace(".0",""));
                    }
                    break;
                case Form.EMAIL:
                    holder.mEditText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                    if (data.getValue() != null) {
                        holder.mEditText.setText(data.getValue().toString());
                    }
                    break;
                default:
                    //Input Type Text
                    if (data.getValue() != null && data.getValue() != "") {
                        holder.mEditText.setText(data.getValue().toString());
                    }
                    if (data.getError_message() != null && data.getValue() != "") {
                        holder.mEditText.setError(data.getError_message());
                    }
                    break;
            }

            holder.mEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String value = holder.mEditText.getText().toString();
                    mList.get(position).setValue(value);
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

    public void setmList(List<ArisanFieldModel> mList) {
        this.mList = mList;
    }
}
