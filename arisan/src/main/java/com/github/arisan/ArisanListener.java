package com.github.arisan;

import android.view.View;

import com.github.arisan.model.ArisanListenerModel;

public class ArisanListener {
    public static interface ErrorCondition{
        public ArisanListenerModel onError(String value);
    }
    public static interface SearchCondition{
        public ArisanListenerModel onSearch(String value);
    }

    public static interface ViewMod{
        public Object modding(Object view);
    }
}
