package com.github.arisan.annotation;

import com.github.arisan.model.ArisanCustomForm;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by wijaya on 3/24/2018.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD) //can use in method only.
public @interface Form {
    String label() default "field name";
    int type() default Form.TEXT;
    boolean required() default false;
    String format() default "dd-MM-yyyy";
    int position() default 100;
    String fileType() default FileType.ANYTHING;
    int background() default 0;
    int color() default 0;
    Class relation() default String.class;
    Class custom_class() default ArisanCustomForm.class;
    String hint() default "...";

    int TEXT = 181;
    int PASSWORD = 182;
    int NUMBER = 183;
    int EMAIL = 184;
    int CHECKBOX = 185;
    int SPINNER = 186;
    int DATE = 187;
    int DATETIME = 188;
    int TIME = 189;
    int BOOLEAN = 190;
    int FILE = 191;
    int SEARCH = 192;
    int ONETOMANY = 193;
    int RADIO = 194;
    int SLIDER = 195;
    int IMAGE = 196;
    int AUTOCOMPLETE = 197;
    int CAMERA = 198;
    int GALLERY = 199;
    int ONELINETEXT = 200;
    int FLOWTEXT = 201;
    int BUTTON = 202;
    int TITLE = 203;
    int CUSTOM = 204;
}
