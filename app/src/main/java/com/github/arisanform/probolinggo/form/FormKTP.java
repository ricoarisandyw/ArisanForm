package com.github.arisanform.probolinggo.form;

import com.github.arisan.annotation.Form;
import com.github.arisanform.probolinggo.model.KTP;
import com.github.arisanform.probolinggo.model.MasterData;
import com.google.gson.Gson;

public class FormKTP {
    @Form(position = 0,label="NIK",type = Form.SEARCH)
    String nik;
    @Form(position = 1,label="Tipe Pengajuan",type = Form.SPINNER,required = true)
    String tipe_pengajuan;
    @Form(position = 2,label = "Desa Pengantar",type = Form.AUTOCOMPLETE, required = true)
    String desa_pengantar;
    @Form(position = 2,label="Nama",required = true)
    String nik_nama;
    @Form(position = 4,label="No KK",required = true)
    String nik_no_kk;
    @Form(position = 5,label="Status Pernikahan",type = Form.RADIO,required = true)
    String nik_status_pernikahan;
    @Form(position = 6,label="Pekerjaan Desc",required = true)
    String nik_pekerjaan_desc;
    @Form(position = 7,label="Alamat Lengkap",required = true)
    String nik_alamat_lengkap;
    @Form(type = Form.DATE,position = 9,label="Tanggal Lahir",required = true,format = "yyyy-MM-dd")
    String nik_tanggal_lahir;
    @Form(position = 10,label="Tempat Lahir",required = true)
    String nik_tempat_lahir;
    @Form(position = 11,label="Jenis Kelamin",type = Form.RADIO,required = true)
    String nik_jenis_kelamin;
    @Form(position = 12,label="Agama", type = Form.SPINNER,required = true)
    String nik_agama;
    @Form(position = 13,label="Gol Darah",required = true)
    String nik_gol_darah;
    @Form(position = 14,label="No Telp",required = true)
    String nik_no_telp;
    @Form(position = 15,label="Lampiran Foto",type = Form.IMAGE,required = false)
    String lampiran_foto;
    @Form(position = 16,label="Lampiran FormKK",type = Form.IMAGE,required = false)
    String lampiran_kk;

    public FormKTP() { }

    public String getTipe_pengajuan() {
        return tipe_pengajuan;
    }

    public void setTipe_pengajuan(String tipe_pengajuan) {
        this.tipe_pengajuan = tipe_pengajuan;
    }

    public String getNik_nama() {
        return nik_nama;
    }

    public void setNik_nama(String nik_nama) {
        this.nik_nama = nik_nama;
    }

    public String getNik_tanggal_lahir() {
        return nik_tanggal_lahir;
    }

    public void setNik_tanggal_lahir(String nik_tanggal_lahir) {
        this.nik_tanggal_lahir = nik_tanggal_lahir;
    }

    public String getNik_no_kk() {
        return nik_no_kk;
    }

    public void setNik_no_kk(String nik_no_kk) {
        this.nik_no_kk = nik_no_kk;
    }

    public String getNik_status_pernikahan() {
        return nik_status_pernikahan;
    }

    public void setNik_status_pernikahan(String nik_status_pernikahan) {
        this.nik_status_pernikahan = nik_status_pernikahan;
    }

    public String getNik_pekerjaan_desc() {
        return nik_pekerjaan_desc;
    }

    public void setNik_pekerjaan_desc(String nik_pekerjaan_desc) {
        this.nik_pekerjaan_desc = nik_pekerjaan_desc;
    }

    public String getNik_alamat_lengkap() {
        return nik_alamat_lengkap;
    }

    public void setNik_alamat_lengkap(String nik_alamat_lengkap) {
        this.nik_alamat_lengkap = nik_alamat_lengkap;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getNik_tempat_lahir() {
        return nik_tempat_lahir;
    }

    public void setNik_tempat_lahir(String nik_tempat_lahir) {
        this.nik_tempat_lahir = nik_tempat_lahir;
    }

    public String getNik_jenis_kelamin() {
        return nik_jenis_kelamin;
    }

    public void setNik_jenis_kelamin(String nik_jenis_kelamin) {
        this.nik_jenis_kelamin = nik_jenis_kelamin;
    }

    public String getNik_agama() {
        return nik_agama;
    }

    public void setNik_agama(String nik_agama) {
        this.nik_agama = nik_agama;
    }

    public String getNik_gol_darah() {
        return nik_gol_darah;
    }

    public void setNik_gol_darah(String nik_gol_darah) {
        this.nik_gol_darah = nik_gol_darah;
    }

    public String getNik_no_telp() {
        return nik_no_telp;
    }

    public void setNik_no_telp(String nik_no_telp) {
        this.nik_no_telp = nik_no_telp;
    }

    public String getLampiran_foto() {
        return lampiran_foto;
    }

    public void setLampiran_foto(String lampiran_foto) {
        this.lampiran_foto = lampiran_foto;
    }

    public String getLampiran_kk() {
        return lampiran_kk;
    }

    public void setLampiran_kk(String lampiran_kk) {
        this.lampiran_kk = lampiran_kk;
    }

    public String getDesa_pengantar() {
        return desa_pengantar;
    }

    public void setDesa_pengantar(String desa_pengantar) {
        this.desa_pengantar = desa_pengantar;
    }

    public KTP toKTP(){
        KTP ktp = new Gson().fromJson(new Gson().toJson(this),KTP.class);
        ktp.setNik_agama(""+(MasterData.find(MasterData.Array.AGAMA,this.nik_agama)));
        ktp.setNik_status_pernikahan(""+(MasterData.find(MasterData.Array.STATUS_PERNIKAHAN,this.nik_status_pernikahan)+1));
        ktp.setNik_jenis_kelamin(""+(MasterData.find(MasterData.Array.JENIS_KELAMIN,this.nik_jenis_kelamin)+1));
        ktp.setNik_gol_darah(""+(MasterData.find(MasterData.Array.GOLONGAN_DARAH,this.nik_gol_darah)+1));
        ktp.setTipe_pengajuan(""+(MasterData.find(MasterData.Array.TIPE_PENGAJUAN,this.tipe_pengajuan)+1));
        ktp.setDesa_pengantar(MasterData.DAFTAR_DESA_ID[MasterData.find(MasterData.DAFTAR_DESA,this.desa_pengantar)]);
        return ktp;
    }

    public static FormKTP fromKTP(KTP ktp){
        FormKTP formKTP = new Gson().fromJson(new Gson().toJson(ktp),FormKTP.class);
        formKTP.nik_alamat_lengkap = ktp.getNik_alamat_lengkap();
        if(ktp.getNik_agama()!=null) formKTP.nik_agama = MasterData.Array.AGAMA[Integer.parseInt(ktp.getNik_agama())];
        if(ktp.getNik_status_pernikahan()!=null)formKTP.nik_status_pernikahan = MasterData.Array.STATUS_PERNIKAHAN[Integer.parseInt(ktp.getNik_status_pernikahan())];
        if(ktp.getDesa_pengantar()!=null) formKTP.nik_alamat_lengkap = MasterData.DAFTAR_DESA[MasterData.find(MasterData.DAFTAR_DESA_ID,ktp.getDesa_pengantar())];
        if(ktp.getNik_jenis_kelamin()!=null) formKTP.nik_jenis_kelamin = MasterData.Array.JENIS_KELAMIN[Integer.parseInt(ktp.getNik_jenis_kelamin())];
        return formKTP;
    }
}
