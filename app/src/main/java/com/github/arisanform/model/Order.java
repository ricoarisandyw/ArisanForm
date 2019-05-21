package com.github.arisanform.model;

import android.graphics.Color;

import com.github.arisan.annotation.Form;
import com.github.arisanform.R;

import java.util.Date;
import java.util.List;

public class Order {
    private int id;
    @Form(position = 0,color = R.color.white)
    private String orderer;
    @Form(type=Form.SPINNER, position = 1,color = R.color.white)
    private String menu;
    @Form(type=Form.NUMBER,color = R.color.white)
    private int quantity;
    @Form(type=Form.CHECKBOX, position = 2)
    private List<String> topping;
    @Form(type=Form.SEARCH, position = 2)
    private String location;
    @Form(type=Form.BOOLEAN)
    private boolean hot;
    @Form(type=Form.DATETIME, format = "dd-MM-yyyy HH:mm",background=R.drawable.gradient, color = R.color.white)
    private Date time;
    @Form(type=Form.DATE)
    private Date send_at;
    @Form(type = Form.ONETOMANY, label = "Tambahan", relation = Additional.class)
    private List<Additional> additionals;

    public Order() {
    }

    public List<Additional> getAdditionals() {
        return additionals;
    }

    public void setAdditionals(List<Additional> additionals) {
        this.additionals = additionals;
    }

    public Order(int id, String orderer, String menu, int quantity, List<String> topping, boolean hot) {
        this.id = id;
        this.orderer = orderer;
        this.menu = menu;
        this.quantity = quantity;
        this.topping = topping;
        this.hot = hot;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Date getSend_at() {
        return send_at;
    }

    public void setSend_at(Date send_at) {
        this.send_at = send_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderer() {
        return orderer;
    }

    public void setOrderer(String orderer) {
        this.orderer = orderer;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public List<String> getTopping() {
        return topping;
    }

    public void setTopping(List<String> topping) {
        this.topping = topping;
    }

    public boolean isHot() {
        return hot;
    }

    public void setHot(boolean hot) {
        this.hot = hot;
    }
}
