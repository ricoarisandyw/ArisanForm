package com.javacreativeapps.arisan.helper;

import com.javacreativeapps.arisan.annotation.Form;
import com.javacreativeapps.arisan.model.ArisanField;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
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

    public static List<ArisanField> getField(Object o) {
        List<ArisanField> detailList = new ArrayList<ArisanField>();
        Field[] declaredField = o.getClass().getDeclaredFields();
        for (Field f : declaredField) {
            if (f.isAnnotationPresent(Form.class)) {
                ArisanField arisanField = new ArisanField();
                Annotation annotation = f.getAnnotation(Form.class);
                Form form = (Form) annotation;

                arisanField.setViewType(form.type());
                arisanField.setConfirm(form.confirm());

                arisanField.setName(f.getName());
                arisanField.setFieldType(f.getType().getName());
                if(f.getType() == List.class){
                    ParameterizedType integerListType = (ParameterizedType) f.getGenericType();
                    Class<?> integerListClass = (Class<?>) integerListType.getActualTypeArguments()[0];
                    arisanField.setFieldType("List of " + integerListType.getActualTypeArguments()[0]);
                }
                detailList.add(arisanField);
            }
        }
        return detailList;
    }
}
