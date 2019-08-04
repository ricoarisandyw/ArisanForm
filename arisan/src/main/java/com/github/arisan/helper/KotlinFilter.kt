package com.github.arisan.helper

import com.github.arisan.model.FormModel
import com.github.arisan.model.FormViewHolder

class KotlinFilter {
    fun filterViewHolder(list:MutableList<FormViewHolder>,name:String): FormViewHolder {
        val holder = list.filter { it.data.name==name}.first()
        return holder
    }

    fun combineJson(list:MutableList<String>):String{
        val filtered = list.filter{!(it.equals("{}")||it.equals(""))}
        return filtered.joinToString()
    }

    fun findFieldByName(name:String,list:MutableList<FormModel>): FormModel?{
        return list.filter { it.name.equals(name)}.firstOrNull()
    }
}