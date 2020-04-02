package com.away0x.lib_network.converter

import java.lang.reflect.Type

interface Converter<T> {

    fun convert(response: String, type: Type): T?

}