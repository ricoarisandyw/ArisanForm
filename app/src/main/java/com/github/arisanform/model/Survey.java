package com.github.arisanform.model;

import com.github.arisan.annotation.Form;

public class Survey {

    public static String[] yesNo = {"ya","tidak"};

    @Form(label = "Status Perkawinan",type = Form.RADIO)
    private String data0;

    @Form(label = " Apakah jenjang pendidikan?", type = Form.RADIO)
    private String data1;

    @Form(label = "Seberapa kuat anda?", type = Form.SLIDER)
    private int data2;

    public Survey(){ }

    public String getData0() {
        return data0;
    }

    public void setData0(String data0) {
        this.data0 = data0;
    }

    public String getData1() {
        return data1;
    }

    public void setData1(String data1) {
        this.data1 = data1;
    }

    public int getData2() {
        return data2;
    }

    public void setData2(int data2) {
        this.data2 = data2;
    }
}
