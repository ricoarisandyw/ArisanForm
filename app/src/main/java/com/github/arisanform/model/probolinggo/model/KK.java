package com.github.arisanform.model.probolinggo.model;

import com.github.arisan.annotation.Form;

import java.util.List;

public class KK {
    @Form(position = 1,label="No KK")
    String no_kk;
    @Form(position = 2,label="Provinsi",type = Form.RADIO)
    String provinsi;
    @Form(position = 3,label="Kabupaten",type = Form.RADIO)
    String kabupaten;
    @Form(position = 4,label="Kecamatan",type = Form.RADIO)
    String kecamatan;
    @Form(position = 5,label="Desa",type = Form.RADIO)
    String desa;
    @Form(position = 5,label="Dusun")
    String dusun;
    @Form(position = 6,label="Nama RT")
    String nama_rt;
    @Form(position = 7,label="Nama RW")
    String nama_rw;
    @Form(position = 8,label="No HP")
    String no_hp;
    @Form(position = 9,label="KK Perorang",type = Form.ONETOMANY,relation = KKPerorang.class)
    List<KKPerorang> kk_perorang;

    public String getNo_kk() {
        return no_kk;
    }

    public void setNo_kk(String no_kk) {
        this.no_kk = no_kk;
    }

    public String getProvinsi() {
        return provinsi;
    }

    public void setProvinsi(String provinsi) {
        this.provinsi = provinsi;
    }

    public String getKabupaten() {
        return kabupaten;
    }

    public void setKabupaten(String kabupaten) {
        this.kabupaten = kabupaten;
    }

    public String getKecamatan() {
        return kecamatan;
    }

    public void setKecamatan(String kecamatan) {
        this.kecamatan = kecamatan;
    }

    public String getDesa() {
        return desa;
    }

    public void setDesa(String desa) {
        this.desa = desa;
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

    public String getNo_hp() {
        return no_hp;
    }

    public void setNo_hp(String no_hp) {
        this.no_hp = no_hp;
    }

    public List<KKPerorang> getKk_perorang() {
        return kk_perorang;
    }

    public void setKk_perorang(List<KKPerorang> kk_perorang) {
        this.kk_perorang = kk_perorang;
    }
}
