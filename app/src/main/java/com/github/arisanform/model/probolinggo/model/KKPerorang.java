package com.github.arisanform.model.probolinggo.model;

import com.github.arisan.annotation.Form;

public class KKPerorang {
    //    DATA PER ORANG
    @Form(position = 9,label="ID")
    String id;
    @Form(position = 10,label="NIK")
    String nik;
    @Form(position = 11,label="Lampiran KTP")
    String lampiran_ktp;
    @Form(position = 12,label="Nama Lengkap")
    String nama_lengkap;
    @Form(position = 13,label="Jenis Kelamin")
    String jenis_kelamin;
    @Form(position = 14,label="Akta Lahir")
    String akta_lahir;
    @Form(position = 15,label="NIK Ibu")
    String nik_ibu;
    @Form(position = 16,label="Nama Ibu")
    String nama_ibu;
    @Form(position = 17,label="NIK Ayah")
    String nik_ayah;
    @Form(position = 18,label="Nama Ayah")
    String nama_ayah;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getLampiran_ktp() {
        return lampiran_ktp;
    }

    public void setLampiran_ktp(String lampiran_ktp) {
        this.lampiran_ktp = lampiran_ktp;
    }

    public String getNama_lengkap() {
        return nama_lengkap;
    }

    public void setNama_lengkap(String nama_lengkap) {
        this.nama_lengkap = nama_lengkap;
    }

    public String getJenis_kelamin() {
        return jenis_kelamin;
    }

    public void setJenis_kelamin(String jenis_kelamin) {
        this.jenis_kelamin = jenis_kelamin;
    }

    public String getAkta_lahir() {
        return akta_lahir;
    }

    public void setAkta_lahir(String akta_lahir) {
        this.akta_lahir = akta_lahir;
    }

    public String getNik_ibu() {
        return nik_ibu;
    }

    public void setNik_ibu(String nik_ibu) {
        this.nik_ibu = nik_ibu;
    }

    public String getNama_ibu() {
        return nama_ibu;
    }

    public void setNama_ibu(String nama_ibu) {
        this.nama_ibu = nama_ibu;
    }

    public String getNik_ayah() {
        return nik_ayah;
    }

    public void setNik_ayah(String nik_ayah) {
        this.nik_ayah = nik_ayah;
    }

    public String getNama_ayah() {
        return nama_ayah;
    }

    public void setNama_ayah(String nama_ayah) {
        this.nama_ayah = nama_ayah;
    }
}
