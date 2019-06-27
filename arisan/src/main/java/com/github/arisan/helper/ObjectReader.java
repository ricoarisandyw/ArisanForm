package com.github.arisan.helper;

import com.github.arisan.annotation.Form;
import com.github.arisan.model.ArisanFieldModel;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by wijaya on 3/24/2018.
 */
public class ObjectReader {
    private static ArisanFieldModel extractField(ObjectGetter o, Field f, Form form){
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
        List<ArisanFieldModel> detailList = new ArrayList<>();
        Field[] declaredField = o.getClass().getDeclaredFields();
        ObjectGetter objectGetter = new ObjectGetter(o);
        for (Field f : declaredField) {
            //if (f.isAnnotationPresent(Form.class)) {
            if(f.getAnnotation(Form.class)!=null){
                Form annotation = f.getAnnotation(Form.class);

                ArisanFieldModel arisanField = extractField(objectGetter,f, annotation);

                if (f.getGenericType().toString().contains("List") && annotation.type()==Form.ONETOMANY) {
                    Field[] my_fields = annotation.relation().getDeclaredFields();
                    List<List<ArisanFieldModel>> childFields = new ArrayList<>();
                    Object listValue = objectGetter.runGetter(f.getName());
                    List<Object> allValues = new ObjectGetter().getList(listValue);
                    for(Object values:allValues){
                        ObjectGetter insideGetter = new ObjectGetter(values);
                        List<ArisanFieldModel> insideModel = new ArrayList<>();
                        for(Field ff : my_fields){
                            if(ff.getAnnotation(Form.class)!=null){
                            //if (ff.isAnnotationPresent(Form.class)) {
                                Form annotation2 = ff.getAnnotation(Form.class);
                                ArisanFieldModel childField = extractField(insideGetter,ff, annotation2);
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
