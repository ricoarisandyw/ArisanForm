package com.github.arisanform.model;

import com.github.arisan.annotation.Form;


import com.github.arisan.annotation.Form;
import com.github.arisan.helper.FieldAssembler;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Book extends RealmObject {
    @PrimaryKey
    Integer ID;

    Integer id_publisher;
    @Form
    String book_name;
    @Form
    String url;
    @Form
    String local_url;
    @Form
    Integer page_readed;
    @Form
    RealmList<Integer> bookmarks;
    @Form
    Date date_saved;
    @Form
    RealmList<String> category;

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getId_publisher() {
        return id_publisher;
    }

    public void setId_publisher(Integer id_publisher) {
        this.id_publisher = id_publisher;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLocal_url() {
        return local_url;
    }

    public void setLocal_url(String local_url) {
        this.local_url = local_url;
    }

    public Integer getPage_readed() {
        return page_readed;
    }

    public void setPage_readed(Integer page_readed) {
        this.page_readed = page_readed;
    }

    public RealmList<Integer> getBookmarks() {
        return bookmarks;
    }

    public void setBookmarks(RealmList<Integer> bookmarks) {
        this.bookmarks = bookmarks;
    }

    public Date getDate_saved() {
        return date_saved;
    }

    public void setDate_saved(Date date_saved) {
        this.date_saved = date_saved;
    }

    public RealmList<String> getCategory() {
        return category;
    }

    public void setCategory(RealmList<String> category) {
        this.category = category;
    }
}
