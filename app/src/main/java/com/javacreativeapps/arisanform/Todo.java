package com.javacreativeapps.arisanform;

import com.javacreativeapps.arisan.annotation.Form;

/**
 * Created by wijaya on 5/5/2018.
 */

public class Todo {

    @Form(position = 0)
    String title;
    @Form(position = 1)
    String note;
    @Form(type = Form.NUMBER)
    int quantity;
    @Form(type = Form.BOOLEAN,position = 3)
    boolean urgent;
    @Form(type = Form.BOOLEAN,position = 2)
    boolean important;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isUrgent() {
        return urgent;
    }

    public void setUrgent(boolean urgent) {
        this.urgent = urgent;
    }

    public boolean isImportant() {
        return important;
    }

    public void setImportant(boolean important) {
        this.important = important;
    }
}
