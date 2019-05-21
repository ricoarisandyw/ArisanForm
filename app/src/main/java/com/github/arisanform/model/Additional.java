package com.github.arisanform.model;

import com.github.arisan.annotation.Form;

public class Additional {
    @Form
    private String name;
    @Form(type = Form.NUMBER)
    private int quantity;

    public Additional() {
    }

    public Additional(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
