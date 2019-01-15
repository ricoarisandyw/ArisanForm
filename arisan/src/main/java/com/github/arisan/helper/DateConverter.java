package com.github.arisan.helper;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by wijaya on 11/26/2018.
 */

public class DateConverter {
    public static String YYYYMMDD = "yyyy-MM-dd";
    public static String YYYYMMDDHHMM = "yyyy-MM-dd hh:mm";
    public static String DDMMYYYY = "dd-MM-yyyy";
    public static String DDBulanYYYY = "dd-MM-yyyy";
    public String data;
    public String fromFormat;
    public Calendar calendar;
    public static String NAMA_BULAN[] = new String[]{
            "Januari",
            "Februari",
            "Maret",
            "April",
            "Mei",
            "Juni",
            "Juli",
            "Agustus",
            "September",
            "oktober",
            "November",
            "Desember"
    };

    public DateConverter(String data){
        calendar = Calendar.getInstance();
        this.data = data;
    }

    public DateConverter(Calendar calendar){
        this.calendar = calendar;
    }

    public DateConverter(Date date){ this.calendar.setTime(date); }

    public DateConverter from(String pattern){
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            calendar.setTime(format.parse(data));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return this;
    }

    public String to(String pattern){
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        String result = format.format(calendar.getTime());
        return result;
    }

    public String toIndonesia(){
        return calendar.get(Calendar.DAY_OF_MONTH)+" "+NAMA_BULAN[calendar.get(Calendar.MONTH)]+" "+calendar.get(Calendar.YEAR)+" "+String.format("%02d:%02d", calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
    }
}
