package com.github.arisanform.model;

import com.github.arisan.annotation.Form;

import java.util.List;

public class KKK {
    @Form(label = "NIK")
    String nik;

    @Form(label = "Data KK",type = Form.ONETOMANY,relation = KK.class)
    List<KK> data_kk;

    public KKK() {
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public List<KK> getData_kk() {
        return data_kk;
    }

    public void setData_kk(List<KK> data_kk) {
        this.data_kk = data_kk;
    }
}
