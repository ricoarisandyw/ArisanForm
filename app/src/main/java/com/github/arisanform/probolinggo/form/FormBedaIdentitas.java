package com.github.arisanform.probolinggo.form;

import com.github.arisan.annotation.Form;

public class FormBedaIdentitas {
    int id;

    @Form(type = Form.SEARCH,required = true)
    String nik;
    @Form(required = true)
    String nama;
    @Form(required = true)
    String alamat;
    @Form(type = Form.DATE,required = true)
    String tanggal_lahir;
    @Form(required = true)
    String nama_yang_salah;
    @Form(required = true)
    String tertera_di;
    @Form()
    String keterangan;
}
