package com.github.arisan.adapter;

import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.github.arisan.R;

public class MyView {
    private View view;

    public TextView mTitle,mBlankText;
    //BUTTON
    public LinearLayout mSubmit;
    public ProgressBar mProgress;
    public TextView mSubmitText;
    //TEXT
    public TextView mEditTextLabel,mDateLabel,mSpinnerLabel;
    public EditText mEditText;
    //ONELINE
    public TextView mOneLineText;
    //FLOWTEXT
    public TextInputLayout mEditText2Layout;
    public TextView mEditText2;
    //PASSOWRD
    public TextView mPasswordLabel;
    public EditText mPassword;
    //SEARCH
    public EditText mEditTextSearch;
    public TextView mSearchLabel;
    public LinearLayout mSearchButton;
    public ProgressBar mSearchLoading;
    public ImageView mSearchIcon;
    //BOOLEAN
    public Switch aSwitch;
    //SPINNER
    public AppCompatSpinner mSpinner;
    //FILE
    public Button mFile;
    public TextView mFileName,mFileLabel;
    //DATETIME
    public TextView mDate;
    //CHECKBOX
    public RecyclerView mCheckboxParent;
    public TextView mCheckboxLabel;
    public EditText mCheckboxText;
    //ONETOMANY
    public RecyclerView mOnetoManyList;
    public TextView mOnetoManyLabel;
    public Button mOnetoManyAdd;
    //RADIO BUTTON
    public RadioGroup mRadioGroup;
    public TextView mRadioLabel;
    public EditText mRadioText;
    //SLIDER
    public TextView mSlideLabel;
    public TextView mSlideValue;
    public SeekBar mSlide;
    //IMAGE
    public TextView mImageLabel,mImageName;
    public ImageView mImage;
    public Button mImagePick;
    //AUTOCOMPLETE
    public AppCompatAutoCompleteTextView mAutocomplete;
    public TextView mAutocompleteLabel;
    //DELETE
    public ImageView mDelete;
    //MANY
    public LinearLayout vManyContainer;
    public TextView vManyLabel;
    public Button vManyAdd;

    public MyView(View v) {
        this.view = v;

        mPasswordLabel = v.findViewById(R.id.arisan_password_label);
        mPassword = v.findViewById(R.id.arisan_password);
        mBlankText = v.findViewById(R.id.arisan_button_blank);
        mEditTextLabel = v.findViewById(R.id.arisan_text_label);
        mEditText = v.findViewById(R.id.arisan_text);
        mDateLabel = v.findViewById(R.id.arisan_date_label);
        mDate = v.findViewById(R.id.arisan_date);
        mSpinnerLabel = v.findViewById(R.id.arisan_spinner_label);
        mSpinner = v.findViewById(R.id.arisan_spinner);
        mTitle = v.findViewById(R.id.arisan_title);
        mSubmit = v.findViewById(R.id.arisan_button);
        aSwitch = v.findViewById(R.id.arisan_switch);
        mFile = v.findViewById(R.id.arisan_upload);
        mFileLabel = v.findViewById(R.id.arisan_file_label);
        mFileName = v.findViewById(R.id.arisan_file_name);
        mCheckboxParent = v.findViewById(R.id.arisan_checkbox_list);
        mCheckboxLabel = v.findViewById(R.id.arisan_label_checkbox);
        mCheckboxText = v.findViewById(R.id.arisan_checkbox_text);
        mEditTextSearch = v.findViewById(R.id.arisan_search_name);
        mSearchButton = v.findViewById(R.id.arisan_search_button);
        mSearchLabel = v.findViewById(R.id.arisan_search_label);
        mSearchLoading = v.findViewById(R.id.arisan_search_loading);
        mSearchIcon = v.findViewById(R.id.arisan_search_icon);
        mOnetoManyAdd = v.findViewById(R.id.arisan_onetomany_add);
        mOnetoManyLabel = v.findViewById(R.id.arisan_onetomany_label);
        mOnetoManyList = v.findViewById(R.id.arisan_onetomany_list);
        mRadioGroup = v.findViewById(R.id.arisan_radio);
        mRadioLabel = v.findViewById(R.id.arisan_radio_label);
        mRadioText = v.findViewById(R.id.arisan_radio_others);
        mSlideLabel = v.findViewById(R.id.arisan_slide_label);
        mSlide = v.findViewById(R.id.arisan_slide);
        mSlideValue = v.findViewById(R.id.arisan_slide_text);
        mImage = v.findViewById(R.id.arisan_image_view);
        mImageLabel = v.findViewById(R.id.arisan_image_label);
        mImageName = v.findViewById(R.id.arisan_image_name);
        mImagePick = v.findViewById(R.id.arisan_image_pick);
        mAutocomplete = v.findViewById(R.id.arisan_autocomplete);
        mAutocompleteLabel = v.findViewById(R.id.arisan_autocomplete_label);
        mOneLineText = v.findViewById(R.id.arisan_oneline_text);
        mEditText2 = v.findViewById(R.id.arisan_text2_text);
        mEditText2Layout = v.findViewById(R.id.arisan_text2_text_layout);
        mDelete = v.findViewById(R.id.arisan_delete);
        mProgress = v.findViewById(R.id.arisan_button_progress);
        mSubmitText = v.findViewById(R.id.arisan_button_text);
        vManyAdd = v.findViewById(R.id.arisan_many_add);
        vManyContainer = v.findViewById(R.id.arisan_many_list);
        vManyLabel = v.findViewById(R.id.arisan_many_label);
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }
}
