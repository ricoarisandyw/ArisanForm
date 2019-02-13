package com.github.arisanform.model;

import com.github.arisan.annotation.Form;

import java.util.Date;

/**
 * Created by wijaya on 5/5/2018.
 */

public class Todo {

    @Form(label="Masukan Judul *",position = 1)
    String title;
    @Form()
    String note;
    @Form(type = Form.NUMBER)
    int quantity;
    @Form(type = Form.BOOLEAN,position = 3)
    boolean urgent;
    @Form(type = Form.BOOLEAN,position = 2)
    boolean important;
    @Form(type=Form.DATE,label = "Start Date")
    Date startDate;
    @Form(type=Form.SPINNER)
    String category;
    @Form(type = Form.FILE,position = 0)
    String attachment;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

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
