package com.github.arisan.helper;

import com.github.arisan.model.ArisanFieldModel;

import java.util.Comparator;

public class SortField implements Comparator<ArisanFieldModel>
{
    // Used for sorting in ascending order of
    // roll number
    @Override
    public int compare(ArisanFieldModel o1, ArisanFieldModel o2) {
        return o1.getPosition() - o2.getPosition();
    }
}