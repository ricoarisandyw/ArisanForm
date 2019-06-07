package com.github.arisanform.model;

import com.github.arisan.annotation.AnnotationProcessor;
import com.github.arisan.annotation.Form;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

public class User extends RealmObject {
    @Form(label = "Nama Peminjam",position = 1)
    private String name;

    @Form(label = "Foto Peminjam",position = 2,type = Form.FILE)
    private String photo;

    @Form(label = "Daftar Buku",type = Form.ONETOMANY, relation = Book.class)
    private RealmList<Book> books;

    public User() {
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RealmList<Book> getBooks() {
        return books;
    }

    public void setBooks(RealmList<Book> books) {
        this.books = books;
    }
}
