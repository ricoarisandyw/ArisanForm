package com.github.arisan.helper;

import android.util.Log;

import com.github.arisan.model.ArisanFieldModel;
import com.github.arisan.model.ArisanFieldModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wijaya on 4/22/2018.
 */

public class FieldAssembler {
    public static String toJson(List<ArisanFieldModel> data) {
        StringBuilder result = new StringBuilder("{");
        for (int i = 0; i < data.size(); i++) {
            ArisanFieldModel model = data.get(i);

            //For model type List
            if (model.getChildFieldModel() != null) {
                result.append(convertListToJson(model));
            } else
                result.append(convertModelToJson(model));

            if (i < data.size() - 1 && (model.getValue() != null || model.getChildFieldModel() != null)) {
                result.append(",");
            }
        }
        result.append("}");
        Log.d("__Result", result.toString());
        return result.toString();
    }

    public static String convertModelToJson(ArisanFieldModel model) {
        Gson gson = new Gson();
        StringBuilder result = new StringBuilder();
        if (model.getValue() != null) {
            result.append("\"").append(model.getName()).append("\":");
            result.append(gson.toJson(model.getValue()));
        }
        return result.toString();
    }

    public static String convertListToJson(ArisanFieldModel model) {
        List<List<ArisanFieldModel>> data = model.getChildFieldModel();
        if (data != null && data.size() > 0) {
            StringBuilder result = new StringBuilder();
            result.append("\"").append(model.getName()).append("\":");
            result.append("[");
            for (int i = 0; i < data.size(); i++) {
                String json = toJson(data.get(i));
                if(!json.equals("{}")) {
                    result.append(toJson(data.get(i)));
                    if (i < data.size() - 1) {
                        result.append(",");
                    }
                }
            }
            result.append("]");
            return result.toString();
        }
        return "";
    }

    public static String mapToJson(Map<String, Object> data) {
        StringBuilder result = new StringBuilder("{");
        Gson gson = new Gson();
        int i = 0;
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (entry.getValue() != null) {
                result.append("\"").append(key).append("\":");
                result.append(gson.toJson(value));
                if (i < data.size() - 1) {
                    result.append(",");
                }
            }
            i++;
        }
        result.append("}");
        Log.d("__Result map", result.toString());
        return result.toString();
    }
}