package com.github.arisan.annotation;

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
    String label() default "null";
    int type() default Form.TEXT;
    boolean confirm() default false;
    Relation relation() default Relation.ManyToMany;
    Mode mode() default Mode.Choice;
    String dateFormat() default "dd-MM-yyyy";
    int position() default -1;

    public static int TEXT = 171;
    public static int PASSWORD = 172;
    public static int NUMBER = 173;
    public static int CHECKBOX = 174;
    public static int SPINNER = 175;
    public static int DATE = 176;
    public static int TIME = 177;
    public static int BOOLEAN = 178;

    enum Relation {
        Single, Multi, ManyToMany;
    }

    enum Mode {
        Fill,Choice;
    }
}
