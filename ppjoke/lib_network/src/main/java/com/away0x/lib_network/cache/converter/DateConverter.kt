package com.away0x.lib_network.cache.converter

import androidx.room.TypeConverter
import java.util.*

/**
 * date 格式转换
 */
object DateConverter {

    @TypeConverter
    fun date2Long(date: Date): Long {
        return date.time
    }

    @TypeConverter
    fun long2Date(d: Long): Date {
        return Date(d)
    }

}