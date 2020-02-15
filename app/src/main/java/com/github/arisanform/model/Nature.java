package com.github.arisanform.model;

import com.github.arisan.annotation.Form;
import com.github.arisanform.activity.MyCustomRange;
import com.github.arisanform.activity.MyCustomToggleForm;

import java.util.List;

public class Nature {
    @Form(label = "Image Name",position = 1) //Default type is Edit Text
    private String image_name;
    @Form(type = Form.SPINNER,position = 2)
    private String category;
    @Form(type = Form.IMAGE,position = 3)
    private String image;
    @Form(type = Form.CHECKBOX,position = 4)
    private List<String> label;
    @Form(position = 5)
    private String description;
    @Form(type = Form.SLIDER,position = 6)
    private int score;
    @Form(label = "Pick at",type = Form.DATE,position = 7,format = "dd-MMM-yyyy")
    private String pick_at;
    @Form(type = Form.CUSTOM, label = "Use Assurance", position = 8, custom_class = MyCustomToggleForm.class)
    private boolean use_assurance;
    @Form(type = Form.CUSTOM, label = "Budget", position = 9, custom_class = MyCustomRange.class)
    private int budget;

    //PREPARRING ARRAY DATA
    public static String[] DATA_CATEGORY = {"Mountain","Beach","Forest","Museum"};
    public static String[] DATA_LABEL = {"Visitable","Souvenir Shop","Guide","Events"};

    public Nature() {
    }

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<String> getLabel() {
        return label;
    }

    public void setLabel(List<String> label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getPick_at() {
        return pick_at;
    }

    public void setPick_at(String pick_at) {
        this.pick_at = pick_at;
    }

    public boolean isUse_assurance() {
        return use_assurance;
    }

    public void setUse_assurance(boolean use_assurance) {
        this.use_assurance = use_assurance;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }
}
