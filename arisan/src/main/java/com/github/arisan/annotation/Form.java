package com.github.arisan.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wijaya on 3/24/2018.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD) //can use in method only.
public @interface Form {
    String label() default "field name";
    String type() default Form.TEXT;
    boolean required() default false;
    String format() default "dd-MM-yyyy";
    int position() default 100;
    String fileType() default FileType.ANYTHING;
    int background() default 0;

    String TEXT = "TEXT";
    String PASSWORD = "PASSWORD";
    String NUMBER = "NUMBER";
    String EMAIL = "EMAIL";
    String CHECKBOX = "CHECKBOX";
    String SPINNER = "SPINNER";
    String DATE = "DATE";
    String DATETIME = "DATETIME";
    String TIME = "TIME";
    String BOOLEAN = "BOOLEAN";
    String FILE = "FILE";
}
