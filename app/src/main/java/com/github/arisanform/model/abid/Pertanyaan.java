package com.github.arisanform.model.abid;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Pertanyaan extends RealmObject implements Serializable {

    private String label;
    private String pertanyaan;
    private String jawaban;

    public Pertanyaan() {
    }

    public Pertanyaan(String label , String pertanyaan, String jawaban) {
        this.label = label;
        this.pertanyaan = pertanyaan;
        this.jawaban = jawaban;
    }
    public Pertanyaan(Pertanyaan pertanyaan) {
        this.label = pertanyaan.getLabel();
        this.pertanyaan = pertanyaan.getPertanyaan();
        this.jawaban = pertanyaan.getJawaban();
    }


    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getPertanyaan() {
        return pertanyaan;
    }

    public void setPertanyaan(String pertanyaan) {
        this.pertanyaan = pertanyaan;
    }

    public String getJawaban() {
        return jawaban;
    }

    public void setJawaban(String jawaban) {
        this.jawaban = jawaban;
    }
}
