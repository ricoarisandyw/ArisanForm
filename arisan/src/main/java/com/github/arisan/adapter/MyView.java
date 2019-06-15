package com.github.arisan.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.github.arisan.R;

public class MyView {
    View view;

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
    TextView mDate;
    //CHECKBOX
    RecyclerView mCheckboxParent;
    TextView mCheckboxLabel;
    EditText mCheckboxText;
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
    TextView mSlideValue;
    SeekBar mSlide;

    public MyView(View v) {
        this.view = v;

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
        mCheckboxLabel = v.findViewById(R.id.arisan_label_checkbox);
        mCheckboxText = v.findViewById(R.id.arisan_checkbox_text);
        mEditTextSearch = v.findViewById(R.id.arisan_search_name);
        mSearchButton = v.findViewById(R.id.arisan_search_button);
        mSearchLabel = v.findViewById(R.id.arisan_search_label);
        mOnetoManyAdd = v.findViewById(R.id.arisan_onetomany_add);
        mOnetoManyLabel = v.findViewById(R.id.arisan_onetomany_label);
        mOnetoManyList = v.findViewById(R.id.arisan_onetomany_list);
        mRadioGroup = v.findViewById(R.id.arisan_radio);
        mRadioLabel = v.findViewById(R.id.arisan_radio_label);
        mRadioText = v.findViewById(R.id.arisan_radio_others);
        mSlideLabel = v.findViewById(R.id.arisan_slide_label);
        mSlide = v.findViewById(R.id.arisan_slide);
        mSlideValue = v.findViewById(R.id.arisan_slide_text);
    }
}
