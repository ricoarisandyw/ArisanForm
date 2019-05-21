package com.github.arisan.helper;

import android.util.Log;

import com.github.arisan.annotation.Form;
import com.github.arisan.model.ArisanFieldModel;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by wijaya on 3/24/2018.
 */
public class ObjectReader {
    public static ArisanFieldModel extractField(ObjectGetter o, Field f, Form form){
        ArisanFieldModel arisanField = new ArisanFieldModel();
        arisanField.setViewType(form.type());
        arisanField.setRequire(form.required());
        arisanField.setPosition(form.position());
        arisanField.setDateFormat(form.format());
        arisanField.setFileType(form.fileType());
        arisanField.setBackground(form.background());
        arisanField.setColor(form.color());

        if(!form.label().equals("field name"))
            arisanField.setLabel(form.label());
        else
            arisanField.setLabel(f.getName().substring(0,1).toUpperCase() + f.getName().substring(1));

        arisanField.setName(f.getName());
        arisanField.setFieldType(f.getType().getName());

        arisanField.setValue(o.runGetter(f.getName()));

        return arisanField;
    }

    public static List<ArisanFieldModel> combineModelWithJson(List<ArisanFieldModel> models,String json){
        ObjectGetter objectGetter = new ObjectGetter(json);
        for(int i=0;i<models.size();i++){
            models.get(i).setValue(objectGetter.myMap.get(models.get(i).getName()));
        }
        return models;
    }

    public static List<ArisanFieldModel> getField(Object o) {
        List<ArisanFieldModel> detailList = new ArrayList<ArisanFieldModel>();
        Field[] declaredField = o.getClass().getDeclaredFields();
        ObjectGetter objectGetter = new ObjectGetter(o);
        for (Field f : declaredField) {
            if (f.isAnnotationPresent(Form.class)) {
                Annotation annotation = f.getAnnotation(Form.class);
                Form form = (Form) annotation;

                ArisanFieldModel arisanField = extractField(objectGetter,f,form);

                if (f.getType() == List.class && form.type().equals(Form.ONETOMANY)) {
                    Field[] my_fields = form.relation().getDeclaredFields();
                    List<List<ArisanFieldModel>> childFields = new ArrayList<>();
                    Object listValue = objectGetter.runGetter(f.getName());
                    List<Object> allValues = new ObjectGetter().getList(listValue);
                    for(Object values:allValues){
                        ObjectGetter insideGetter = new ObjectGetter(values);
                        List<ArisanFieldModel> insideModel = new ArrayList<>();
                        for(Field ff : my_fields){
                            if (ff.isAnnotationPresent(Form.class)) {
                                Annotation annotation2 = ff.getAnnotation(Form.class);
                                Form form2 = (Form) annotation2;
                                ArisanFieldModel childField = extractField(insideGetter,ff,form2);
                                insideModel.add(childField);
                            }
                        }
                        childFields.add(insideModel);
                    }
                    arisanField.setChildFieldModel(childFields);
                }
                detailList.add(arisanField);
            }
        }
        //Sort
        Collections.sort(detailList,new SortField());
        return detailList;
    }
}
