package com.github.arisanform.model;

import com.github.arisan.annotation.Form;

import java.util.List;

public class User {
    @Form(label = "Nama Peminjam")
    private String name;

    @Form(label = "Daftar Buku",type = Form.ONETOMANY, relation = Book.class)
    private List<Book> books;

    public User() {
    }

    public User(String name, List<Book> books) {
        this.name = name;
        this.books = books;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
