package com.github.arisan.helper

import com.github.arisan.model.ArisanFieldModel
import com.github.arisan.model.RadioModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object FieldUtils {
    fun countBlank(models:MutableList<ArisanFieldModel>?): List<String>? {
        return models?.filter { it.isRequire && it.value == null }?.map { it.label }
    }

    fun removeField(fieldName: String, list: MutableList<ArisanFieldModel>): List<ArisanFieldModel> {
        return list.filter { it.name != fieldName }
    }

    fun insertOrUpdateField(new_model: ArisanFieldModel, list: MutableList<ArisanFieldModel>): List<ArisanFieldModel> {
        removeField(new_model.name, list)
        list.add(new_model)
        return list
    }

    fun convertArrayToList(data: Any?): List<String>? {
        val gson = Gson()
        val json = gson.toJson(data)
        return gson.fromJson<List<String>>(json, object : TypeToken<List<String>>() {

        }.type)
    }

    fun convertDataToRadio(data: Any): List<RadioModel>? {
        val gson = Gson()
        val json = gson.toJson(data)
        return gson.fromJson<List<RadioModel>>(json, object : TypeToken<List<RadioModel>>() {

        }.type)
    }
}
