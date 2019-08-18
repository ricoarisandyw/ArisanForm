package com.github.arisan.helper;

import com.github.arisan.annotation.Form;
import com.github.arisan.model.FormModel;
import com.github.arisan.model.FormModel;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by wijaya on 3/24/2018.
 */
public class ObjectReader {
    private static FormModel extractField(ObjectGetter o, Field f, Form form){
        FormModel model = new FormModel();
        model.setViewType(form.type());
        model.setRequire(form.required());
        model.setPosition(form.position());
        model.setDateFormat(form.format());
        model.setFileType(form.fileType());
        model.setBackground(form.background());
        model.setColor(form.color());

        if(!form.label().equals("field name"))
            model.setLabel(form.label());
        else
            model.setLabel(f.getName().substring(0,1).toUpperCase() + f.getName().substring(1));

        if(!form.hint().equals("...")) model.setHint(form.hint());
        else model.setHint(f.getName().replace("_",""));

        model.setName(f.getName());
        model.setFieldType(f.getType().getName());

        model.setValue(o.runGetter(f.getName()));

        return model;
    }

    public static List<FormModel> combineModelWithJson(List<FormModel> models,String json){
        ObjectGetter objectGetter = new ObjectGetter(json);
        for(int i=0;i<models.size();i++){
            models.get(i).setValue(objectGetter.myMap.get(models.get(i).getName()));
        }
        return models;
    }

    public static List<FormModel> getField(Object o) {
        List<FormModel> detailList = new ArrayList<>();
        Field[] declaredField = o.getClass().getDeclaredFields();
        ObjectGetter objectGetter = new ObjectGetter(o);
        for (Field f : declaredField) {
            if(f.getAnnotation(Form.class)!=null){
                Form annotation = f.getAnnotation(Form.class);

                FormModel arisanField = extractField(objectGetter,f, annotation);

                if (f.getGenericType().toString().contains("List") && annotation.type()==Form.ONETOMANY) {
                    Field[] my_fields = annotation.relation().getDeclaredFields();
                    List<List<FormModel>> childFields = new ArrayList<>();
                    Object listValue = objectGetter.runGetter(f.getName());
                    List<Object> allValues = new ObjectGetter().getList(listValue);
                    for(Object values:allValues){
                        ObjectGetter insideGetter = new ObjectGetter(values);
                        List<FormModel> insideModel = new ArrayList<>();
                        for(Field ff : my_fields){
                            if(ff.getAnnotation(Form.class)!=null){
                            //if (ff.isAnnotationPresent(Form.class)) {
                                Form annotation2 = ff.getAnnotation(Form.class);
                                FormModel childField = extractField(insideGetter,ff, annotation2);
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
        Collections.sort(detailList,new SortForm());
        return detailList;
    }
}
