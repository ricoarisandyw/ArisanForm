package com.github.arisan.helper;

import com.github.arisan.model.FormModel;

import java.util.Comparator;

public class SortForm implements Comparator<FormModel>
{
    // Used for sorting in ascending order of
    // roll number
    @Override
    public int compare(FormModel o1, FormModel o2) {
        return o1.getPosition() - o2.getPosition();
    }

    public static SortForm getInstance(){
        return new SortForm();
    }
}