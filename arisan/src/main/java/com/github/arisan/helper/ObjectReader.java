package com.github.arisan.helper;

import android.util.Log;

import com.github.arisan.annotation.Form;
import com.google.gson.Gson;
import com.github.arisan.annotation.Form;
import com.github.arisan.model.ArisanFieldModel;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by wijaya on 3/24/2018.
 */
public class ObjectReader {
    public String read(Object o) {
        String result = "";
        result += o.getClass().toString();
        Field[] fields = o.getClass().getFields();
        for (Field f : fields) {
            result += "\nFields " + f.getName();
            if (f.isAnnotationPresent(Form.class)) {

            }
        }
        Field[] declaredField = o.getClass().getDeclaredFields();
        for (Field f : declaredField) {
            if (f.isAnnotationPresent(Form.class)) {
                Annotation annotation = f.getAnnotation(Form.class);
                Form form = (Form) annotation;

                result += "\nAnnotation Data ViewType : " + form.type();
            }
            result += "\nDeclared " + f.getType().getName() + " " + f.getName();
        }
        return result;
    }

    public static List<ArisanFieldModel> getField(Object o) {
        List<ArisanFieldModel> detailList = new ArrayList<ArisanFieldModel>();
        Field[] declaredField = o.getClass().getDeclaredFields();
        ObjectGetter objectGetter = new ObjectGetter(o);
        for (Field f : declaredField) {
            if (f.isAnnotationPresent(Form.class)) {
                ArisanFieldModel arisanField = new ArisanFieldModel();
                Annotation annotation = f.getAnnotation(Form.class);
                Form form = (Form) annotation;

                arisanField.setViewType(form.type());
                arisanField.setRequire(form.confirm());
                arisanField.setPosition(form.position());
                arisanField.setValue(objectGetter.runGetter(f.getName()));
                arisanField.setDateFormat(form.dateFormat());

                if(form.label()!="null")
                    arisanField.setLabel(form.label());
                else
                    arisanField.setLabel(f.getName().substring(0,1).toUpperCase() + f.getName().substring(1));

                arisanField.setName(f.getName());
                arisanField.setFieldType(f.getType().getName());
                if (f.getType() == List.class) {
                    ParameterizedType integerListType = (ParameterizedType) f.getGenericType();
                    Class<?> integerListClass = (Class<?>) integerListType.getActualTypeArguments()[0];
                    arisanField.setFieldType("List of " + integerListType.getActualTypeArguments()[0]);
                }
                detailList.add(arisanField);
            }
        }
        //Sort
        for (int i = 0; i < detailList.size(); i++) {
            if (detailList.get(i).getPosition() != -1) {
                Collections.swap(detailList, i, detailList.get(i).getPosition());
            }
        }
        return detailList;
    }
}
