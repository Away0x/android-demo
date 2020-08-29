package com.away0x.com.recyclerview.utils

import com.google.gson.Gson
import com.google.gson.JsonNull
import java.util.*

object JsonUtil {

    private val gson = Gson()

    fun toJson(src: Any?): String {
        if (src == null) return gson.toJson(JsonNull.INSTANCE)
        return gson.toJson(src)
    }

    fun <T> fromJson(json: String, classOfT: Class<T>): T {
        return gson.fromJson(json, classOfT)
    }

}