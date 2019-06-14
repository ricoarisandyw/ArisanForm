package com.github.arisan.helper;

import java.util.List;

public class ArrayUtils {

    public boolean find(String value,List<String> data){
        for(String d:data){
            if(d.equals(value)){
                return true;
            }
        }
        return false;
    }
}
