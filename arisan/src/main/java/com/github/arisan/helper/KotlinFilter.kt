package com.github.arisan.helper

import com.github.arisan.model.FormModel
import com.github.arisan.model.FormViewHolder

class KotlinFilter {
    fun filterViewHolder(name:String,list:MutableList<FormViewHolder>): FormViewHolder? {
        val holder = list.filter { it.data!=null && it.data.name == name }.first()
        return holder
    }

    fun combineJson(list:MutableList<String>):String{
        val filtered = list.filter{!(it.equals("{}")||it.equals(""))}
        return filtered.joinToString()
    }

    fun findFieldByName(name:String,list:MutableList<FormModel>): FormModel?{
        if(list == null || list.size == 0) return null
        return list.filter { it.name.equals(name)}.firstOrNull()
    }


}