package com.github.arisan.adapter;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.AttributeSet;
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
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TimePicker;
import android.widget.Toast;

import com.github.arisan.ArisanListener;
import com.github.arisan.FormConfig;
import com.github.arisan.ImageDialog;
import com.github.arisan.R;
import com.github.arisan.annotation.ArisanCode;
import com.github.arisan.annotation.Form;
import com.github.arisan.annotation.Model;
import com.github.arisan.helper.DateConverter;
import com.github.arisan.helper.FieldAssembler;
import com.github.arisan.helper.FieldUtils;
import com.github.arisan.helper.ImagePickerUtils;
import com.github.arisan.helper.KotlinFilter;
import com.github.arisan.helper.NumberUtils;
import com.github.arisan.helper.ObjectReader;
import com.github.arisan.helper.PreferenceHelper;
import com.github.arisan.helper.SortForm;
import com.github.arisan.helper.TextUtils;
import com.github.arisan.helper.UriUtils;
import com.github.arisan.model.ArisanCustomForm;
import com.github.arisan.model.FormModel;
import com.github.arisan.model.FormViewHolder;
import com.github.arisan.model.RadioModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class FormAdapter extends LinearLayout {
    //MAIN DATA
    private List<FormModel> fieldModels = new ArrayList<>();
    private List<FormViewHolder> holderList = new ArrayList<>();

    private List<ArisanCustomForm> customForms = new ArrayList<>();

    private OnSubmitListener onSubmitListener;
    private PreferenceHelper preference;
    private String blank_message = "cannot blank!!!";
    private boolean no_blank = false;
    private FormConfig config = new FormConfig();
    private String parent_field_name;
    private ArisanListener.ProgressListener progressListener;

    //Check wheter buildForm is done
    boolean is_build_done = false;
    int builded_form = 0;
    int spinner_check = 0;

    public static ViewGroup.LayoutParams LAYOUT_PARAMS = new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    ViewGroup parent_view;
    LinearLayout container_view;
    View child_view;

    void init(){
        //WRITE CODE HERE
        preference = new PreferenceHelper(getContext());
    }

    public void processData(){
        if(config.isChild) {
            LayoutInflater inflater = LayoutInflater.from(parent_view.getContext());
            View v = inflater.inflate(R.layout.item_child_many, parent_view, false);
            child_view = v;
            parent_view.addView(child_view);
            container_view = v.findViewById(R.id.arisan_item_child_many);
        }else{
            container_view = new LinearLayout(getContext());
            container_view.setOrientation(LinearLayout.VERTICAL);
            container_view.setLayoutParams(LAYOUT_PARAMS);
            parent_view.addView(container_view);
        }

        Log.d("_PROCESS","PROCESS DATA");
        for(FormModel data:fieldModels){
            int position = fieldModels.indexOf(data);
            //inflate
            final View view;

            view = MyInflater.inflate(container_view,data,config);

            container_view.addView(view);
            //final Animation anim =
            //Save all data
            FormViewHolder holder = new FormViewHolder(view);
            holder.setData(data);
            holder.setPosition(position);
            holderList.add(holder);
            //notify
            notifyData(holder);
            builded_form++;
        }
        if(builded_form == fieldModels.size()){
            Log.d(">>>Building","Done");
            is_build_done = true;
        }
        Log.d("__PROCESS","INFLATE ALL SUCCESS");
    }

    /*========== @ADDITIONAL LOGIC===========*/

    public String getResult(){
        return FieldAssembler.toFormJson(fieldModels);
    }

    public void notifyData(@NonNull FormViewHolder holder){
        FormModel data = holder.getData();
        int position = holder.getPosition();
        if(position==0&&config.useTitle){
            ViewTitle(holder);
        }else if(holder.getPosition()== fieldModels.size()-1&&config.useSubmit){
            if(config.isChild)
                ViewDelete(holder);
            else
                ViewSubmit(holder);
        }else {
            switch (data.getViewType()) {
                case Form.BOOLEAN: ViewBoolean(holder);break;
                case Form.SLIDER: ViewSlider(holder);break;
                case Form.RADIO: ViewRadio(holder);break;
                case Form.DATE: ViewDate(holder);break;
                case Form.TIME: ViewTime(holder);break;
                case Form.DATETIME: ViewDateTime(holder);break;
                case Form.SPINNER: ViewSpinner(holder);break;
                case Form.FILE: ViewFile(holder);break;
                case Form.CHECKBOX: ViewCheckbox(holder);break;
                case Form.ONETOMANY: ViewOneToMany(holder);break;
                case Form.SEARCH: ViewSearch(holder);break;
                case Form.IMAGE: ViewImage(holder);break;
                case Form.AUTOCOMPLETE: ViewAutocomplete(holder);break;
                case Form.PASSWORD: ViewPassword(holder);break;
                case Form.ONELINETEXT: ViewOneLineText(holder);break;
                case Form.FLOWTEXT: ViewEditText2(holder);break;
                case Form.CUSTOM: holder.data.getCustomForm().onCreated(holder,this);break; //TODO: onCreted here
                default: ViewEditText(holder);break;
            }
        }
    }

    public void explode(){
        if(config.isChild)
            parent_view.removeView(child_view);
        else
            parent_view.removeView(container_view);

        fieldModels.clear();
        holderList.clear();
    }

    public void update(String name,Object data){

    }

    public void updateChild(String parent_name,String name,int index,Object data){

    }

    /*=================VIEW CONDITION=====================*/

    private void ViewEditText(final FormViewHolder holder) {
        holder.view.mEditTextLabel.setText(holder.data.getLabel());
        holder.view.mEditText.setHint(holder.data.getHint());
        if(config.labelColor!=0) holder.view.mEditTextLabel.setTextColor(getContext().getResources().getColor(config.labelColor));

        holder.view.mEditText.addTextChangedListener(new MyTextWatcher(holder.data));
        switch (holder.data.getViewType()) {
            case Form.NUMBER: {
//                Log.d("__TYPE","NUMBER");
                holder.view.mEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
                if (holder.data.getValue() != null && holder.data.getValue().toString().equals("0.0")) {
                    holder.view.mEditText.setText("0");
                    holder.data.setValue(0);
                } else if (holder.data.getValue() != null) {
                    holder.view.mEditText.setText(holder.data.getValue().toString().replace(".0", ""));
                }
            }
            break;
            case Form.EMAIL: {
//                Log.d("__TYPE","EMAIL");
                holder.view.mEditText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                if (holder.data.getValue() != null) {
                    holder.view.mEditText.setText(holder.data.getValue().toString());
                }
            }
            break;
            default: {
//                Log.d("__TYPE","NORMAL");
                //Input Type Text
                if (holder.data.getValue() != null) {
                    //Log.d("__DATA",new Gson().toJson(holder.data.getValue()));
                    holder.view.mEditText.setText(holder.data.getValue().toString());
                }

                if (holder.data.getError_message() != null && holder.data.getValue() != "") {
                    holder.view.mEditText.setError(holder.data.getError_message());
                }
            }break;
        }
    }

    private void ViewEditText2(final FormViewHolder holder) {
        holder.view.mEditText2Layout.setHint(holder.data.getLabel());

        Log.d("__TYPE", String.valueOf(holder.data.getViewType()));
        //Input Type Text
        if (holder.data.getValue() != null) {
            //Log.d("__DATA",new Gson().toJson(holder.data.getValue()));
            holder.view.mEditText2.setText(holder.data.getValue().toString());
        }

        if (holder.data.getError_message() != null && holder.data.getValue() != "") {
            holder.view.mEditText2.setError(holder.data.getError_message());
        }

        holder.view.mEditText2.addTextChangedListener(new MyTextWatcher(holder.data));
        if(holder.data.isError()) holder.view.mEditText2.setError(holder.data.getError_message());
        if(holder.data.getHint()!=null) holder.view.mEditText2.setHint(holder.data.getHint());

        if(config.labelColor!=0) holder.view.mEditText2.setHintTextColor(getContext().getResources().getColor(config.labelColor));
    }

    private void ViewOneLineText(final FormViewHolder holder) {
        holder.view.mOneLineText.setHint(holder.data.getLabel());
        //Log.d("__TYPE", String.valueOf(holder.data.getViewType()));
        //Input Type Text
        if (holder.data.getValue() != null) {
            //Log.d("__DATA",new Gson().toJson(holder.data.getValue()));
            holder.view.mOneLineText.setText(holder.data.getValue().toString());
        }

        if (holder.data.getError_message() != null && holder.data.getValue() != "") {
            holder.view.mOneLineText.setError(holder.data.getError_message());
        }

        holder.view.mOneLineText.addTextChangedListener(new MyTextWatcher(holder.data));
        if(holder.data.isError()) holder.view.mOneLineText.setError(holder.data.getError_message());
        if(holder.data.getHint()!=null) holder.view.mOneLineText.setHint(holder.data.getHint());

//        if(config.labelColor!=0) holder.view.mOneLineText.setHintTextColor(getContext().getResources().getColor(config.labelColor));
    }

    private void ViewPassword(final FormViewHolder holder) {
        holder.view.mPasswordLabel.setText(holder.data.getLabel());
        holder.view.mPassword.addTextChangedListener(new MyTextWatcher(holder.data));
        holder.view.mPassword.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (holder.view.mPassword.getRight() - holder.view.mPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        if(holder.data.getData()!=null){
                            String bool = new Gson().toJson(holder.data.getData());
                            if(bool.equals("true")){
                                //hide password
                                holder.data.setData(false);
                                holder.view.mPassword.setInputType(InputType.TYPE_CLASS_TEXT |
                                        InputType.TYPE_TEXT_VARIATION_PASSWORD);
                                holder.view.mPassword.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_eye,0);
                            }else{
                                //show password
                                holder.data.setData(true);
                                holder.view.mPassword.setInputType(InputType.TYPE_CLASS_TEXT |
                                        InputType.TYPE_TEXT_VARIATION_NORMAL);
                                holder.view.mPassword.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_eye_hide,0);
                            }
                        }else{
                            //show
                            holder.data.setData(true);
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
        holder.data.setData(true);

        if(config.labelColor!=0) holder.view.mPasswordLabel.setTextColor(getContext().getResources().getColor(config.labelColor));
    }

    private void ViewAutocomplete(final FormViewHolder holder) {
        holder.view.mAutocompleteLabel.setText(holder.data.getLabel());
        final List<String> options = FieldUtils.convertArrayToList(holder.data.getData());
        AutocompleteAdapter adapter = new AutocompleteAdapter(getContext(), android.R.layout.select_dialog_item, options);
        holder.view.mAutocomplete.setThreshold(1); //will start working from first character
        holder.view.mAutocomplete.setAdapter(adapter);
        holder.view.mAutocomplete.addTextChangedListener(new MyTextWatcher(holder.data));

        holder.view.mAutocomplete.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                holder.data.setValue(options.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if(holder.data.getHint()!=null) holder.view.mAutocomplete.setHint(holder.data.getHint());
        if(holder.data.isError()) holder.view.mAutocomplete.setError(holder.data.getError_message());

        if(holder.data.getValue()!=null) holder.view.mAutocomplete.setText(holder.data.getValue().toString());
        if(config.labelColor!=0) holder.view.mAutocompleteLabel.setTextColor(getContext().getResources().getColor(config.labelColor));
    }

    private void ViewImage(final FormViewHolder holder) {
        holder.view.mImageLabel.setText(holder.data.getLabel());
        holder.view.mImagePick.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageDialog.build(getContext(), new ImageDialog.OnImageDialog() {
                    @Override
                    public void selected(boolean gallery) {
                        ImagePickerUtils imagePickerUtils = new ImagePickerUtils(getContext(),holder.data);
                        if(config.isChild) {
                            imagePickerUtils.setChild(config.isChild);
                            imagePickerUtils.setChild_position(config.index_child);
                            imagePickerUtils.setParent_name(parent_field_name);
                        }

                        if(gallery) imagePickerUtils.pickFromGallery();
                        else imagePickerUtils.pickFromCamera();
                    }
                });
            }
        });

        if(holder.data.getThumbnail()!=null) holder.view.mImage.setImageBitmap(holder.data.getThumbnail());
        if(holder.data.getData()!=null) holder.view.mImageName.setText(holder.data.getData().toString());

        if(config.buttonBackground !=0)holder.view.mImagePick.setBackgroundResource(config.buttonBackground);
        if(config.buttonTextColor !=0) holder.view.mImagePick.setTextColor(getContext().getResources().getColor(config.buttonTextColor));
        if(config.labelColor!=0) holder.view.mImageLabel.setTextColor(getContext().getResources().getColor(config.labelColor));
    }

    private void ViewSearch(final FormViewHolder holder) {
        holder.view.mSearchLabel.setText(holder.data.getLabel());
        holder.view.mSearchButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //ERROR CONDITION
                try{
                    String value = holder.view.mEditTextSearch.getText().toString();
                    holder.data.doListener(value,FormAdapter.this);
                    //TODO: Change condition listener
//                    if(!listenerModel.isCondition()){
//                        holder.view.mEditTextSearch.setError(listenerModel.message,getContext().getResources().getDrawable(R.drawable.ic_clear));
//                    }
                }catch (Exception ignored){ }
            }
        });
        try {
            holder.view.mEditTextSearch = (EditText) holder.data.doViewMod(holder.view.mEditTextSearch);
        }catch (Exception ignored){ }

        holder.view.mEditTextSearch.addTextChangedListener(new MyTextWatcher(holder.data));

        if(holder.isWait()) {
            holder.view.mSearchLoading.setVisibility(VISIBLE);
            holder.view.mSearchIcon.setVisibility(GONE);
        } else{
            holder.view.mSearchLoading.setVisibility(GONE);
            holder.view.mSearchIcon.setVisibility(VISIBLE);
        }

        if(holder.data.getValue()!=null) holder.view.mEditTextSearch.setText(holder.data.getValue().toString());
        if(holder.data.getHint()!=null) holder.view.mEditTextSearch.setHint(holder.data.getHint());
        if(holder.data.isError()) holder.view.mEditTextSearch.setError(holder.data.getError_message());

        //Configuration like color
        if(config.labelColor!=0) holder.view.mSearchLabel.setTextColor(getContext().getResources().getColor(config.labelColor));
        if(config.buttonTextColor!=0) holder.view.mSearchIcon.setColorFilter(getContext().getResources().getColor(config.buttonTextColor));
        if(config.buttonBackground !=0)holder.view.mSearchButton.setBackgroundResource(config.buttonBackground);
    }

    private void ViewOneToMany(FormViewHolder holder) {
        holder.view.vManyLabel.setText(holder.data.getLabel());

        final OneToManyAdapter oneToManyAdapter = new OneToManyAdapter(holder.view.vManyContainer);
        oneToManyAdapter.setParent_field_name(holder.data.getName());
        oneToManyAdapter.setConfig(config);
        oneToManyAdapter.setList(holder.data.getChildFieldModel());
        oneToManyAdapter.processData();
        holder.setChildForm(oneToManyAdapter.getListForm());

        holder.setAdapter(oneToManyAdapter);

        holder.view.vManyAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add blank child to adapter
                oneToManyAdapter.addNewData();
            }
        });

        if(config.buttonBackground !=0)holder.view.vManyAdd.setBackgroundResource(config.buttonBackground);
        if(config.labelColor!=0) holder.view.vManyLabel.setTextColor(getContext().getResources().getColor(config.labelColor));
    }

    private void ViewCheckbox(final FormViewHolder holder) {
        List<String> options = FieldUtils.convertArrayToList(holder.data.getData());
        List<String> valueList = FieldUtils.convertArrayToList(holder.data.getValue());
        holder.view.mCheckboxParent.setLayoutManager(new LinearLayoutManager(getContext()));
        final CheckboxAdapter adapter = new CheckboxAdapter(options, valueList);

        holder.view.mCheckboxText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<String> options = FieldUtils.convertArrayToList(holder.data.getData());
                List<String> valueList = FieldUtils.convertArrayToList(holder.data.getValue());

                //remove others value that is not availabel in holder.data
                try{
                    List<String> no_from_data = FieldUtils.convertArrayToList(holder.data.getValue());
                    no_from_data.removeAll(options);
                    String edittext_value = no_from_data.size() > 0 ? no_from_data.get(0) : null; //edit text value
                    valueList.remove(edittext_value);
                }catch(Exception ignore){}

                String new_value = holder.view.mCheckboxText.getText().toString();

                if(!new_value.equals(""))
                    valueList.add(new_value);

                valueList.remove(Model.OTHERS);
                holder.data.setValue(valueList);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        adapter.setOnCheckedListener(new CheckboxAdapter.OnCheckedListener() {
            @Override
            public void onChecked(String value,List<String> checked) {
                holder.data.setValue(checked);
                if(checked.contains(Model.OTHERS)){
                    holder.view.mCheckboxText.setVisibility(View.VISIBLE);
                }else{
                    holder.view.mCheckboxText.setVisibility(View.GONE);
                }
            }
        });
        holder.view.mCheckboxLabel.setText(holder.data.getLabel());
        holder.view.mCheckboxParent.setAdapter(adapter);

        if(config.labelColor!=0) holder.view.mCheckboxLabel.setTextColor(getContext().getResources().getColor(config.labelColor));
    }

    private void ViewFile(FormViewHolder holder) {
        holder.view.mFileLabel.setText(holder.data.getLabel());

        if(holder.data.getData()!=null)
            holder.view.mFileName.setText((String)holder.data.getData());
        holder.view.mFile.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("file/*");
                ((Activity)getContext()).startActivityForResult(intent, ArisanCode.REQUEST_FILE);
            }
        });

        if(config.buttonBackground !=0)holder.view.mFile.setBackgroundResource(config.buttonBackground);
        if(config.labelColor!=0) holder.view.mFileLabel.setTextColor(getContext().getResources().getColor(config.labelColor));
    }

    private void ViewSpinner(final FormViewHolder holder) {
        holder.view.mSpinnerLabel.setText(holder.data.getLabel());
        //ArrayAdapter mAdapter = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,(List)holder.data.getData());
        final List<String> options = FieldUtils.convertArrayToList(holder.data.getData());
        ArrayAdapter<String> mAdapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_dropdown_item,options);
        holder.view.mSpinner.setAdapter(mAdapter);
        holder.view.mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                holder.data.setValue(options.get(pos));
                if(++spinner_check > 1){
                    holder.data.doListener(options.get(pos),FormAdapter.this);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        if(holder.data.getValue() != null){
            holder.view.mSpinner.setSelection(options.indexOf(holder.data.getValue()));
        }

        if(config.labelColor!=0) holder.view.mSpinnerLabel.setTextColor(getContext().getResources().getColor(config.labelColor));
    }

    private void ViewDateTime(final FormViewHolder holder) {
        holder.view.mDateLabel.setText(holder.data.getLabel());
        final Calendar calendar;
        if (holder.data.getValue() != null) {
            if(holder.data.getDateFormat() ==null)
                calendar = new DateConverter(holder.data.getValue().toString()).from("MMMM dd, yyyy").calendar;
            else
                calendar = new DateConverter(holder.data.getValue().toString()).from(holder.data.getDateFormat()).calendar;
            holder.view.mDate.setText(new DateConverter(calendar).to(holder.data.getDateFormat()));
        } else {
            calendar = Calendar.getInstance();
            holder.view.mDate.setText(new DateConverter(calendar).to(holder.data.getDateFormat()));
        }
        String result = "";
        holder.view.mDate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String result = NumberUtils.from(dayOfMonth) + "-" + NumberUtils.from(month + 1) + "-" + year;
                        holder.data.setValue(new DateConverter(result).from("dd-MM-yyyy").to("dd-MM-yyyy"));
                        new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                String result = holder.data.getValue()+" "+ NumberUtils.from(hourOfDay)+":"+ NumberUtils.from(minute);
                                String convertedResult = new DateConverter(result).from("dd-MM-yyyy HH:mm").to(holder.data.getDateFormat());
                                holder.view.mDate.setText(convertedResult);
                                holder.data.setValue(convertedResult);
                            }
                        },calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE), DateFormat.is24HourFormat(getContext())).show();
                    }
                }, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        if(config.buttonBackground !=0)holder.view.mDate.setBackgroundResource(config.buttonBackground);
        if(config.textColor !=0) holder.view.mDate.setTextColor(getContext().getResources().getColor(config.textColor));
        if(config.labelColor!=0) holder.view.mDateLabel.setTextColor(getContext().getResources().getColor(config.labelColor));
    }

    private void ViewTime(final FormViewHolder holder) {
        final Calendar calendar;
        if (holder.data.getValue() != null) {
            if(holder.data.getDateFormat() ==null)
                calendar = new DateConverter(holder.data.getValue().toString()).from("MMMM dd, yyyy HH:mm:ss").calendar;
            else
                calendar = new DateConverter(holder.data.getValue().toString()).from(holder.data.getDateFormat()).calendar;
            holder.view.mDate.setText(new DateConverter(calendar).to(holder.data.getDateFormat()));
        } else {
            calendar = Calendar.getInstance();
            holder.view.mDate.setText("12:34");
        }
        holder.view.mDateLabel.setText(holder.data.getLabel());
        holder.view.mDate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String result = NumberUtils.from(hourOfDay)+":"+ NumberUtils.from(minute);
                        String convertedResult = new DateConverter(result).from("HH:mm").to(holder.data.getDateFormat());
                        holder.view.mDate.setText(convertedResult);
                        holder.data.setValue(convertedResult);
                        try {
                            holder.data.doListener(convertedResult,FormAdapter.this);
                        }catch (Exception ignore){
                            Log.e("ArisanForm","Listener not found");
                        }
                    }
                },calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE), DateFormat.is24HourFormat(getContext())).show();
            }
        });

        if(config.buttonBackground !=0)holder.view.mDate.setBackgroundResource(config.buttonBackground);
        if(config.labelColor!=0) holder.view.mDateLabel.setTextColor(getContext().getResources().getColor(config.labelColor));
    }

    private void ViewDate(final FormViewHolder holder) {
        holder.view.mDateLabel.setText(holder.data.getLabel());
        final Calendar calendar;
        if (holder.data.getValue() != null) {
            if(holder.data.getDateFormat() ==null)
                calendar = new DateConverter(holder.data.getValue().toString()).from("MMMM dd, yyyy").calendar;
            else
                calendar = new DateConverter(holder.data.getValue().toString()).from(holder.data.getDateFormat()).calendar;
            holder.view.mDate.setText(new DateConverter(calendar).to(holder.data.getDateFormat()));
        } else {
            calendar = Calendar.getInstance();
            holder.view.mDate.setText(new DateConverter(calendar).to(holder.data.getDateFormat()));
        }
        holder.view.mDate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String result = NumberUtils.from(dayOfMonth) + "-" + NumberUtils.from(month + 1) + "-" + year;
                        holder.view.mDate.setText(new DateConverter(result).from("dd-MM-yyyy").to(holder.data.getDateFormat()));
                        String value = new DateConverter(result).from("dd-MM-yyyy").to(holder.data.getDateFormat());
                        holder.data.setValue(value);
                        try{
                            holder.data.doListener(value,FormAdapter.this);
                        }catch (Exception ignore){
                            Log.e("ArisanForm","Listener not found");
                        }
                    }
                }, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        if(config.buttonBackground !=0)holder.view.mDate.setBackgroundResource(config.buttonBackground);
        if(config.labelColor!=0) holder.view.mDateLabel.setTextColor(getContext().getResources().getColor(config.labelColor));
    }

    private void ViewRadio(final FormViewHolder holder) {
        String value = (String) holder.data.getValue();
        List<RadioModel> dataList = null;
        try {
            dataList = FieldUtils.convertDataToRadio(holder.data.getData());
        }catch (Exception e){
            Log.e("ARISAN FORM",holder.data.getName()+" holder.data not match with requirement. Radio need Array or List holder.data.");
        }
        holder.view.mRadioGroup.removeAllViews();
        for(RadioModel mData:dataList){
            RadioButton btn = new RadioButton(getContext());
            btn.setId(mData.getId());
            //Log.d("__ID",String.valueOf(mData.getId()));
            btn.setText(mData.getValue());
            //Log.d("__VAL",mData.getValue());
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
                            holder.data.setValue(result);
                            holder.view.mRadioText.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void onTextChanged(CharSequence s, int start, int before, int count) {
                                    String value = holder.view.mRadioText.getText().toString();
                                    holder.data.setValue(value);
                                }

                                @Override
                                public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

                                @Override
                                public void afterTextChanged(Editable s) { }
                            });
                        }
                        //CONDITION
                        try {
                            holder.data.setValue(result);
                            holder.data.doListener(result,FormAdapter.this);

                        } catch (Exception ignored){}
                    }
                }
            });
            holder.view.mRadioGroup.addView(btn);
        }
        holder.view.mRadioLabel.setText(holder.data.getLabel());

        if(config.labelColor!=0) holder.view.mRadioLabel.setTextColor(getContext().getResources().getColor(config.labelColor));
    }

    private void ViewSlider(final FormViewHolder holder) {
        try {
            int value = NumberUtils.doubleToInt((Double) holder.data.getValue());
            if (value != 0)
                holder.view.mSlide.setProgress(value);
        }catch(Exception ignore){ }

        holder.view.mSlideLabel.setText(holder.data.getLabel());

        holder.view.mSlide.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                holder.data.setValue(progress);
                holder.view.mSlideValue.setText(String.valueOf(progress));
                try{
                    holder.data.doListener(String.valueOf(progress),FormAdapter.this);
                }catch (Exception e){
                    Log.d("Listener","Not Found");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        if(config.labelColor!=0) holder.view.mSlideLabel.setTextColor(getContext().getResources().getColor(config.labelColor));
    }

    private void ViewBoolean(final FormViewHolder holder) {
        holder.view.aSwitch.setText(holder.data.getLabel());
        if (holder.data.getValue() != null) {
            if (holder.data.getValue().toString().equals("true")) {
                holder.view.aSwitch.setChecked(true);
                holder.data.setValue(true);
            } else {
                holder.view.aSwitch.setChecked(false);
                holder.data.setValue(false);
            }
        } else {
            holder.view.aSwitch.setChecked(false);
        }
        holder.view.aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                holder.data.setValue(isChecked);
                try{
                    holder.data.doListener(Boolean.toString(isChecked),FormAdapter.this);
                }catch (NullPointerException e){
                    e.printStackTrace();
                }
            }
        });

        if(config.labelColor!=0) holder.view.aSwitch.setTextColor(getContext().getResources().getColor(config.labelColor));
    }

    public void showSubmitProgress(boolean roll){
        FormViewHolder holder = this.holderList.get(holderList.size()-1);
        if(config.useSubmit&&roll) {
            holder.view.mProgress.setVisibility(VISIBLE);
        }else {
            holder.view.mProgress.setVisibility(GONE);
        }
    }

    private void ViewSubmit(final FormViewHolder holder) {
        boolean roll = holder.data.getData() != null && (boolean) holder.data.getData();

        if(roll) holder.view.mProgress.setVisibility(View.VISIBLE);
        else holder.view.mProgress.setVisibility(View.GONE);

        //SUBMIT BUTTON
        holder.view.mSubmitText.setText(config.submitText);
        holder.view.mSubmit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String json = FieldAssembler.toFormJson(getListField());
                Log.d("__Result ",json);
                try {
                    checkBlankCondition();
                }catch (Exception ignored){}

                if(no_blank){
                    holder.view.mBlankText.setVisibility(View.GONE);
                    if(holder.view.mProgress.getVisibility()==VISIBLE)
                        Toast.makeText(getContext(), "Loading . . .", Toast.LENGTH_SHORT).show();
                    else
                        onSubmitListener.onSubmit(json);
                }else{
                    holder.view.mBlankText.setVisibility(View.VISIBLE);
                    holder.view.mBlankText.setText(checkBlankCondition());
                }
            }
        });

        if(config.buttonBackground !=0) holder.view.mSubmit.setBackgroundResource(config.buttonBackground);
        if(config.buttonTextColor !=0) {
            holder.view.mSubmitText.setTextColor(getContext().getResources().getColor(config.buttonTextColor));
            holder.view.mProgress.setIndeterminateTintList(ColorStateList.valueOf(config.buttonTextColor));
        };
    }

    private void ViewDelete(final FormViewHolder holder) {
        //DELETE BUTTON
        holder.view.mDelete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("__KEY","DELETE");
                onSubmitListener.onSubmit("Explode!!!");
            }
        });
    }

    private void ViewTitle(FormViewHolder holder) {
        //TITLE
        holder.view.mTitle.setText(config.title);
        if(config.labelColor!=0) holder.view.mTitle.setTextColor(getContext().getResources().getColor(config.labelColor));
    }

    private String checkBlankCondition(){
        StringBuilder blank = new StringBuilder();
        List<FormModel> models = getListField();//Parent
        List<String> arr_string = FieldUtils.countFormBlank(models);
        no_blank = arr_string.size() == 0;
        blank.append(TextUtils.join(arr_string));
        blank.append(" ").append(config.blankMessage);
        return blank.toString();
    }

    public List<FormModel> getListField() {
        List<FormModel> models = new ArrayList<>();
        for(FormModel model:fieldModels) models.add(model.renew());

        if(config.useSubmit) models.remove(models.size()-1);
        if(config.useTitle) models.remove(0);

        return models;
    }

    //========CONTEXT INIT===============//

    public FormAdapter(Context context) {
        super(context);
        Log.d("INIT A","");
        init();
    }

    public FormAdapter(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Log.d("INIT","B");
        init();
    }

    public FormAdapter(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.d("INIT","C");
        init();
    }

    public FormAdapter(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        Log.d("INIT","D");
        init();
    }

    //==========SETGET===============//


    public String getParent_field_name() {
        return parent_field_name;
    }

    public void setParent_field_name(String parent_field_name) {
        this.parent_field_name = parent_field_name;
    }

    public List<FormViewHolder> getHolderList() {
        return holderList;
    }

    public void setHolderList(List<FormViewHolder> holderList) {
        this.holderList = holderList;
    }

    public void setFieldModels(List<FormModel> fieldModels) {
        this.fieldModels = new ArrayList<>();
        if(config.useTitle) {
            FormModel title = new FormModel();
            title.setViewType(Form.TITLE);
            this.fieldModels.add(title);
        }
        Collections.sort(fieldModels, SortForm.getInstance());
        this.fieldModels.addAll(fieldModels);
        if(config.useSubmit) {
            FormModel title = new FormModel();
            title.setViewType(Form.BUTTON);
            this.fieldModels.add(title);
        }
    }

    public List<FormModel> getFieldModels() {
        List<FormModel> models = new ArrayList<>();
        for(FormModel model:fieldModels) models.add(model.renew());

        if(config.useSubmit) models.remove(models.size()-1);
        if(config.useTitle) models.remove(0);

        return fieldModels;
    }

    public void setModels(Object object) {
        setFieldModels(ObjectReader.getField(object));
    }

    public ViewGroup getParent_view() {
        return parent_view;
    }

    public void setParent_view(ViewGroup parent_view) {
        this.parent_view = parent_view;
    }

    public FormConfig getConfig() {
        return config;
    }

    public void setConfig(FormConfig config) {
        this.config = config;
    }

    public void updateConfig(FormConfig config){
        this.config = config;

    }

    public OnSubmitListener getOnSubmitListener() {
        return onSubmitListener;
    }

    public void setOnSubmitListener(OnSubmitListener onSubmitListener) {
        this.onSubmitListener = onSubmitListener;
    }

    //=======END OF SETGET============//

    public interface OnSubmitListener{
        void onSubmit(String result);
    }

    class MyTextWatcher implements TextWatcher {
        FormModel data;

        public MyTextWatcher(FormModel data) {
            this.data = data;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            //Log.d("__beforeChange",data.getName()+":"+s.toString());
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String value = s.toString();

            if(value.equals("")) {
                data.setValue(null);
            } else {
                data.setValue(value);
                //Log.d("__OnTextChange",data.getName()+":"+value);
            }

            if(data.getViewType()!=Form.SEARCH)
                try{
                    data.doListener(value,FormAdapter.this);
                }catch (Exception ignore){
                    //Log.e("Arisan","Listener Not Found!!!");
                }
        }

        @Override
        public void afterTextChanged(Editable s) {
            //Log.d("__afterChange",data.getName()+":"+s.toString());
        }
    }

    public void updateImage(final ImagePickerUtils imagePickerUtils){
        Uri uri = imagePickerUtils.getUri();
        UriUtils uriTools = new UriUtils(getContext(),uri);
        String pick_type = preference.load("pick_image");

        if(imagePickerUtils.isChild()){
            FormViewHolder parent_holder = new KotlinFilter().filterViewHolder(imagePickerUtils.getParent_name(),holderList);
            //FIND CHILD
            FormViewHolder holder = new KotlinFilter().filterViewHolder(imagePickerUtils.getFieldName(),parent_holder.childForm.get(imagePickerUtils.getChild_position()).holderList);
            holder.view.mImage.setImageBitmap(imagePickerUtils.getBitmap());
            holder.view.mImageName.setText(uriTools.getFilename_with_ex());
            holder.data.setValue(uriTools.getUri().getPath());
            holder.view.mImage.setImageBitmap(imagePickerUtils.getBitmap());
            try{
                holder.data.doListener(uriTools.getUri().getPath(),FormAdapter.this);
            }catch(Exception e){Log.d("__Arisan","No Listener IMAGE");}
        }else{
            //UPDATE DATA
            FormViewHolder holder = new KotlinFilter().filterViewHolder(imagePickerUtils.getFieldName(),holderList);
            holder.view.mImage.setImageBitmap(imagePickerUtils.getBitmap());
            holder.view.mImageName.setText(uriTools.getFilename_with_ex());
            holder.data.setValue(uriTools.getUri().getPath());
            holder.view.mImage.setImageBitmap(imagePickerUtils.getBitmap());
            try{
                holder.data.doListener(uriTools.getRealPath(),FormAdapter.this);
            }catch(Exception e){Log.d("__Arisan","No Listener IMAGE");}
        }
    }

    public void setProgressListener(ArisanListener.ProgressListener progressListener) {
        this.progressListener = progressListener;
        progressListener.showProgress();
    }

    public void updateListener(String field_name, ArisanListener.OnCondition condition){
        FormViewHolder holder = new KotlinFilter().filterViewHolder(field_name,this.holderList);
        holder.data.addCondition(condition);
        notifyData(holder);
    }

    public void updateValue(String field_name,Object value){
        if(value!=null) {
            FormViewHolder holder = new KotlinFilter().filterViewHolder(field_name, holderList);
            if (holder != null) holder.data.setValue(value);
            notifyData(holder);
        }
    }

    public void updateData(String field_name,Object data){
        FormViewHolder holder = new KotlinFilter().filterViewHolder(field_name,this.holderList);
        if(holder!=null) holder.data.setData(data);
        notifyData(holder);
    }

    public void notifyValue(){
        for(FormModel model:this.fieldModels){
            if(model.getViewType()==Form.ONETOMANY){
                FormViewHolder holder = new KotlinFilter().filterViewHolder(model.getName(),getHolderList());
                for(int i = 0;i<holder.adapter.getListForm().size();i++){
                    for(FormModel child_model:model.getChildFieldModel().get(i)) {
                        holder.adapter.getListForm().get(i).updateValue(child_model.getName(), child_model.getValue());
                    }
                }
            }else{
                updateValue(model.getName(),model.getValue());
            }
        }
    }

    public void updateValueByObject(Object object){
        if(object!=null){
            List<FormModel> models = ObjectReader.getField(object);
            for(FormModel model:models)
                updateValue(model.getName(),model.getValue());
        }
    }

    //=============QUERY FINDER============

    public void updateSpecificHolder(FormViewHolder holder){
        notifyData(holder);
    }

    public FormViewHolder findViewHolderByName(String name){
        FormViewHolder holder = new KotlinFilter().filterViewHolder(name,holderList);
        return holder;
    }

    //==============CUSTOM VIEW=============

    public void addCustomForm(ArisanCustomForm custom_form){
        customForms.add(custom_form);
    }
}
