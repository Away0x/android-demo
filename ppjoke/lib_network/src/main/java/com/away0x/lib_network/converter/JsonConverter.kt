package com.away0x.lib_network.converter

import com.google.gson.Gson
import com.google.gson.JsonObject
import java.lang.reflect.Type

class JsonConverter<T>: Converter<T?> {

    override fun convert(response: String, type: Type): T? {
        val jsonObject = Gson().fromJson(response, JsonObject::class.java)
        val data = jsonObject.getAsJsonObject("data")
        if (data != null) {
            return Gson().fromJson(data.get("data").toString(), type)
        }
        return null
    }
}