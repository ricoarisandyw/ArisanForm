package com.github.arisanform.model;

import android.content.Context;

import com.github.arisan.annotation.Model;
import com.github.arisan.helper.RadioUtils;
import com.github.arisan.model.ArisanFieldModel;
import com.github.arisan.model.template.FormCheckbox;
import com.github.arisan.model.template.FormDate;
import com.github.arisan.model.template.FormNumber;
import com.github.arisan.model.template.FormRadio;
import com.github.arisan.model.template.FormSlider;
import com.github.arisan.model.template.FormText;
import com.github.arisanform.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FormC {
    static Map<String, ArisanFieldModel> data = new HashMap<>();
    Context context;

    public FormC(Context context){
        this.context = context;
        all_1();
        all_2();
        all_3();
        all_4();
        all_5();
        all_6();
    }

    public String Str(int id){
        return context.getResources().getString(id);
    }

    public List<ArisanFieldModel> all_1(){
        List<ArisanFieldModel> list = new ArrayList<>();

        String[] status_kawin = {Str(R.string.belum_kawin),Str(R.string.kawin),Str(R.string.duda_janda)};
        ArisanFieldModel model10l = FormRadio.getModel();
        model10l.setLabel(Str(R.string.pertanyaan_c_101));
        model10l.setName("101");
        model10l.setPosition(1);
        model10l.setData(RadioUtils.convertToRadio(status_kawin));

        ArisanFieldModel model102 = FormRadio.getModel();
        String[] sekolah = {Str(R.string.tidak_sekolah),Str(R.string.lulus_sd),Str(R.string.lulus_smp),Str(R.string.akademi)};
        model102.setLabel(Str(R.string.pertanyaan_c_102));
        model102.setName("102");
        model102.setPosition(2);
        model102.setData(RadioUtils.convertToRadio(sekolah));

        ArisanFieldModel model103 = FormRadio.getModel();
        String[] test = {Str(R.string.manajer),Str(R.string.teknisi),Str(R.string.tenaga_pemasaran),
                Str(R.string.buruh),Str(R.string.sopir),Str(R.string.polri),Str(R.string.pekerjaan_rumah),
                Str(R.string.atlit),Str(R.string.pensiunan),Str(R.string.pelajar),Str(R.string.tidak_bekerja), Model.OTHERS};
        model103.setLabel(Str(R.string.pertanyaan_c_103_a));
        model103.setName("103");
        model103.setPosition(3);
        model103.setData(RadioUtils.convertToRadio(test));

        ArisanFieldModel model103_1 = FormNumber.getModel();
        model103_1.setName("103_1");
        model103_1.setLabel(Str(R.string.pertanyaan_c_103_b));
        model103_1.setPosition(4);

        ArisanFieldModel model103_2 = FormText.getModel();
        model103_2.setName("103_2");
        model103_2.setLabel(Str(R.string.pertanyaan_c_103_c));
        model103_2.setPosition(5);

        ArisanFieldModel model104 = FormRadio.getModel();
        model104.setName("104");
        String[] jawaban4 = {Str(R.string.ya),Str(R.string.istri),Str(R.string.anak)
                ,Str(R.string.orang_tua), Model.OTHERS};
        model104.setLabel(Str(R.string.pertanyaan_c_104));
        model104.setPosition(6);
        model104.setData(RadioUtils.convertToRadio(jawaban4));

        ArisanFieldModel model105 = FormText.getModel();
        model105.setName("105");
        model105.setLabel(Str(R.string.pertanyaan_c_105));
        model105.setPosition(6);

        ArisanFieldModel model105_1 = FormText.getModel();
        model105_1.setName("105_1");
        model105_1.setLabel(Str(R.string.pertanyaan_c_105_1));
        model105_1.setPosition(7);

        ArisanFieldModel model105_2 = FormText.getModel();
        model105_2.setName("105_2");
        model105_2.setLabel(Str(R.string.pertanyaan_c_105_2));
        model105_2.setPosition(8);

        ArisanFieldModel model106 = FormText.getModel();
        model106.setName("106");
        model106.setLabel(Str(R.string.pertanyaan_c_106));
        model106.setPosition(9);

        ArisanFieldModel model107 = FormRadio.getModel();
        model107.setName("107");
        String[] jawaban107 = {Str(R.string.dibawah1juta),Str(R.string.sampai2juta),Str(R.string.sampai3juta),
                Str(R.string.sampai4juta),Str(R.string.sampai5juta),Str(R.string.sampai6juta),Str(R.string.sampai7juta),
                Str(R.string.sampai8juta),Str(R.string.diatas8juta),Str(R.string.tidak_tahu)};
        model107.setLabel(Str(R.string.pertanyaan_c_107));
        model107.setPosition(10);
        model107.setData(RadioUtils.convertToRadio(jawaban107));

        ArisanFieldModel model108 = FormCheckbox.getModel();
        //**jika tidak asuransi lain maka loncat ke 109
        model108.setName("108");
        String[] jawaban108 = {Str(R.string.bpjs_kes),Str(R.string.bpjs_ket),Str(R.string.asuransi_lain),Str(R.string.tidak_punya_asuransi)};
        model108.setLabel(Str(R.string.pertanyaan_c_108));
        model108.setPosition(11);
        model108.setData(jawaban108);

        ArisanFieldModel model108_1 = FormText.getModel();
        //**jika tidak asuransi lain maka loncat ke 109
        model108_1.setName("108_1");
        model108_1.setLabel(Str(R.string.pertanyaan_c_108_1));
        model108_1.setPosition(12);

        ArisanFieldModel model109 = FormCheckbox.getModel();
        model109.setName("109");
        String[] jawaban109 = {Str(R.string.sendiri)
                ,Str(R.string.kerabat)
                , Model.OTHERS};
        model109.setLabel(Str(R.string.pertanyaan_c_109));
        model109.setPosition(13);
        model109.setData(jawaban109);



        list.add(model10l);
        list.add(model102);
        list.add(model103);
//        list.add(model103_2);
//        list.add(model103_3);
        list.add(model104);
        list.add(model105);
        list.add(model105_1);
        list.add(model105_2);
        list.add(model106);
        list.add(model107);
        list.add(model108);
//        list.add(model108_1);
        list.add(model109);

        data.put("101",model10l);
        data.put("102",model102);
        data.put("103",model103);
        data.put("103_1",model103_1);
        data.put("103_2",model103_2);
        data.put("104",model104);
        data.put("105",model105);
        data.put("105_1",model105_1);
        data.put("105_2",model105_2);
        data.put("106",model106);
        data.put("107",model107);
        data.put("108",model108);
        data.put("108_1",model108_1);
        data.put("109",model109);

        return list;
    }

    public List<ArisanFieldModel> all_2(){
        List<ArisanFieldModel> list = new ArrayList<>();

        ArisanFieldModel mode201 = FormRadio.getModel();
        mode201.setName("201");
        //** jika ya ke 201_1 jika tidak loncat ke 203
        String[] jawaban201 = {Str(R.string.ya)
                ,Str(R.string.tidak)};
        mode201.setLabel(Str(R.string.pertanyaan_c_201));
        mode201.setPosition(8);
        mode201.setData(RadioUtils.convertToRadio(jawaban201));

        ArisanFieldModel mode201_1 = FormText.getModel();
        mode201_1.setName("201_1");
        mode201_1.setLabel(Str(R.string.pertanyaan_c_201_1));
        mode201_1.setPosition(9);

        ArisanFieldModel model202 = FormRadio.getModel();
        String[] jawaban202 = {Str(R.string.dibawah_1_tahun)
                ,Str(R.string.sampai_3_tahun)
                ,Str(R.string.sampai_5_tahun)
                ,Str(R.string.sampai_10_tahun)
                ,Str(R.string.diatas_10_tahun)};
        model202.setLabel(Str(R.string.pertanyaan_c_202));
        model202.setName("202");
        model202.setPosition(10);
        model202.setData(RadioUtils.convertToRadio(jawaban202));

        ArisanFieldModel mode203 = FormDate.getModel();
        mode203.setName("203");
        mode203.setLabel(Str(R.string.pertanyaan_c_203));
        mode203.setPosition(11);

        ArisanFieldModel model204 = FormCheckbox.getModel();
        //**jika autoimun maka selesai(intent ke signatureActivity)
        //**jika lain-lain maka loncat ke 204_1 selain langsung k2 205
        String[] jawaban204 = {Str(R.string.diabetes)
                ,Str(R.string.hipertensi)
                ,Str(R.string.batu_ginjal)
                ,Str(R.string.infeksi_ginjal)
                ,Str(R.string.efek_samping)
                ,Str(R.string.operasi_atau_penyakit)
                ,Str(R.string.autoimun)
                ,Str(R.string.lain)
                ,Str(R.string.tidak_tahu)
        };
        model204.setLabel(Str(R.string.pertanyaan_c_204));
        model204.setPosition(12);
        model204.setName("204");
        model204.setData(jawaban204);

        ArisanFieldModel model204_1 = FormText.getModel();
        model204_1.setName("204_1");
        model204_1.setLabel(Str(R.string.pertanyaan_c_204_1));
        model204_1.setPosition(13);

        ArisanFieldModel model205 = FormCheckbox.getModel();
        String[] jawaban205 = {Str(R.string.masalah_ginjal)
                ,Str(R.string.rawat_inap)
                ,Str(R.string.batu_ginjal)
                ,Str(R.string.infeksi_ginjal)
                ,Str(R.string.sulit_berkemih)
                ,Str(R.string.bedah_urologi)
                ,Str(R.string.radiasi_di_perut)
                ,Str(R.string.kemoterapi_untuk_kanker)
                ,Str(R.string.keluarga_bermasalah_ginjal)
                ,Str(R.string.darah_dlm_urin)
                ,Str(R.string.urin_berbusa)
                ,Str(R.string.air_besar)
                ,Str(R.string.ada_darah_segar)
                ,Str(R.string.tidak)
        };
        model205.setLabel(Str(R.string.pertanyaan_c_205));
        model205.setPosition(14);
        model205.setName("205");
        model205.setData(jawaban205);

        ArisanFieldModel model205_1 = FormRadio.getModel();
        String[] jawaban205_1 = {Str(R.string.dibawah_1_tahun)
                ,Str(R.string.sampai_3_tahun)
                ,Str(R.string.sampai_5_tahun)
                ,Str(R.string.sampai_10_tahun)
                ,Str(R.string.diatas_10_tahun)
        };
        model205_1.setName("205_1");
        model205_1.setLabel(Str(R.string.pertanyaan_c_205_1));
        model205_1.setPosition(15);
        model205_1.setData(RadioUtils.convertToRadio(jawaban205_1));

        ArisanFieldModel model205_2 = FormRadio.getModel();
        //**jika tidak loncat ke 206
        String[] jawaban205_2 = {Str(R.string.ya)
                ,Str(R.string.tidak)
        };
        model205_2.setName("205_2");
        model205_2.setLabel(Str(R.string.pertanyaan_c_205_2));
        model205_2.setPosition(16);
        model205_2.setData(RadioUtils.convertToRadio(jawaban205_2));

        ArisanFieldModel model205_3 = FormRadio.getModel();
        String[] jawaban205_3 = {Str(R.string.dibawah_1_tahun)
                ,Str(R.string.mulai_1_sampai_5_tahun)
                ,Str(R.string.sampai_10_tahun)
                ,Str(R.string.diatas_10_tahun)
        };
        model205_3.setName("205_3");
        model205_3.setLabel(Str(R.string.pertanyaan_c_205_3));
        model205_3.setPosition(17);
        model205_3.setData(RadioUtils.convertToRadio(jawaban205_3));

        ArisanFieldModel model206 = FormRadio.getModel();
        //** jika kanker maka ke 206_0_1 selain itu 206_1
        String[] jawaban206 = {Str(R.string.hipertensi)
                ,Str(R.string.diabetes)
                ,Str(R.string.anemia)
                ,Str(R.string.kelainan_tulang)
                ,Str(R.string.stroke)
                ,Str(R.string.gagal_jantung)
                ,Str(R.string.serangan_jantung)
                ,Str(R.string.limfoma)
                ,Str(R.string.leukemia)
                ,Str(R.string.muntah_darah)
                ,Str(R.string.tukak_lambung)
                ,Str(R.string.operasi_pada_arteri)
                ,Str(R.string.kanker)
                ,Str(R.string.tidak)
        };
        model206.setName("206");
        model206.setLabel(Str(R.string.pertanyaan_c_206));
        model206.setPosition(18);
        model206.setData(RadioUtils.convertToRadio(jawaban206));

        ArisanFieldModel model206_0_1 = FormText.getModel();
        model206_0_1.setName("206_0_1");
        model206_0_1.setLabel(Str(R.string.pertanyaan_c_206_0_1));
        model206_0_1.setPosition(19);

        ArisanFieldModel model206_1 = FormRadio.getModel();
        String[] jawaban206_1 = {Str(R.string.dibawah_1_tahun)
                ,Str(R.string.sampai_3_tahun)
                ,Str(R.string.sampai_5_tahun)
                ,Str(R.string.sampai_10_tahun)
                ,Str(R.string.diatas_10_tahun)
        };
        model206_1.setName("206_1");
        model206_1.setLabel(Str(R.string.pertanyaan_c_206_1));
        model206_1.setPosition(20);
        model206_1.setData(RadioUtils.convertToRadio(jawaban206_1));

        ArisanFieldModel model206_2 = FormRadio.getModel();
        //**jika tidak langsung ke 206_3
        String[] jawaban206_2 = {Str(R.string.ya)
                ,Str(R.string.tidak)
        };
        model206_2.setName("206_2");
        model206_2.setLabel(Str(R.string.pertanyaan_c_206_2));
        model206_2.setPosition(21);
        model206_2.setData(RadioUtils.convertToRadio(jawaban206_2));

        ArisanFieldModel model206_2_1 = FormRadio.getModel();
        String[] jawaban206_2_1 = {Str(R.string.dibawah_1_tahun)
                ,Str(R.string.mulai_1_sampai_5_tahun)
                ,Str(R.string.sampai_10_tahun)
                ,Str(R.string.diatas_10_tahun)
        };
        model206_2_1.setName("206_2_1");
        model206_2_1.setLabel(Str(R.string.pertanyaan_c_206_2_1));
        model206_2_1.setPosition(22);
        model206_2_1.setData(RadioUtils.convertToRadio(jawaban206_2_1));

        ArisanFieldModel model206_3 = FormText.getModel();
        model206_3.setName("206_3");
        model206_3.setLabel(Str(R.string.pertanyaan_c_206_3));
        model206_3.setPosition(23);

        ArisanFieldModel model207 = FormRadio.getModel();
        //**jika tidak langsung ke 207_2
        String[] jawaban207 = {Str(R.string.ya)
                ,Str(R.string.tidak)
        };
        model207.setName("207");
        model207.setLabel(Str(R.string.pertanyaan_c_207));
        model207.setPosition(24);
        model207.setData(RadioUtils.convertToRadio(jawaban207));

        ArisanFieldModel model207_1 = FormRadio.getModel();
        String[] jawaban207_1 = {Str(R.string.dibawah_1_tahun)
                ,Str(R.string.mulai_1_sampai_5_tahun)
                ,Str(R.string.sampai_10_tahun)
                ,Str(R.string.diatas_10_tahun)
        };
        model207_1.setName("207_1");
        model207_1.setLabel(Str(R.string.pertanyaan_c_207_1));
        model207_1.setPosition(24);
        model207_1.setData(RadioUtils.convertToRadio(jawaban207_1));

        ArisanFieldModel model207_2 = FormRadio.getModel();
        String[] jawaban207_2 = {Str(R.string.ya)
                ,Str(R.string.tidak)
        };
        model207_2.setName("207_2");
        model207_2.setLabel(Str(R.string.pertanyaan_c_207_2));
        model207_2.setPosition(25);
        model207_2.setData(RadioUtils.convertToRadio(jawaban207_2));

        ArisanFieldModel model208 = FormRadio.getModel();
        String[] jawaban208 = {Str(R.string.mulai_1_sampai_4_gelas)
                ,Str(R.string.mulai_5_sampai_8_gelas)
                ,Str(R.string.diatas_8)
        };
        model208.setName("208");
        model208.setLabel(Str(R.string.pertanyaan_c_208));
        model208.setPosition(26);
        model208.setData(RadioUtils.convertToRadio(jawaban208));

        ArisanFieldModel model209 = FormCheckbox.getModel();
        String[] jawaban209 = {Str(R.string.air_minum_kemasan)
                ,Str(R.string.air_isi_ulang)
                ,Str(R.string.air_ledeng)
                ,Str(R.string.air_sumur)
                ,Str(R.string.lain), Model.OTHERS
        };
        model209.setName("209");
        model209.setLabel(Str(R.string.pertanyaan_c_209));
        model209.setPosition(27);
        model209.setData(jawaban209);

        ArisanFieldModel model210 = FormCheckbox.getModel();
        String[] jawaban210 = {Str(R.string.minuman_ringan)//tambah edit text sebutkan
                ,Str(R.string.minuman_isotonis)//tambah edit text sebutkan
                ,Str(R.string.minuman_berenergi) //tambah edit text sebutkan
                ,Str(R.string.minuman_beralkohol)
                ,Str(R.string.kopi_teh_coklat)
                ,Str(R.string.lain)
                ,Str(R.string.tidak)
        };
        model210.setName("210");
        model210.setLabel(Str(R.string.pertanyaan_c_210));
        model210.setPosition(28);
        model210.setData(jawaban210);

        ArisanFieldModel mode210_0_1 = FormText.getModel();
        mode210_0_1.setName("210_1");
        mode210_0_1.setLabel(Str(R.string.pertanyaan_c_210_0_1));
        mode210_0_1.setPosition(29);

        ArisanFieldModel model210_1 = FormCheckbox.getModel();
        String[] jawaban210_1 = {Str(R.string.lebih_dari_sekali)
                ,Str(R.string.setidaknya_sekali)
                ,Str(R.string.tiga_kali_perminggu)
                ,Str(R.string.satu_kali_perminggu)
                ,Str(R.string.satu_kali_perbulan)
        };
        model210_1.setName("210_1");
        model210_1.setLabel(Str(R.string.pertanyaan_c_210_1));
        model210_1.setPosition(30);
        model210_1.setData(jawaban210_1);

        ArisanFieldModel model210_2 = FormText.getModel();
        model210_2.setName("210_2");
        model210_2.setLabel(Str(R.string.pertanyaan_c_210_2));
        model210_2.setPosition(31);

        ArisanFieldModel model210_3 = FormRadio.getModel();
        String[] jawaban210_3 = {Str(R.string.dibawah_1_tahun)
                ,Str(R.string.sampai_3_tahun)
                ,Str(R.string.sampai_5_tahun)
                ,Str(R.string.sampai_10_tahun)
                ,Str(R.string.diatas_10_tahun)
        };
        model210_3.setLabel(Str(R.string.pertanyaan_c_210_3));
        model210_3.setName("210_3");
        model210_3.setPosition(32);
        model210_3.setData(RadioUtils.convertToRadio(jawaban210_3));

        ArisanFieldModel model210_4 = FormRadio.getModel();
        String[] jawaban210_4 = {Str(R.string.merasa_tidak_berefek)
                ,Str(R.string.ingin_mendapat_manfaat_lebih)
                ,Str(R.string.menyukai_rasanya)
                ,Str(R.string.lain), Model.OTHERS
        };
        model210_4.setName("210_4");
        model210_4.setLabel(Str(R.string.pertanyaan_c_210_4));
        model210_4.setPosition(33);
        model210_4.setData(RadioUtils.convertToRadio(jawaban210_4));

        ArisanFieldModel model211 = FormCheckbox.getModel();
        //** jika jawaban selain lain-lain loncat ke 211_1
        String[] jawaban211= {Str(R.string.fried_chicken)
                ,Str(R.string.burger)
                ,Str(R.string.mie_intan)
                ,Str(R.string.kecap_atau_makanan)
                ,Str(R.string.makanan_berlemak)
                ,Str(R.string.daging_olahan)
                ,Str(R.string.makanan_kaleng)
                ,Str(R.string.makanan_terfermentasi)
                ,Str(R.string.lain)
                ,Str(R.string.tidak)
        };
        model211.setLabel(Str(R.string.pertanyaan_c_211));
        model211.setName("211");
        model211.setPosition(34);
        model211.setData(jawaban211);

        ArisanFieldModel model211_0_1 = FormText.getModel();
        model211_0_1.setName("211_0_1");
        model211_0_1.setLabel(Str(R.string.pertanyaan_c_211_0_1));
        model211_0_1.setPosition(35);

        ArisanFieldModel model211_1 = FormRadio.getModel();
        String[] jawaban211_1 = {Str(R.string.lebih_dari_sekali)
                ,Str(R.string.setidaknya_sekali)
                ,Str(R.string.tiga_kali_perminggu)
                ,Str(R.string.satu_kali_perminggu)
                ,Str(R.string.satu_kali_perbulan)
        };
        model211_1.setLabel(Str(R.string.pertanyaan_c_211_1));
        model211_1.setName("211_1");
        model211_1.setPosition(36);
        model211_1.setData(RadioUtils.convertToRadio(jawaban211_1));

        ArisanFieldModel model211_2 = FormRadio.getModel();
        String[] jawaban211_2 = {Str(R.string.dibawah_1_tahun)
                ,Str(R.string.sampai_3_tahun)
                ,Str(R.string.sampai_5_tahun)
                ,Str(R.string.sampai_10_tahun)
                ,Str(R.string.diatas_10_tahun)
        };
        model211_2.setName("211_2");
        model211_2.setLabel(Str(R.string.pertanyaan_c_211_2));
        model211_2.setPosition(37);
        model211_2.setData(RadioUtils.convertToRadio(jawaban211_2));

        ArisanFieldModel model212 = FormRadio.getModel();
        String[] jawaban212 = {Str(R.string.tidak_pernah)
                ,Str(R.string.kadang_kadang)
                ,Str(R.string.sering)
                ,Str(R.string.hampir_setiap_kali_makan)
        };
        model212.setLabel(Str(R.string.pertanyaan_c_212));
        model212.setName("212");
        model212.setPosition(38);
        model212.setData(RadioUtils.convertToRadio(jawaban212));

        ArisanFieldModel model212_1 = FormText.getModel();
        model212_1.setLabel(Str(R.string.pertanyaan_c_212_1));
        model212_1.setName("212_1");
        model212_1.setPosition(39);

        ArisanFieldModel model213 = FormText.getModel();
        model213.setLabel(Str(R.string.pertanyaan_c_213));
        model213.setName("213");
        model213.setPosition(40);

        ArisanFieldModel model214 = FormText.getModel();
        model214.setLabel(Str(R.string.pertanyaan_c_214));
        model214.setName("214");
        model214.setPosition(41);

        ArisanFieldModel model215 = FormCheckbox.getModel();
        String[] jawaban215 = {Str(R.string.kendaraan_pribadi_roda_2)
                ,Str(R.string.kendaraan_pribadi_roda_4)
                ,Str(R.string.angkot)
                ,Str(R.string.ojek)
                ,Str(R.string.taksi)
                ,Str(R.string.lain), Model.OTHERS
        };
        model215.setLabel(Str(R.string.pertanyaan_c_215));
        model215.setName("215");
        model215.setPosition(42);
        model215.setData(jawaban215);

        ArisanFieldModel model216 = FormText.getModel();
        model216.setLabel(Str(R.string.pertanyaan_c_216));
        model216.setName("216");
        model216.setPosition(43);

        list.add(mode201);
//        list.add(mode201_1);
//        list.add(model202);
        list.add(mode203);
        list.add(model204);
        list.add(model205);
        list.add(model205_1);
        list.add(model205_2);
//        list.add(model205_3);
        list.add(model206);
        list.add(model206_1);
//        list.add(model206_0_1);
        list.add(model206_2);
//        list.add(model206_2_1);
        list.add(model206_3);
        list.add(model207);
//        list.add(model207_1);
        list.add(model207_2);
        list.add(model208);
        list.add(model209);
        list.add(model210);
        list.add(model210_1);
        list.add(model210_2);
        list.add(model210_3);
        list.add(model210_4);
        list.add(model211);
//        list.add(model211_0_1);
        list.add(model211_1);
        list.add(model211_2);
        list.add(model212);
        list.add(model212_1);
        list.add(model213);
        list.add(model214);
        list.add(model215);
        list.add(model216);


        data.put("201",mode201);
        data.put("201_1",mode201_1);
        data.put("202",model202);
        data.put("203",mode203);
        data.put("204",model204);
        data.put("204_1",model204_1);
        data.put("205",model205);
        data.put("205_1",model205_1);
        data.put("205_2",model205_2);
        data.put("205_3",model205_3);
        data.put("206",model206);
        data.put("206_1",model206_1);
        data.put("206_0_1",model206_0_1);
        data.put("206_2",model206_2);
        data.put("206_2_1",model206_2_1);
        data.put("206_3",model206_3);
        data.put("207",model207);
        data.put("207_1",model207_1);
        data.put("207_2",model207_2);
        data.put("208",model208);
        data.put("209",model209);
        data.put("210",model210);
        data.put("210_1",model210_1);
        data.put("210_2",model210_2);
        data.put("210_3",model210_3);
        data.put("210_4",model210_4);
        data.put("211",model211);
        data.put("211_0_1",model211_0_1);
        data.put("211_1",model211_1);
        data.put("211_2",model211_2);
        data.put("212",model212);
        data.put("212_1",model212_1);
        data.put("213",model213);
        data.put("214",model214);
        data.put("215",model215);
        data.put("216",model216);

        return list;
    }

    public List<ArisanFieldModel> all_3() {
        List<ArisanFieldModel> list = new ArrayList<>();

        ArisanFieldModel model301 = FormRadio.getModel();
        String[] jawaban301 = {Str(R.string.ya_sebutkan_minimal_3)////tambah edit text sebutkan 3
                ,Str(R.string.tidak_tahu)
        };
        model301.setLabel(Str(R.string.pertanyaan_c_301));
        model301.setName("301");
        model301.setPosition(40);
        model301.setData(RadioUtils.convertToRadio(jawaban301));

        ArisanFieldModel model301_1 = FormText.getModel();
        model301_1.setLabel(Str(R.string.pertanyaan_c_301_1));
        model301_1.setName("301_1");
        model301_1.setPosition(41);

        ArisanFieldModel model302 = FormCheckbox.getModel();
        String[] jawaban302 = {Str(R.string.tv)
                ,Str(R.string.radio)
                ,Str(R.string.surat_kabar)
                ,Str(R.string.majalah)
                ,Str(R.string.internet)
                ,Str(R.string.media_sosial)
                ,Str(R.string.orang_lain)
                ,Str(R.string.lain), Model.OTHERS
        };
        model302.setLabel(Str(R.string.pertanyaan_c_302));
        model302.setName("302");
        model302.setPosition(42);
        model302.setData(jawaban302);

        ArisanFieldModel model303 = FormRadio.getModel();
        //** jika tidak lompat 304
        String[] jawaban303 = {Str(R.string.ya)////tambah edit text
                ,Str(R.string.tidak_tahu)
        };
        model303.setLabel(Str(R.string.pertanyaan_c_303));
        model303.setName("303");
        model303.setPosition(43);
        model303.setData(RadioUtils.convertToRadio(jawaban303));

        ArisanFieldModel model303_1 = FormText.getModel();
        model303_1.setLabel(Str(R.string.pertanyaan_c_303_1));
        model303_1.setName("303_1");
        model303_1.setPosition(44);

        ArisanFieldModel model304 = FormRadio.getModel();
        String[] jawaban304 = {Str(R.string.ya), Model.OTHERS////tambah edit text
                ,Str(R.string.tidak_tahu)
        };
        model304.setLabel(Str(R.string.pertanyaan_c_304));
        model304.setName("304");
        model304.setPosition(45);
        model304.setData(RadioUtils.convertToRadio(jawaban304));

        ArisanFieldModel model304_1 = FormText.getModel();
        model304_1.setLabel(Str(R.string.pertanyaan_c_304_1));
        model304_1.setName("304_1");
        model304_1.setPosition(46);

        ArisanFieldModel model305 = FormRadio.getModel();
        String[] jawaban305 = {Str(R.string.mengatasi_kel)
                ,Str(R.string.mengatai_kan)
                ,Str(R.string.menambah_stamina)
                ,Str(R.string.lain), Model.OTHERS
        };
        model305.setLabel(Str(R.string.pertanyaan_c_305));
        model305.setName("305");
        model305.setPosition(47);
        model305.setData(RadioUtils.convertToRadio(jawaban305));

        ArisanFieldModel model306 = FormRadio.getModel();
        //**jika tidak lompat ke poin 307
        String[] jawaban306 = {Str(R.string.ya), Model.OTHERS////tambah edit text
                ,Str(R.string.tidak)
        };
        model306.setLabel(Str(R.string.pertanyaan_c_306));
        model306.setName("306");
        model306.setPosition(48);
        model306.setData(RadioUtils.convertToRadio(jawaban306));

        ArisanFieldModel model306_1 = FormText.getModel();
        model306_1.setLabel(Str(R.string.pertanyaan_c_306_1));
        model306_1.setName("306_1");
        model306_1.setPosition(49);

        ArisanFieldModel model307 = FormRadio.getModel();
        //jika tidak lompat ke 308
        String[] jawaban307 = {Str(R.string.ya)
                ,Str(R.string.tidak)
        };
        model307.setLabel(Str(R.string.pertanyaan_c_307));
        model307.setName("307");
        model307.setPosition(50);
        model307.setData(RadioUtils.convertToRadio(jawaban307));

        ArisanFieldModel model307_1 = FormText.getModel();
        model307_1.setLabel(Str(R.string.pertanyaan_c_307_1));
        model307_1.setName("307_1");
        model307_1.setPosition(51);

        ArisanFieldModel model308 = FormRadio.getModel();
        //**jika tidak lompat ke 309
        String[] jawaban308 = {Str(R.string.ya)
                ,Str(R.string.tidak)
        };
        model308.setLabel(Str(R.string.pertanyaan_c_308));
        model308.setName("308");
        model308.setPosition(52);
        model308.setData(RadioUtils.convertToRadio(jawaban308));

        ArisanFieldModel model308_1 = FormText.getModel();
        model308_1.setLabel(Str(R.string.pertanyaan_c_308_1));
        model308_1.setName("308_1");
        model308_1.setPosition(53);

        ArisanFieldModel model309 = FormRadio.getModel();
        String[] jawaban309 = {Str(R.string.warung_jamu)
                ,Str(R.string.tukang_jamu_keliling)
                ,Str(R.string.supermarket)
                ,Str(R.string.warung)
                ,Str(R.string.toko_online)
                ,Str(R.string.toko_obat_china)
                ,Str(R.string.toko_obat)
                ,Str(R.string.apotek)
                ,Str(R.string.lain),Model.OTHERS
        };
        model309.setLabel(Str(R.string.pertanyaan_c_309));
        model309.setName("309");
        model309.setPosition(54);
        model309.setData(RadioUtils.convertToRadio(jawaban309));

        list.add(model301);
//        list.add(model301_1);
        list.add(model302);
        list.add(model303);
//        list.add(model303_1);
        list.add(model304);
//        list.add(model304_1);
        list.add(model305);
        list.add(model306);
//        list.add(model306_1);
        list.add(model307);
//        list.add(model307_1);
        list.add(model308);
//        list.add(model308_1);
        list.add(model309);


        data.put("301",model301);
        data.put("301_1",model301_1);
        data.put("302",model302);
        data.put("303",model303);
        data.put("303_1",model303_1);
        data.put("304",model304);
        data.put("304_1",model304_1);
        data.put("305",model305);
        data.put("306",model306);
        data.put("306_1",model306_1);
        data.put("307",model307);
        data.put("307_1",model307_1);
        data.put("308",model308);
        data.put("308_1",model308);
        data.put("309",model309);

        return list ;
    }

    public List<ArisanFieldModel> all_4() {
        List<ArisanFieldModel> list = new ArrayList<>();

        ArisanFieldModel model401 = FormCheckbox.getModel();
        String[] jawaban401 = {Str(R.string.pusing)
                ,Str(R.string.encok)
                ,Str(R.string.rematik)
                ,Str(R.string.kelelahan_kronis)
                ,Str(R.string.lain)
                ,Str(R.string.tidak)
        };
        model401.setLabel(Str(R.string.pertanyaan_c_401));
        model401.setName("401");
        model401.setPosition(49);
        model401.setData(jawaban401);

        ArisanFieldModel model401_1 = FormText.getModel();
        model401_1.setLabel(Str(R.string.pertanyaan_c_401_1));
        model401_1.setName("401_1");
        model401_1.setPosition(50);

        ArisanFieldModel model402 = FormCheckbox.getModel();
        //Jika tidak lain-lain dan obt china maka lompat ke 402_1
        String[] jawaban402 = {Str(R.string.obat_pereda_sakit)
                ,Str(R.string.obat_anti_inflamasi)
                ,Str(R.string.obat_stelan)
                ,Str(R.string.obat_paten)
                ,Str(R.string.obat_china)
                ,Str(R.string.jamu_kemasan_rumah)
                ,Str(R.string.jamu_kemasan_warung)
                ,Str(R.string.jamu_gendong)
                ,Str(R.string.jamu_stelan)
                ,Str(R.string.minuman_suplemen)
                ,Str(R.string.vitamin_dosis_tinggi)
                ,Str(R.string.lain)
                ,Str(R.string.tidak)

        };
        model402.setName("402");
        model402.setLabel(Str(R.string.pertanyaan_c_402));
        model402.setPosition(51);
        model402.setData(jawaban402);

        ArisanFieldModel model402_0_1 = FormText.getModel();
        model402_0_1.setLabel(Str(R.string.pertanyaan_c_402_0_1));
        model402_0_1.setName("402_0_1");
        model402_0_1.setPosition(52);

        ArisanFieldModel model402_1 = FormRadio.getModel();
        String[] jawaban402_1 = {Str(R.string.setidaknya_sekali)
                ,Str(R.string.dua_kali_sehari)
                ,Str(R.string.tiga_kali_sehari)
                ,Str(R.string.lebih_dari_tiga_kali_sehari)
                ,Str(R.string.tiga_kali_perminggu)
                ,Str(R.string.satu_kali_perminggu)
                ,Str(R.string.satu_kali_perbulan)
                ,Model.OTHERS
        };
        model402_1.setLabel(Str(R.string.pertanyaan_c_402_1));
        model402_1.setName("402_1");
        model402_1.setPosition(53);
        model402_1.setData(RadioUtils.convertToRadio(jawaban402_1));

        ArisanFieldModel model402_2 = FormText.getModel();
        model402_2.setName("402_2");
        model402_2.setLabel(Str(R.string.pertanyaan_c_402_2));
        model402_2.setPosition(54);

        ArisanFieldModel model402_3 = FormRadio.getModel();
        String[] jawaban402_3 = {Str(R.string.dibawah_1_tahun)
                ,Str(R.string.sampai_3_tahun)
                ,Str(R.string.sampai_5_tahun)
                ,Str(R.string.sampai_10_tahun)
                ,Str(R.string.diatas_10_tahun)
        };
        model402_3.setLabel(Str(R.string.pertanyaan_c_402_3));
        model402_3.setName("402_3");
        model402_3.setPosition(55);
        model402_3.setData(RadioUtils.convertToRadio(jawaban402_3));

        ArisanFieldModel model402_4 = FormRadio.getModel();
        //**jika tidak lompat ke 402_5
        String[] jawaban402_4 = {Str(R.string.ya)
                ,Str(R.string.tidak)
        };
        model402_4.setLabel(Str(R.string.pertanyaan_c_402_4));
        model402_4.setName("402_4");
        model402_4.setPosition(56);
        model402_4.setData(RadioUtils.convertToRadio(jawaban402_4));

        ArisanFieldModel model402_4_1 = FormRadio.getModel();
        String[] jawaban402_4_1 = {Str(R.string.satu_hari)
                ,Str(R.string.dua_hari)
                ,Str(R.string.tiga_sampai_4_hari)
                ,Str(R.string.lima_sampai_7_hari)
                ,Model.OTHERS
        };
        model402_4_1.setName("402_4_1");
        model402_4_1.setLabel(Str(R.string.pertanyaan_c_402_4_1));
        model402_4_1.setPosition(57);
        model402_4_1.setData(RadioUtils.convertToRadio(jawaban402_4_1));

        ArisanFieldModel model402_5 = FormRadio.getModel();
        String[] jawaban402_5 = {Str(R.string.ya)
                ,Str(R.string.tidak)
        };
        model402_5.setLabel(Str(R.string.pertanyaan_c_402_5));
        model402_5.setName("402_5");
        model402_5.setPosition(58);
        model402_5.setData(RadioUtils.convertToRadio(jawaban402_5));

        ArisanFieldModel model403 = FormText.getModel();
        model403.setName("403");
        model403.setLabel(Str(R.string.pertanyaan_c_403));
        model403.setPosition(59);

        ArisanFieldModel model404 = FormCheckbox.getModel();
        //**jika selain lain maka lompat ke 404_1
        String[] jawaban404 = {Str(R.string.suplemen_herbal)
                ,Str(R.string.herbal_china)
                ,Str(R.string.jamu_kemasan_warung)
                ,Str(R.string.jamu_kemasan_rumah)
                ,Str(R.string.jamu_gendong)
                ,Str(R.string.jamu_gendong_racikan)
                ,Str(R.string.obat_racikan_bukan_buatan_pabrik)
                ,Str(R.string.multivitamin_dan_mineral)
                ,Str(R.string.lain)
                ,Str(R.string.tidak)
        };
        model404.setName("404");
        model404.setLabel(Str(R.string.pertanyaan_c_404));
        model404.setPosition(60);
        model404.setData(jawaban404);

        ArisanFieldModel model404_0_1 = FormText.getModel();
        model404_0_1.setName("404_0_1");
        model404_0_1.setLabel(Str(R.string.pertanyaan_c_404_0_1));
        model404_0_1.setPosition(61);

        ArisanFieldModel model404_1 = FormRadio.getModel();
        String[] jawaban404_1 = {Str(R.string.lebih_dari_sekali)
                ,Str(R.string.setidaknya_sekali)
                ,Str(R.string.tiga_kali_perminggu)
                ,Str(R.string.satu_kali_perminggu)
                ,Str(R.string.satu_kali_perbulan)
        };
        model404_1.setLabel(Str(R.string.pertanyaan_c_404_1));
        model404_1.setName("404_1");
        model404_1.setPosition(62);
        model404_1.setData(RadioUtils.convertToRadio(jawaban404_1));

        ArisanFieldModel model404_2 = FormRadio.getModel();
        String[] jawaban404_2 = {Str(R.string.dibawah_1_tahun)
                ,Str(R.string.sampai_3_tahun)
                ,Str(R.string.sampai_5_tahun)
                ,Str(R.string.sampai_10_tahun)
                ,Str(R.string.diatas_10_tahun)
                ,Model.OTHERS//tambah edit text
        };
        model404_2.setLabel(Str(R.string.pertanyaan_c_404_2));
        model404_2.setName("404_2");
        model404_2.setPosition(63);
        model404_2.setData(RadioUtils.convertToRadio(jawaban404_2));

        ArisanFieldModel model404_3 = FormRadio.getModel();
        String[] jawaban404_3 = {Str(R.string.warung_jamu)
                ,Str(R.string.tukang_jamu_keliling)
                ,Str(R.string.supermarket)
                ,Str(R.string.warung)
                ,Str(R.string.toko_online)
                ,Str(R.string.toko_obat_china)
                ,Str(R.string.toko_obat)
                ,Str(R.string.apotek)
                ,Model.OTHERS
        };
        model404_3.setLabel(Str(R.string.pertanyaan_c_404_3));
        model404_3.setName("404_3");
        model404_3.setPosition(64);
        model404_3.setData(RadioUtils.convertToRadio(jawaban404_3));

        list.add(model401);
        list.add(model401_1);
        list.add(model402);
//        list.add(model402_0_1);
        list.add(model402_1);
        list.add(model402_2);
        list.add(model402_3);
        list.add(model402_4);
//        list.add(model402_4_1);
        list.add(model402_5);
        list.add(model403);
        list.add(model404);
//        list.add(model404_0_1);
        list.add(model404_1);
        list.add(model404_2);
        list.add(model404_3);

        data.put("401",model401);
        data.put("401_1",model401_1);
        data.put("402",model402);
        data.put("402_0_1",model402_0_1);
        data.put("402_1",model402_1);
        data.put("402_2",model402_2);
        data.put("402_3",model402_3);
        data.put("402_4",model402_4);
        data.put("402_4_1",model402_4_1);
        data.put("402_5",model402_5);
        data.put("403",model403);
        data.put("404",model404);
        data.put("404_0_1",model404_0_1);
        data.put("404_1",model404_1);
        data.put("404_1",model404_2);
        data.put("404_3",model404_3);

        return list;
    }

    public List<ArisanFieldModel> all_5() {
        List<ArisanFieldModel> list = new ArrayList<>();

        ArisanFieldModel model501 = FormCheckbox.getModel();
        //**selain jawaban lain lompat ke 502
        String[] jawaban501 = {Str(R.string.mengurus_diri_sendiri)
                ,Str(R.string.kesana_kemari)
                ,Str(R.string.melakukan_pekerjaan_rumah)
                ,Str(R.string.belanja)
                ,Str(R.string.mengantar_anak_sekolah)
                ,Str(R.string.mengantar_anda_ke_rumah_sakit)
                ,Str(R.string.lain)
                ,Str(R.string.tidak_pernah)
        };
        model501.setLabel(Str(R.string.pertanyaan_c_501));
        model501.setName("501");
        model501.setPosition(63);
        model501.setData(jawaban501);

        ArisanFieldModel model501_1 = FormText.getModel();
        model501_1.setName("501_1");
        model501_1.setLabel(Str(R.string.pertanyaan_c_501_1));
        model501_1.setPosition(64);

        ArisanFieldModel model502 = FormText.getModel();
        model502.setName("502");
        model502.setLabel(Str(R.string.pertanyaan_c_502));
        model502.setPosition(65);

        ArisanFieldModel model503 = FormCheckbox.getModel();
        String[] jawaban503 = {Str(R.string.pekerjaan)
                ,Str(R.string.pendidikan)
                ,Str(R.string.pergaulan)
                ,Str(R.string.tidak_pernah)
        };
        model503.setLabel(Str(R.string.pertanyaan_c_503));
        model503.setName("503");
        model503.setPosition(66);
        model503.setData(jawaban503);

        ArisanFieldModel model504 = FormRadio.getModel();
        String[] jawaban504 = {Str(R.string.sejahtera)
                ,Str(R.string.cukup_nyaman)
                ,Str(R.string.pas_pasan)
                ,Str(R.string.miskin)
        };
        model504.setName("504");
        model504.setLabel(Str(R.string.pertanyaan_c_504));
        model504.setPosition(67);
        model504.setData(RadioUtils.convertToRadio(jawaban504));

        ArisanFieldModel model505 = FormRadio.getModel();
        String[] jawaban505 = {Str(R.string.saya_tidak_kesulitan)
                ,Str(R.string.saya_sedikit_kesulitan)
                ,Str(R.string.saya_cukup_kesulitan)
                ,Str(R.string.saya_sangat_kesulitan)
                ,Str(R.string.saya_tidak_bisa)
        };
        model505.setLabel(Str(R.string.pertanyaan_c_505));
        model505.setName("505");
        model505.setPosition(68);
        model505.setData(RadioUtils.convertToRadio(jawaban505));

        ArisanFieldModel model506 = FormRadio.getModel();
        String[] jawaban506 = {Str(R.string.saya_tidak_kesulitan)
                ,Str(R.string.saya_sedikit_kesulitan)
                ,Str(R.string.saya_cukup_kesulitan)
                ,Str(R.string.saya_sangat_kesulitan)
                ,Str(R.string.saya_tidak_bisa)
        };
        model506.setLabel(Str(R.string.pertanyaan_c_506));
        model506.setName("506");
        model506.setPosition(69);
        model506.setData(RadioUtils.convertToRadio(jawaban506));

        ArisanFieldModel model507 = FormRadio.getModel();
        String[] jawaban507 = {Str(R.string.saya_tidak_kesulitan)
                ,Str(R.string.saya_sedikit_kesulitan)
                ,Str(R.string.saya_cukup_kesulitan)
                ,Str(R.string.saya_sangat_kesulitan)
                ,Str(R.string.saya_tidak_bisa)
        };
        model507.setLabel(Str(R.string.pertanyaan_c_507));
        model507.setName("507");
        model507.setPosition(70);
        model507.setData(RadioUtils.convertToRadio(jawaban507));

        ArisanFieldModel model508 = FormRadio.getModel();
        String[] jawaban508 = {Str(R.string.saya_tidak_merasa)
                ,Str(R.string.saya_merasa)
                ,Str(R.string.saya_merasa_cukup)
                ,Str(R.string.saya_merasa_sangat)
                ,Str(R.string.saya_merasa_amat_sangat)
        };
        model508.setLabel(Str(R.string.pertanyaan_c_508));
        model508.setName("508");
        model508.setPosition(71);
        model508.setData(RadioUtils.convertToRadio(jawaban508));

        ArisanFieldModel model509 = FormRadio.getModel();
        String[] jawaban509 = {Str(R.string.saya_tidak_merasa)
                ,Str(R.string.saya_merasa)
                ,Str(R.string.saya_merasa_cukup)
                ,Str(R.string.saya_merasa_sangat)
                ,Str(R.string.saya_merasa_amat_sangat)
        };
        model509.setName("509");
        model509.setLabel(Str(R.string.pertanyaan_c_509));
        model509.setPosition(72);
        model509.setData(RadioUtils.convertToRadio(jawaban509));

        ArisanFieldModel model510 = FormSlider.getModel();
        model510.setName("510");
        model510.setLabel(Str(R.string.pertanyaan_c_510));
        model510.setPosition(73);


        list.add(model501);
//        list.add(model501_1);
        list.add(model502);
        list.add(model503);
        list.add(model504);
        list.add(model505);
        list.add(model506);
        list.add(model507);
        list.add(model508);
        list.add(model509);
        list.add(model510);


        data.put("501",model501);
        data.put("501_1",model501_1);
        data.put("502",model502);
        data.put("503",model503);
        data.put("504",model504);
        data.put("505",model505);
        data.put("506",model506);
        data.put("507",model507);
        data.put("508",model508);
        data.put("509",model509);
        data.put("510",model510);


        return list;
    }

    public List<ArisanFieldModel> all_6() {
        List<ArisanFieldModel> list = new ArrayList<>();

        ArisanFieldModel model601 = FormRadio.getModel();
        String[] jawaban601 = {Str(R.string.bekerja)//tambah edit text
                ,Str(R.string.tidak_bekerja)
        };
        model601.setLabel(Str(R.string.pertanyaan_c_601));
        model601.setName("601");
        model601.setPosition(72);
        model601.setData(RadioUtils.convertToRadio(jawaban601));

        ArisanFieldModel model601_1 = FormNumber.getModel();
        model601_1.setName("601_1");
        model601_1.setLabel(Str(R.string.pertanyaan_c_601_1));
        model601_1.setPosition(73);

        ArisanFieldModel model602 = FormRadio.getModel();
        String[] jawaban602 = {Str(R.string.bekerja)//tambah edit text
                ,Str(R.string.tidak_bekerja)
        };
        model602.setLabel(Str(R.string.pertanyaan_c_602));
        model602.setName("602");
        model602.setPosition(74);
        model602.setData(RadioUtils.convertToRadio(jawaban602));

        ArisanFieldModel model602_1 = FormNumber.getModel();
        model602_1.setName("602_1");
        model602_1.setLabel(Str(R.string.pertanyaan_c_602_1));
        model602_1.setPosition(75);

        ArisanFieldModel model603 = FormRadio.getModel();
        String[] jawaban603 = {Str(R.string.ya)//tambah edit text
                ,Str(R.string.tidak_pernah)
        };
        model603.setLabel(Str(R.string.pertanyaan_c_603));
        model603.setName("603");
        model603.setPosition(76);
        model603.setData(RadioUtils.convertToRadio(jawaban603));

        ArisanFieldModel model604 = FormRadio.getModel();
        String[] jawaban604 = {Str(R.string.hasil_panen)
                ,Str(R.string.hasil_ternak)
                ,Str(R.string.bisnis_keluarga)
                ,Str(R.string.gaji)
                ,Str(R.string.kiriman_dan_pemberian)
                ,Str(R.string.lain)
        };
        model604.setLabel(Str(R.string.pertanyaan_c_604));
        model604.setName("604");
        model604.setPosition(77);
        model604.setData(RadioUtils.convertToRadio(jawaban604));

        ArisanFieldModel model605 = FormRadio.getModel();
        String[] jawaban605 = {Str(R.string.dibawah1juta)
                ,Str(R.string.sampai2juta)
                ,Str(R.string.sampai3juta)
                ,Str(R.string.sampai4juta)
                ,Str(R.string.sampai5juta)
                ,Str(R.string.sampai6juta)
                ,Str(R.string.sampai7juta)
                ,Str(R.string.sampai8juta)
                ,Str(R.string.diatas8juta)
                ,Str(R.string.tidak_tahu)
        };
        model605.setLabel(Str(R.string.pertanyaan_c_605));
        model605.setName("605");
        model605.setPosition(78);
        model605.setData(RadioUtils.convertToRadio(jawaban605));

        ArisanFieldModel model606 = FormCheckbox.getModel();
        String[] jawaban606 = {Str(R.string.tanah_atau_lahan)
                ,Str(R.string.rumah_atau_apartemen)
                ,Str(R.string.mobil)
                ,Str(R.string.motor)
                ,Str(R.string.instalasi_listrik)
                ,Str(R.string.anak_usia_sekolah)
        };
        model606.setLabel(Str(R.string.pertanyaan_c_606));
        model606.setName("606");
        model606.setPosition(79);
        model606.setData(jawaban606);

        ArisanFieldModel model607 = FormRadio.getModel();
        String[] jawaban607 = {Str(R.string.dibawah2juta)
                ,Str(R.string.dua_sampai4juta)
                ,Str(R.string.empat_sampai6juta)
                ,Str(R.string.enam_sampai8juta)
                ,Str(R.string.delapan_sampai10juta)
                ,Str(R.string.diatas10juta)
        };
        model607.setLabel(Str(R.string.pertanyaan_c_607));
        model607.setName("607");
        model607.setPosition(80);
        model607.setData(RadioUtils.convertToRadio(jawaban607));

        ArisanFieldModel model608 = FormCheckbox.getModel();
        String[] jawaban608 = {Str(R.string.tak_mampu_bayar_tagihan_listrik)
                ,Str(R.string.tak_mampu_bayar_uang_sewa)
                ,Str(R.string.tak_mampu_bayar_biaya_medis)
                ,Str(R.string.tak_mampu_bayar_iuran_asuransi)
                ,Str(R.string.tak_mampu_bayar_biaya_sekolah)
                ,Str(R.string.tak_mampu_bayar_biaya_transportasi)
                ,Str(R.string.tidak_pernah_tak_mampu_bayar)
        };
        model608.setLabel(Str(R.string.pertanyaan_c_608));
        model608.setName("608");
        model608.setPosition(81);
        model608.setData(jawaban608);

        ArisanFieldModel model609 = FormCheckbox.getModel();
        String[] jawaban609 = {Str(R.string.tak_mampu_bayar_tagihan_listrik)
                ,Str(R.string.tak_mampu_bayar_uang_sewa)
                ,Str(R.string.tak_mampu_bayar_biaya_medis)
                ,Str(R.string.tak_mampu_bayar_iuran_asuransi)
                ,Str(R.string.tak_mampu_bayar_biaya_sekolah)
                ,Str(R.string.tak_mampu_bayar_biaya_transportasi)
                ,Str(R.string.tidak_pernah_tak_mampu_bayar)
        };
        model609.setLabel(Str(R.string.pertanyaan_c_609));
        model609.setName("609");
        model609.setPosition(82);
        model609.setData(jawaban609);

        ArisanFieldModel model610 = FormCheckbox.getModel();
        String[] jawaban610 = {Str(R.string.pindah_rumah)
                ,Str(R.string.menggunakan_tabungan)
                ,Str(R.string.minta_bantuan_keuangan_dari_teman)
                ,Str(R.string.minta_bantuan_keuangan_dari_pemerintah)
                ,Str(R.string.pinjam_uang_dengan_jaminan_pribadi)
                ,Str(R.string.jual_aset_atau_harta)
                ,Str(R.string.melakukan_strategi_lain)
                ,Str(R.string.tidak_pernah_satupun_diatas)
        };
        model610.setLabel(Str(R.string.pertanyaan_c_610));
        model610.setName("610");
        model610.setPosition(83);
        model610.setData(jawaban610);

        ArisanFieldModel model611 = FormCheckbox.getModel();
        String[] jawaban611 = {Str(R.string.pindah_rumah)
                ,Str(R.string.menggunakan_tabungan)
                ,Str(R.string.minta_bantuan_keuangan_dari_teman)
                ,Str(R.string.minta_bantuan_keuangan_dari_pemerintah)
                ,Str(R.string.pinjam_uang_dengan_jaminan_pribadi)
                ,Str(R.string.jual_aset_atau_harta)
                ,Str(R.string.melakukan_strategi_lain)
                ,Str(R.string.tidak_pernah_satupun_diatas)
        };
        model611.setLabel(Str(R.string.pertanyaan_c_611));
        model611.setName("611");
        model611.setPosition(84);
        model611.setData(jawaban611);

        ArisanFieldModel model612 = FormText.getModel();
        model612.setLabel(Str(R.string.pertanyaan_c_612));
        model612.setName("612");
        model612.setPosition(85);

        list.add(model601);
//        list.add(model601_1);
        list.add(model602);
//        list.add(model602_1);
        list.add(model603);
        list.add(model604);
        list.add(model605);
        list.add(model606);
        list.add(model607);
        list.add(model608);
        list.add(model609);
        list.add(model610);
        list.add(model611);
        list.add(model612);

        data.put("601",model601);
        data.put("601_1",model601_1);
        data.put("602",model602);
        data.put("602_1",model602_1);
        data.put("603",model603);
        data.put("604",model604);
        data.put("605",model605);
        data.put("606",model606);
        data.put("607",model607);
        data.put("608",model608);
        data.put("609",model609);
        data.put("610",model610);
        data.put("611",model611);
        data.put("612",model612);
        return list;
    }

    public ArisanFieldModel getMap(String code){
        return data.get(code);
    }

}
