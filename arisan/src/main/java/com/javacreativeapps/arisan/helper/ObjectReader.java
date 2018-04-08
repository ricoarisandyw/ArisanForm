package com.javacreativeapps.arisan.helper;

import com.javacreativeapps.arisan.annotation.Form;
import com.javacreativeapps.arisan.model.FieldDetail;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
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
        /*Class<Object> objectClass = (Class<Object>) o.getClass();
        Method[] method = objectClass.getDeclaredMethods();
        for(Method m : method){
            result += "\nMethod "+ m.getName();
        }*/
        return result;
    }

    public static List<FieldDetail> getFieldName(Object o) {

        Class<Object> objectClass = (Class<Object>) o.getClass();
        Method[] method = objectClass.getDeclaredMethods();
        List<FieldDetail> detailList = new ArrayList<FieldDetail>();
        Field[] declaredField = o.getClass().getDeclaredFields();
        for (Field f : declaredField) {
            if (f.isAnnotationPresent(Form.class)) {
                FieldDetail fieldDetail = new FieldDetail();
                Annotation annotation = f.getAnnotation(Form.class);
                Form form = (Form) annotation;

                fieldDetail.setViewType(form.type().name());
                fieldDetail.setConfirm(form.confirm());

                fieldDetail.setName(f.getName());
                fieldDetail.setFieldType(f.getType().getName());
                if(f.getType() == List.class){
                    ParameterizedType integerListType = (ParameterizedType) f.getGenericType();
                    Class<?> integerListClass = (Class<?>) integerListType.getActualTypeArguments()[0];
                    fieldDetail.setFieldType("List of " + integerListType.getActualTypeArguments()[0]);
                }
                detailList.add(fieldDetail);
            }
        }
        return detailList;
    }
}
