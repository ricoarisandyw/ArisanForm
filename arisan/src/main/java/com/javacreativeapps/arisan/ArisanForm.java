package com.javacreativeapps.arisan;



import com.javacreativeapps.arisan.helper.ObjectReader;
import com.javacreativeapps.arisan.helper.ObjectSetter;
import com.javacreativeapps.arisan.model.FieldDetail;

import java.util.List;

/**
 * Created by wijaya on 3/26/2018.
 */
public class ArisanForm<T>{
    T t;

    public void setObject(Object o,OnResponse onResponse){
        ObjectSetter objectSetter;
        String str = "example";
        int ex = 17;

        List<FieldDetail> fieldDetailList = ObjectReader.getFieldName(o);

        for(FieldDetail f: fieldDetailList){
            if(f.getFieldType().equals("java.lang.String")){
                ObjectSetter.set(o,f.getName(),str);
            }else if(f.getFieldType().equals("boolean")){
                ObjectSetter.set(o,f.getName(),true);
            }else {
                ObjectSetter.set(o,f.getName(),1000);
            }
        }

        onResponse.response(o);
    }

    public interface OnResponse<T>{
        public void response(T response);
    }
}
