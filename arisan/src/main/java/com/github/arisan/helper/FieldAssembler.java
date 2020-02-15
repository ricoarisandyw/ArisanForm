package com.github.arisan.helper;

import android.util.Log;

import com.github.arisan.annotation.Form;
import com.github.arisan.model.FormModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by wijaya on 4/22/2018.
 */

public class FieldAssembler {

    public static String toFormJson(List<FormModel> data) {
        StringBuilder result = new StringBuilder("{");
        List<String> json_list = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            FormModel model = data.get(i).renew();

            //For model type List
            if (model.getChildFieldModel() != null) {
                String json = convertListToJson(model);
                json_list.add(json);
            } else if (model.getValue() != null) {
                String json = convertModelToJson(model);
                json_list.add(json);
            }
        }
        for (String json : json_list) {
            result.append(json);
            if (json_list.indexOf(json) < json_list.size()-1) {
                result.append(",");
            }
        }
        result.append("}");
        Log.d("__Result", result.toString());
        return result.toString();
    }

    public static String convertModelToJson(FormModel model) {
        Gson gson = new Gson();
        StringBuilder result = new StringBuilder();
        if (model.getValue() != null) {
            result.append("\"").append(model.getName()).append("\":");
            result.append(gson.toJson(model.getValue()));
        }
        return result.toString();
    }

    public static String convertListToJson(FormModel model) {
        List<List<FormModel>> data = model.getChildFieldModel();
        if (data != null && data.size() > 0) {
            StringBuilder result = new StringBuilder();
            result.append("\"").append(model.getName()).append("\":");
            //Begin array definition
            result.append("[");
            List<String> json_list = new ArrayList<>();
            for (List<FormModel> m:data) {
                String json = toFormJson(m);
                json_list.add(json);
            }

            String combine = new KotlinFilter().combineJson(json_list);
            result.append(combine);

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