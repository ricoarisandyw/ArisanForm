package com.javacreativeapps.arisanform;

import com.javacreativeapps.arisan.annotation.Form;

/**
 * Created by wijaya on 4/22/2018.
 */

public class Student {

    @Form
    private String name;

    @Form(type = Form.NUMBER)
    private int password;

    @Form(type = Form.BOOLEAN)
    private boolean graduated;

    int number;

    public Student() {
    }

    public Student(String name, int number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getPassword() {
        return password;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    public boolean isGraduated() {
        return graduated;
    }

    public void setGraduated(boolean graduated) {
        this.graduated = graduated;
    }
}
