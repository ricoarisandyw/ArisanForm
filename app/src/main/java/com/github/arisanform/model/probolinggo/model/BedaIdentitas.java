package com.github.arisanform.model.probolinggo.model;

import com.github.arisan.annotation.Form;

import java.util.Date;

public class BedaIdentitas {
    @Form(label = "NIK")
    String nik;
    @Form(label = "Nama")
    String nama;
    @Form(label = "Alamat")
    String alamat;
    @Form(type = Form.DATE,label = "Tanggal lahir")
    Date tanggal_lahir;
    @Form(label = "Nama yang salah")
    String nama_yang_salah;
    @Form(label = "Tertera di")
    String tertera_di;
    @Form(label = "Keterangan lain")
    String keterangan_lain;
    @Form(label = "Lampiran KTP",type = Form.FILE)
    String lampiran_ktp;
    @Form(label = "Lampiran lain",type = Form.FILE)
    String lampiran_lain;

    public BedaIdentitas() {
    }

    public BedaIdentitas(String nik, String nama, String alamat, Date tanggal_lahir, String nama_yang_salah, String tertera_di, String keterangan_lain, String lampiran_ktp, String lampiran_lain) {
        this.nik = nik;
        this.nama = nama;
        this.alamat = alamat;
        this.tanggal_lahir = tanggal_lahir;
        this.nama_yang_salah = nama_yang_salah;
        this.tertera_di = tertera_di;
        this.keterangan_lain = keterangan_lain;
        this.lampiran_ktp = lampiran_ktp;
        this.lampiran_lain = lampiran_lain;
    }

    public String getLampiran_ktp() {
        return lampiran_ktp;
    }

    public void setLampiran_ktp(String lampiran_ktp) {
        this.lampiran_ktp = lampiran_ktp;
    }

    public String getLampiran_lain() {
        return lampiran_lain;
    }

    public void setLampiran_lain(String lampiran_lain) {
        this.lampiran_lain = lampiran_lain;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public Date getTanggal_lahir() {
        return tanggal_lahir;
    }

    public void setTanggal_lahir(Date tanggal_lahir) {
        this.tanggal_lahir = tanggal_lahir;
    }

    public String getNama_yang_salah() {
        return nama_yang_salah;
    }

    public void setNama_yang_salah(String nama_yang_salah) {
        this.nama_yang_salah = nama_yang_salah;
    }

    public String getTertera_di() {
        return tertera_di;
    }

    public void setTertera_di(String tertera_di) {
        this.tertera_di = tertera_di;
    }

    public String getKeterangan_lain() {
        return keterangan_lain;
    }

    public void setKeterangan_lain(String keterangan_lain) {
        this.keterangan_lain = keterangan_lain;
    }
}
