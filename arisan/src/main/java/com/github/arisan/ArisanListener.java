package com.github.arisan;

import com.github.arisan.model.ListenerModel;

import java.util.List;

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

    public static interface CheckboxCondition{
        public ListenerModel onChecked(String just_checked, List<String> all_checked);
    }
}
