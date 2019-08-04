package com.github.arisan.helper;

import java.io.File;

public class FileUtils {
    public static int getFileSizeKB(File file){
        int file_size = Integer.parseInt(String.valueOf(file.length()/1024));
        return file_size;
    }

    public static String getFileSiz(File file){
        int file_size = Integer.parseInt(String.valueOf(file.length()/1024));
        if(file_size>1000){
            return (file_size/1024)+" Mb";
        }else{
            return file_size+" Kb";
        }
    }
}
