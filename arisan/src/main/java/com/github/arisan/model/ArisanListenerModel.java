package com.github.arisan.model;

public class ArisanListenerModel {
    public String message;
    public boolean condition;

    public ArisanListenerModel() {
    }

    public ArisanListenerModel(String message, boolean condition) {
        this.message = message;
        this.condition = condition;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isCondition() {
        return condition;
    }

    public void setCondition(boolean condition) {
        this.condition = condition;
    }
}
