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
import android.view.MotionEvent;
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

import com.github.arisan.ArisanForm;
import com.github.arisan.R;
import com.github.arisan.annotation.ArisanCode;
import com.github.arisan.annotation.Form;
import com.github.arisan.annotation.Model;
import com.github.arisan.helper.ChildUtils;
import com.github.arisan.helper.DateConverter;
import com.github.arisan.helper.FieldAssembler;
import com.github.arisan.helper.FieldUtils;
import com.github.arisan.helper.KotlinTextUtils;
import com.github.arisan.helper.NumberUtils;
import com.github.arisan.helper.PreferenceHelper;
import com.github.arisan.helper.SortField;
import com.github.arisan.helper.UriUtils;
import com.github.arisan.model.ArisanFieldModel;
import com.github.arisan.model.ListenerModel;
import com.github.arisan.model.RadioModel;
import com.github.arisan.model.TypeForm;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import static com.github.arisan.annotation.Form.BOOLEAN;

/**
 * Created by wijaya on 3/27/2018.
 */

public class ArisanAdapter extends RecyclerView.Adapter<ArisanAdapter.ViewHolder> implements View.OnClickListener{
    private List<ArisanFieldModel> fieldList = new ArrayList<>();
    private Context mContext;
    private OnSubmitListener onSubmitListener;
    private PreferenceHelper preference;
    private String blank_message = "cannot blank!!!";
    private boolean no_blank = false;

    public String getBlank_message() {
        return blank_message;
    }

    public void setBlank_message(String blank_message) {
        this.blank_message = blank_message;
    }

    private boolean useTitle = true;
    private boolean useSubmit = true;

    public boolean isUseTitle() {
        return useTitle;
    }

    private void setUseTitle(boolean useTitle) {
        this.useTitle = useTitle;
    }

    public boolean isUseSubmit() {
        return useSubmit;
    }

    private void setUseSubmit(boolean useSubmit) {
        this.useSubmit = useSubmit;
    }

    public ArisanAdapter(Context context, ArisanForm form) {
        this.mContext = context;
        useTitle = form.isUse_title();
        useSubmit = form.isUse_submit();
        blank_message = form.getBlankMessage()==null ? blank_message:form.getBlankMessage();

        Collections.sort(form.getFieldData(),SortField.getInstance());

        preference = new PreferenceHelper(context);

        if(isUseTitle()){
            ArisanFieldModel model = new ArisanFieldModel();
            model.setName("Title");
            model.setLabel(form.getTitle());
            fieldList.add(model);//For Title
        }

        fieldList.addAll(form.getFieldData());

        if(isUseSubmit()){
            ArisanFieldModel model = new ArisanFieldModel();
            model.setName("Submit");
            model.setLabel(form.getSubmitText());
            fieldList.add(model);//For Title

            try{
                setSubmitBackground(form.getSubmit_background());
            }catch (Exception ignore){}
        }

        System.out.println("Arisan : Form Adapter Constructor");
    }

    @Override
    public ArisanAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        System.out.println("Arisan : OnCreateViewHolder Adapter");
        TypeForm type = new TypeForm();
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v;
        if(viewType==0){
            v = inflater.inflate(com.github.arisan.R.layout.item_text, parent, false);
        }else if(viewType==Model.BUTTON){
            v = inflater.inflate(com.github.arisan.R.layout.item_button, parent, false);
        }else if(viewType==type.get(BOOLEAN)){
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
        }else if(viewType== type.get(Form.PASSWORD)){
            v = inflater.inflate(R.layout.item_password, parent, false);
        }else{
            v = inflater.inflate(R.layout.item_edittext, parent, false);
        }
        return new ViewHolder(v);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        //COMMON
        MyView view;

