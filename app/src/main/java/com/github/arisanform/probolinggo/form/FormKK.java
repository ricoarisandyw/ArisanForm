package com.github.arisanform.probolinggo.form;

import com.github.arisan.annotation.Form;
import com.github.arisanform.probolinggo.model.KK;
import com.github.arisanform.probolinggo.model.KKPerorang;
import com.google.gson.Gson;

import io.realm.RealmList;
import io.realm.annotations.PrimaryKey;

public class FormKK{
    @PrimaryKey
    int id;

    @Form(position = 1,label="Desa Pengantar",type = Form.AUTOCOMPLETE)
    String desa_pengantar;
    @Form(position = 2,label="Nomor Telepon")
    String no_telp;
    @Form(position = 3,label="Dusun")
    String dusun;
    @Form(position = 4,label="Nama RT")
    String nama_rt;
    @Form(position = 5,label="Nama RW")
    String nama_rw;
    @Form(position = 6,label="FormKK Perorang",type = Form.ONETOMANY,relation = KKPerorang.class)
    RealmList<KKPerorang> kk_perorang;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesa_pengantar() {
        return desa_pengantar;
    }

    public void setDesa_pengantar(String desa_pengantar) {
        this.desa_pengantar = desa_pengantar;
    }

    public String getNo_telp() {
        return no_telp;
    }

    public void setNo_telp(String no_telp) {
        this.no_telp = no_telp;
    }

    public String getDusun() {
        return dusun;
    }

    public void setDusun(String dusun) {
        this.dusun = dusun;
    }

    public String getNama_rt() {
        return nama_rt;
    }

    public void setNama_rt(String nama_rt) {
        this.nama_rt = nama_rt;
    }

    public String getNama_rw() {
        return nama_rw;
    }

    public void setNama_rw(String nama_rw) {
        this.nama_rw = nama_rw;
    }

    public RealmList<KKPerorang> getKk_perorang() {
        return kk_perorang;
    }

    public void setKk_perorang(RealmList<KKPerorang> kk_perorang) {
        this.kk_perorang = kk_perorang;
    }

    public KK toKK(){
        Gson gson = new Gson();
        String json = gson.toJson(this);
        KK kk = gson.fromJson(json,KK.class);
        return kk;
    }
}
