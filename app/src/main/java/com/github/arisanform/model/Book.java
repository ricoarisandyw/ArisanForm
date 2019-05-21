package com.github.arisanform.model;

import com.github.arisan.annotation.Form;

public class Book {
    @Form(label = "Nama Buku", type = Form.TEXT)
    private String book_name;

    @Form(label = "Lama Peminjaman",type = Form.NUMBER)
    private int long_day;

    public Book() {
    }

    public Book(String book_name, int long_day) {
        this.book_name = book_name;
        this.long_day = long_day;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public int getLong_day() {
        return long_day;
    }

    public void setLong_day(int long_day) {
        this.long_day = long_day;
    }
}
