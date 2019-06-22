package com.github.arisan.helper;

public class NumberUtils {
    public static String from(int value){
        return String.format("%02d", value);
    }
    public static int doubleToInt(Double value){
        int result = (int) Math.round(value);
        return result;
    }
}
