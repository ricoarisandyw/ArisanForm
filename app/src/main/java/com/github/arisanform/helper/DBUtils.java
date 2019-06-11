package com.github.arisanform.helper;

import android.content.Context;

import com.github.arisan.helper.PreferenceHelper;
import com.github.arisan.model.ArisanFieldModel;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DBUtils {
    PreferenceHelper db;
    Context context;

    public static List<ArisanFieldModel> removeField(String fieldName,List<ArisanFieldModel> list){
        for(ArisanFieldModel model:list){
            if (model.getName().equals(fieldName)){
                list.remove(model);
                break;
            }
        }
        return list;
    }

    public DBUtils(Context context) {
        db = new PreferenceHelper(context);
    }

    public void add(Object object){
        //Get class name
        String tabel = object.getClass().getCanonicalName();
        List<Object> objectNow = this.all(object.getClass());
        objectNow.add(object);
        db.saveObj(tabel,objectNow);
    }

    public void delete(Object object){
        String tabel = object.getClass().getCanonicalName();
        List<Object> objectNow = this.all(object.getClass());
        objectNow.remove(object);
        db.saveObj(tabel,objectNow);
    }

    public List<Object> all(Class clz){
        Type type = new TypeToken<List<Object>>(){}.getType();
        List<Object> objects = db.loadObjList(clz.getCanonicalName(),type);
        if(objects==null){
            return new ArrayList<>();
        }
        return objects;
    }
}
