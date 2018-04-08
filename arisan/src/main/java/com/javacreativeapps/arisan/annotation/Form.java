package com.javacreativeapps.arisan.annotation;

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
    ViewType type() default ViewType.TEXT;
    boolean confirm() default false;
    Relation relation() default Relation.ManyToMany;
    Mode mode() default Mode.Choice;

    enum ViewType {
        TEXT,PASSWORD,NUMBER,CHECKBOX,RADIO,DATE,TIME;
    }

    enum Relation {
        Single, Multi, ManyToMany;
    }

    enum Mode {
        Fill,Choice;
    }
}
