package com.github.arisan;

import com.github.arisan.model.ListenerModel;

public class ArisanListener {
    public static interface Condition {
        public ListenerModel onValue(String value);
    }
    public static interface SearchCondition{
        public ListenerModel onSearch(String value);
    }

    public static interface ViewMod{
        public Object modding(Object view);
    }
}
