package com.github.arisanform.model;

import com.github.arisan.annotation.Form;

public class KK {
    @Form(type = Form.SEARCH)
    String search;

    @Form(label = "Name")
    String name;

    public KK() {
    }

    public KK(String search, String name) {
        this.search = search;
        this.name = name;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
