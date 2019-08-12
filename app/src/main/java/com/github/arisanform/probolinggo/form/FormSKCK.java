package com.github.arisanform.probolinggo.form;

import com.github.arisan.annotation.Form;
import com.github.arisanform.probolinggo.model.MasterData;
import com.github.arisanform.probolinggo.model.SKCK;
import com.google.gson.Gson;

public class FormSKCK{

    @Form(position = 0,label="Tipe Pengajuan",required = true,type = Form.SPINNER)
    String tipe_pengajuan;
    @Form(position = 1,label = "NIK Penduduk",type = Form.SEARCH,required = true)
    String penduduk_nik;
    @Form(position = 2,label = "Nama",required = true)
    String penduduk_nik_nama;
    @Form(position = 3,label ="No FormKK",required = true)
    String penduduk_nik_no_kk;
    @Form(position = 4,label = "Alamat Lengkap",required = true)
    String penduduk_nik_alamat_lengkap;
    @Form(position = 5,label = "Desa Pengantar", type = Form.AUTOCOMPLETE,required = true)
    String desa_pengantar;
    @Form(position = 6,label = "Tanggal Lahir",type = Form.DATE,format = "yyyy-MM-dd",required = true)
    String penduduk_nik_tanggal_lahir;
    @Form(position = 7,label ="Tempat Lahir",required = true)
    String penduduk_nik_tempat_lahir;
    @Form(position = 8,label = "Agama",type = Form.SPINNER,required = true)
    String penduduk_nik_agama;
    @Form(position = 9,label = "No Telp",required = true)
    String penduduk_nik_no_telp;
    @Form(position = 10,label ="Jenis Kelamin",type = Form.SPINNER,required = true)
    String penduduk_nik_jenis_kelamin;
    @Form(position = 11,label ="Pekerjaan",required = true)
    String penduduk_nik_pekerjaan;
    @Form(position = 12,label ="Tujuan Pembuatan",required = true)
    String tujuan_pembuatan;

    @Form(position = 13,label ="Lampiran FormKTP",type = Form.IMAGE)
    String lampiran_ktp;
    @Form(position = 14,label ="Lampiran FormKK",type = Form.IMAGE)
    String lampiran_kk;
    @Form(position = 15,label ="Lampiran Akte Kelahiran/Ijazah",type = Form.IMAGE)
    String lampiran_akte_kelahiran;
    @Form(position = 16,label ="Pasfoto",type = Form.IMAGE)
    String lampiran_pasfoto;

    public FormSKCK() {
    }


    public SKCK toSKCK(){
        SKCK skck = new Gson().fromJson(new Gson().toJson(this),SKCK.class);
        skck.setPenduduk_nik_agama(""+(MasterData.find(MasterData.Array.STATUS_PERNIKAHAN,this.penduduk_nik_agama)+1));
        skck.setPenduduk_nik_jenis_kelamin(""+(MasterData.find(MasterData.Array.JENIS_KELAMIN,this.penduduk_nik_jenis_kelamin)+1));
        skck.setTipe_pengajuan(""+(MasterData.find(MasterData.Array.TIPE_PENGAJUAN,this.tipe_pengajuan)+1));
        skck.setDesa_pengantar(MasterData.DAFTAR_DESA_ID[MasterData.find(MasterData.DAFTAR_DESA,this.desa_pengantar)]);
        return skck;
    }

    public static FormSKCK fromSKCK(SKCK skck){
        FormSKCK formSKCK = new FormSKCK();
        formSKCK.penduduk_nik_jenis_kelamin = MasterData.Array.JENIS_KELAMIN[Integer.parseInt(skck.getPenduduk_nik_jenis_kelamin())-1];
        formSKCK.penduduk_nik_agama = MasterData.Array.AGAMA[Integer.parseInt(skck.getPenduduk_nik_agama())];
        formSKCK.tipe_pengajuan = MasterData.Array.TIPE_PENGAJUAN[MasterData.find(MasterData.Array.TIPE_PENGAJUAN,skck.getDesa_pengantar())];
        formSKCK.desa_pengantar = MasterData.DAFTAR_DESA[MasterData.find(MasterData.DAFTAR_DESA_ID,skck.getDesa_pengantar())];
        return formSKCK;
    }

    public String getTipe_pengajuan() {
        return tipe_pengajuan;
    }

    public void setTipe_pengajuan(String tipe_pengajuan) {
        this.tipe_pengajuan = tipe_pengajuan;
    }

    public String getPenduduk_nik() {
        return penduduk_nik;
    }

    public void setPenduduk_nik(String penduduk_nik) {
        this.penduduk_nik = penduduk_nik;
    }

    public String getPenduduk_nik_nama() {
        return penduduk_nik_nama;
    }

    public void setPenduduk_nik_nama(String penduduk_nik_nama) {
        this.penduduk_nik_nama = penduduk_nik_nama;
    }

    public String getPenduduk_nik_no_kk() {
        return penduduk_nik_no_kk;
    }

    public void setPenduduk_nik_no_kk(String penduduk_nik_no_kk) {
        this.penduduk_nik_no_kk = penduduk_nik_no_kk;
    }

    public String getPenduduk_nik_alamat_lengkap() {
        return penduduk_nik_alamat_lengkap;
    }

    public void setPenduduk_nik_alamat_lengkap(String penduduk_nik_alamat_lengkap) {
        this.penduduk_nik_alamat_lengkap = penduduk_nik_alamat_lengkap;
    }

    public String getDesa_pengantar() {
        return desa_pengantar;
    }

    public void setDesa_pengantar(String desa_pengantar) {
        this.desa_pengantar = desa_pengantar;
    }

    public String getPenduduk_nik_tanggal_lahir() {
        return penduduk_nik_tanggal_lahir;
    }

    public void setPenduduk_nik_tanggal_lahir(String penduduk_nik_tanggal_lahir) {
        this.penduduk_nik_tanggal_lahir = penduduk_nik_tanggal_lahir;
    }

    public String getPenduduk_nik_tempat_lahir() {
        return penduduk_nik_tempat_lahir;
    }

    public void setPenduduk_nik_tempat_lahir(String penduduk_nik_tempat_lahir) {
        this.penduduk_nik_tempat_lahir = penduduk_nik_tempat_lahir;
    }

    public String getPenduduk_nik_agama() {
        return penduduk_nik_agama;
    }

    public void setPenduduk_nik_agama(String penduduk_nik_agama) {
        this.penduduk_nik_agama = penduduk_nik_agama;
    }

    public String getPenduduk_nik_no_telp() {
        return penduduk_nik_no_telp;
    }

    public void setPenduduk_nik_no_telp(String penduduk_nik_no_telp) {
        this.penduduk_nik_no_telp = penduduk_nik_no_telp;
    }

    public String getPenduduk_nik_jenis_kelamin() {
        return penduduk_nik_jenis_kelamin;
    }

    public void setPenduduk_nik_jenis_kelamin(String penduduk_nik_jenis_kelamin) {
        this.penduduk_nik_jenis_kelamin = penduduk_nik_jenis_kelamin;
    }

    public String getPenduduk_nik_pekerjaan() {
        return penduduk_nik_pekerjaan;
    }

    public void setPenduduk_nik_pekerjaan(String penduduk_nik_pekerjaan) {
        this.penduduk_nik_pekerjaan = penduduk_nik_pekerjaan;
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
}
