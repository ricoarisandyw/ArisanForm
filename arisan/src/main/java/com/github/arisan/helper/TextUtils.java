package com.github.arisan.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TextUtils {
    public static String join(String[] arr){
        List<String> data = Arrays.asList(arr);
        StringBuilder result = new StringBuilder();
        for(String a :data){
            if(data.indexOf(a)!=data.size()-1)
                result.append(a+", ");
            else
                result.append(a);
        }
        return result.toString();
    }

    public static String join(List<String> data){
        StringBuilder result = new StringBuilder();
        for(String a :data){
            if(data.indexOf(a)!=data.size()-1)
                result.append(a+", ");
            else
                result.append(a);
        }
        return result.toString();
    }
}