        ViewHolder(View v) {
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
        }else if(position== fieldList.size()-1&&useSubmit) {
            return Model.BUTTON;
        }else{
            return type.get(fieldList.get(position).getViewType());
        }
    }

    @Override
    public void onBindViewHolder(final ArisanAdapter.ViewHolder holder, final int position) {
        System.out.println("Arisan : onBind Adapter");
        final ArisanFieldModel data = fieldList.get(position);
        int color = data.getColor();

        if(position==0&&useTitle){
            //TITLE
            holder.view.mTitle.setText(data.getLabel());
            if(data.getColor()!=0) {
                holder.view.mTitle.setTextColor(mContext.getResources().getColor(color));
            }
        }else if(position== fieldList.size()-1&&useSubmit){
            //SUBMIT BUTTON
            if(data.getBackground()!=0)
                try {
                    holder.view.mSubmit.setBackgroundResource(data.getSubmit_color());
                } catch (Exception ignored) { }

            holder.view.mSubmit.setText(data.getLabel());
            holder.view.mSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String json = FieldAssembler.toJson(getListField());
                    Log.d("__Result ",json);
                    try {
                        checkBlankCondition();
                    }catch (Exception e){}

                    if(no_blank){
                        holder.view.mBlankText.setVisibility(View.GONE);
                        onSubmitListener.onSubmit(json);
                    }else{
                        holder.view.mBlankText.setVisibility(View.VISIBLE);
                        holder.view.mBlankText.setText(checkBlankCondition());
                    }
                }
            });
            if(data.getColor()!=0) {
                holder.view.mSubmit.setTextColor(mContext.getResources().getColor(color));
            }
        }else if(data.getViewType().equals(BOOLEAN)) {
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
                    data.doListener(Boolean.toString(isChecked),ArisanAdapter.this);
                }
            });
            if (data.getColor() != 0) {
                holder.view.aSwitch.setTextColor(mContext.getResources().getColor(color));
            }
        }else if(data.getViewType().equals(Form.SLIDER)){
            try {
                int value = NumberUtils.doubleToInt((Double) data.getValue());
                if (value != 0)
                    holder.view.mSlide.setProgress(value);
            }catch(Exception ignore){ }

            holder.view.mSlideLabel.setText(data.getLabel());

            holder.view.mSlide.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    data.setValue(progress);
                    holder.view.mSlideValue.setText(String.valueOf(progress));
                    data.doListener(String.valueOf(progress),ArisanAdapter.this);
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
                            if (!result.equals(Model.OTHERS)) {
                                Log.d("Arisan","Show others form");
                                holder.view.mRadioText.setVisibility(View.GONE);
                            } else {
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
                            }
                            //CONDITION
                            try {
                                data.setValue(result);
                                preference.save("saved_position", String.valueOf(position));
                                data.doListener(result,ArisanAdapter.this);

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
                            String result = NumberUtils.from(dayOfMonth) + "-" + NumberUtils.from(month + 1) + "-" + year;
                            holder.view.mDate.setText(new DateConverter(result).from("dd-MM-yyyy").to(data.getDateFormat()));
                            String value = new DateConverter(result).from("dd-MM-yyyy").to(data.getDateFormat());
                            data.setValue(value);
                            data.doListener(value,ArisanAdapter.this);
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
                            String result = NumberUtils.from(hourOfDay)+":"+ NumberUtils.from(minute);
                            String convertedResult = new DateConverter(result).from("HH:mm").to(data.getDateFormat());
                            holder.view.mDate.setText(convertedResult);
                            data.setValue(convertedResult);
                            data.doListener(convertedResult,ArisanAdapter.this);
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
                            String result = NumberUtils.from(dayOfMonth) + "-" + NumberUtils.from(month + 1) + "-" + year;
                            data.setValue(new DateConverter(result).from("dd-MM-yyyy").to("dd-MM-yyyy"));
                            new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                    String result = data.getValue()+" "+ NumberUtils.from(hourOfDay)+":"+ NumberUtils.from(minute);
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
                    data.doListener(dataArray.get(pos),ArisanAdapter.this);
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
            final CheckboxAdapter adapter = new CheckboxAdapter(dataList, valueList);

            holder.view.mCheckboxText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    List<String> dataList = FieldUtils.convertArrayToList(data.getData());
                    List<String> valueList = FieldUtils.convertArrayToList(data.getValue());

                    //remove others value that is not availabel in data
                    try{
                        List<String> no_from_data = FieldUtils.convertArrayToList(data.getValue());
                        no_from_data.removeAll(dataList);
                        String edittext_value = no_from_data.size() > 0 ? no_from_data.get(0) : null; //edit text value
                        valueList.remove(edittext_value);
                    }catch(Exception ignore){}

                    String new_value = holder.view.mCheckboxText.getText().toString();

                    if(!new_value.equals(""))
                        valueList.add(new_value);

                    valueList.remove(Model.OTHERS);
                    data.setValue(valueList);
                    Log.d("__DATA",new Gson().toJson(valueList));
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            adapter.setOnCheckedListener(new CheckboxAdapter.OnCheckedListener() {
                @Override
                public void onChecked(String value,List<String> checked) {
                    data.setValue(checked);
                    try{
                        preference.save("saved_position", String.valueOf(position));
                        data.doCheckboxListener(value,checked,ArisanAdapter.this);
                    }catch (Exception ignore){}
                    if(checked.contains(Model.OTHERS)){
                        holder.view.mCheckboxText.setVisibility(View.VISIBLE);
                    }else{
                        holder.view.mCheckboxText.setVisibility(View.GONE);
                    }
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
                    childAdapter.mList.add(ChildUtils.listValueRemover(childAdapter.mList));
                    childAdapter.notifyDataSetChanged();
                }
            });
        }else if(data.getViewType().equals(Form.SEARCH)){
            holder.view.mSearchLabel.setText(data.getLabel());
            holder.view.mSearchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //ERROR CONDITION
                    try{
                        String value = holder.view.mEditTextSearch.getText().toString();
                        ListenerModel listenerModel = data.doListener(value,ArisanAdapter.this);;
                        if(!listenerModel.isCondition()){
                            holder.view.mEditTextSearch.setError(listenerModel.message);
                        }
                    }catch (Exception ignored){ }
                }
            });
            try {
                holder.view.mEditTextSearch = (EditText) data.doViewMod(holder.view.mEditTextSearch);
            }catch (Exception ignored){ }

            holder.view.mEditTextSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String value = holder.view.mEditTextSearch.getText().toString();
                    data.setValue(value);
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

                @Override
                public void afterTextChanged(Editable s) { }
            });
        }else if(data.getViewType().equals(Form.PASSWORD)){
            holder.view.mPasswordLabel.setText(data.getLabel());
            holder.view.mPassword.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String value = holder.view.mPassword.getText().toString();
                    data.setValue(value);
                    if(value.equals("")){
                        data.setValue(null);
                    }
                    try{
                        data.doListener(value,ArisanAdapter.this);
                    }catch (Exception ignore){
                        Log.e("Arisan","Listener Not Found!!!");
                    }
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

                @Override
                public void afterTextChanged(Editable s) { }
            });
            holder.view.mPassword.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    final int DRAWABLE_RIGHT = 2;

                    if(event.getAction() == MotionEvent.ACTION_UP) {
                        if(event.getRawX() >= (holder.view.mPassword.getRight() - holder.view.mPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                            // your action here
                            if(data.getData()!=null){
                                String bool = new Gson().toJson(data.getData());
                                if(bool.equals("true")){
                                    //hide password
                                    data.setData(false);
                                    holder.view.mPassword.setInputType(InputType.TYPE_CLASS_TEXT |
                                            InputType.TYPE_TEXT_VARIATION_PASSWORD);
                                    holder.view.mPassword.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_eye,0);
                                }else{
                                    //show password
                                    data.setData(true);
                                    holder.view.mPassword.setInputType(InputType.TYPE_CLASS_TEXT |
                                            InputType.TYPE_TEXT_VARIATION_NORMAL);
                                    holder.view.mPassword.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_eye_hide,0);
                                }
                            }else{
                                //show
                                data.setData(true);
                                holder.view.mPassword.setInputType(InputType.TYPE_CLASS_TEXT |
                                        InputType.TYPE_TEXT_VARIATION_NORMAL);
                                holder.view.mPassword.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_eye_hide,0);
                            }
                            return true;
                        }
                    }
                    return false;
                }
            });
            data.setData(true);

        }else{
            holder.view.mInputTextLabel.setText(data.getLabel());
            try{
                holder.view.mEditTextSearch = (EditText) data.doViewMod(holder.view.mEditTextSearch);
            }catch (Exception ignore){ }

            if(data.getColor()!=0) {
                holder.view.mEditText.setTextColor(mContext.getResources().getColor(color));
            }
            switch (data.getViewType()) {
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
                    if(value.equals("")){
                        data.setValue(null);
                    }
                    try{
                        data.doListener(value,ArisanAdapter.this);
                    }catch (Exception ignore){
                        Log.e("Arisan","Listener Not Found!!!");
                    }
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

                @Override
                public void afterTextChanged(Editable s) { }
            });
        }
    }

    public String getResult(){
        return FieldAssembler.toJson(fieldList);
    }

    @Override
    public int getItemCount() {
        if(fieldList !=null)
            return fieldList.size();
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
        for(ArisanFieldModel a: fieldList) {
            if(a.getName()!=null)
                if(a.getName().equals(fieldName)){
                    a.setData(utils.getFilename());
                    a.setValue(utils.getPath());
                    this.notifyDataSetChanged();
                    break;
                }
        }
    }

    public List<ArisanFieldModel> getData() {
        return fieldList;
    }

    public List<ArisanFieldModel> getListField() {
        List<ArisanFieldModel> models = new ArrayList<>();
        for(ArisanFieldModel model:fieldList){
            models.add(model.renew());
        }

        if(useSubmit){
            models.remove(models.size()-1);
        }
        if(useTitle){
            models.remove(0);
        }
        return models;
    }

    public void addModel(ArisanFieldModel add_model){
        List<ArisanFieldModel> new_list = new ArrayList<>(getListField());
        new_list.add(add_model);
        Collections.sort(new_list,SortField.getInstance());

        if(useTitle){
            ArisanFieldModel title_model = fieldList.get(0);
            new_list.add(0,title_model);
        }

        if(useSubmit){
            ArisanFieldModel submit_model = fieldList.get(fieldList.size()-1);
            new_list.add(submit_model);
        }

        setFieldList(new_list);
        notifyDataSetChanged();
    }

    public void removeModel(String name){
        FieldUtils.removeField(name, fieldList);
        notifyDataSetChanged();
    }

    private void setFieldList(List<ArisanFieldModel> fieldList) {
        this.fieldList = fieldList;
    }

    public void setSubmitBackground(int submitBackground) {
        fieldList.get(fieldList.size()-1).setBackground(submitBackground);
    }

    private String checkBlankCondition(){
        StringBuilder blank = new StringBuilder();
        List<ArisanFieldModel> models = getListField();
        int blank_value = 0;
        List<String> arr_string = new ArrayList<>();
        for(ArisanFieldModel model:models){
            if(model.isRequire()){
                if(model.getValue()==null){
                    arr_string.add(model.getLabel());
                    blank_value++;
                }
            }
        }
        no_blank = blank_value == 0;
        blank.append(new KotlinTextUtils().join(arr_string));
        blank.append(" ").append(blank_message);
        return blank.toString();
    }
}
