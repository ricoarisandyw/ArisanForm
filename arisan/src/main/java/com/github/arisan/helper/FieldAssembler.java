package com.github.arisan.helper;

import com.github.arisan.model.ArisanFieldModel;
import com.github.arisan.model.ArisanFieldModel;

import java.util.List;

/**
 * Created by wijaya on 4/22/2018.
 */

public class FieldAssembler {
    public static String toJson(List<ArisanFieldModel> f){
        String result = "{";
        for(int i = 0;i<f.size();i++){
            result+="\""+ f.get(i).getName()+"\":";
            if(f.get(i).getFieldType().equals(String.class.getCanonicalName())){
                if(f.get(i).getValue()!=null)result+="\""+f.get(i).getValue()+"\"";
                else result+="\"\"";
            }else{
                if(f.get(i).getValue()!=null)result+="\""+f.get(i).getValue()+"\"";
                else result+=f.get(i).getValue();
            }
            if(i!=f.size()-1){
                result+=",";
            }
        }
        result+="}";
        return result;
    }
}
