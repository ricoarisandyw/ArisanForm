package com.github.arisanform.model.probolinggo.model;

import com.github.arisan.annotation.Form;

public class SKCK {
    @Form(position = 1,label = "NIK Penduduk")
    String nik_penduduk;
    @Form(position = 2,label = "Nama")
    String nama;
    @Form(position = 3,label = "Alamat Lengkap")
    String alamat_lengkap;
    @Form(position = 4,label = "Tanggal Lahir")
    String tanggal_lahir;
    @Form(position = 5,label = "Agama",type = Form.RADIO)
    String agama;
    @Form(position = 6,label = "No Telp")
    String no_telp;
    @Form(position = 7,label ="No KK")
    String no_kk;
    @Form(position = 8,label ="Tempat Lahir")
    String tempat_lahir;
    @Form(position = 9,label ="Jenis Kelamin",type = Form.RADIO)
    String jenis_kelamin;
    @Form(position = 10,label ="Pekerjaan")
    String pekerjaan;
    @Form(position = 11,label ="Tujuan Pembuatan")
    String tujuan_pembuatan;
    @Form(position = 12,label ="Lampiran KTP")
    String lampiran_ktp;
    @Form(position = 13,label ="Lampiran KK")
    String lampiran_kk;
    @Form(position = 14,label ="Lampiran Akte Kelahiran")
    String lampiran_akte_kelahiran;
    @Form(position = 15,label ="Pasfoto")
    String lampiran_pasfoto;

    public SKCK() {
    }

    public String getNik_penduduk() {
        return nik_penduduk;
    }

    public void setNik_penduduk(String nik_penduduk) {
        this.nik_penduduk = nik_penduduk;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat_lengkap() {
        return alamat_lengkap;
    }

    public void setAlamat_lengkap(String alamat_lengkap) {
        this.alamat_lengkap = alamat_lengkap;
    }

    public String getTanggal_lahir() {
        return tanggal_lahir;
    }

    public void setTanggal_lahir(String tanggal_lahir) {
        this.tanggal_lahir = tanggal_lahir;
    }

    public String getAgama() {
        return agama;
    }

    public void setAgama(String agama) {
        this.agama = agama;
    }

    public String getNo_telp() {
        return no_telp;
    }

    public void setNo_telp(String no_telp) {
        this.no_telp = no_telp;
    }

    public String getNo_kk() {
        return no_kk;
    }

    public void setNo_kk(String no_kk) {
        this.no_kk = no_kk;
    }

    public String getTempat_lahir() {
        return tempat_lahir;
    }

    public void setTempat_lahir(String tempat_lahir) {
        this.tempat_lahir = tempat_lahir;
    }

    public String getJenis_kelamin() {
        return jenis_kelamin;
    }

    public void setJenis_kelamin(String jenis_kelamin) {
        this.jenis_kelamin = jenis_kelamin;
    }

    public String getPekerjaan() {
        return pekerjaan;
    }

    public void setPekerjaan(String pekerjaan) {
        this.pekerjaan = pekerjaan;
    }

    public String getTujuan_pembuatan() {
        return tujuan_pembuatan;
    }

    public void setTujuan_pembuatan(String tujuan_pembuatan) {
        this.tujuan_pembuatan = tujuan_pembuatan;
    }

    public String getLampiran_ktp() {
        return lampiran_ktp;
    }

    public void setLampiran_ktp(String lampiran_ktp) {
        this.lampiran_ktp = lampiran_ktp;
    }

    public String getLampiran_kk() {
        return lampiran_kk;
    }

    public void setLampiran_kk(String lampiran_kk) {
        this.lampiran_kk = lampiran_kk;
    }

    public String getLampiran_akte_kelahiran() {
        return lampiran_akte_kelahiran;
    }

    public void setLampiran_akte_kelahiran(String lampiran_akte_kelahiran) {
        this.lampiran_akte_kelahiran = lampiran_akte_kelahiran;
    }

    public String getLampiran_pasfoto() {
        return lampiran_pasfoto;
    }

    public void setLampiran_pasfoto(String lampiran_pasfoto) {
        this.lampiran_pasfoto = lampiran_pasfoto;
    }

    public SKCK(String nik_penduduk, String nama, String alamat_lengkap, String tanggal_lahir, String agama, String no_telp, String no_kk, String tempat_lahir, String jenis_kelamin, String pekerjaan, String tujuan_pembuatan, String lampiran_ktp, String lampiran_kk, String lampiran_akte_kelahiran, String lampiran_pasfoto) {
        this.nik_penduduk = nik_penduduk;
        this.nama = nama;
        this.alamat_lengkap = alamat_lengkap;
        this.tanggal_lahir = tanggal_lahir;
        this.agama = agama;
        this.no_telp = no_telp;
        this.no_kk = no_kk;
        this.tempat_lahir = tempat_lahir;
        this.jenis_kelamin = jenis_kelamin;
        this.pekerjaan = pekerjaan;
        this.tujuan_pembuatan = tujuan_pembuatan;
        this.lampiran_ktp = lampiran_ktp;
        this.lampiran_kk = lampiran_kk;
        this.lampiran_akte_kelahiran = lampiran_akte_kelahiran;
        this.lampiran_pasfoto = lampiran_pasfoto;
    }
}
