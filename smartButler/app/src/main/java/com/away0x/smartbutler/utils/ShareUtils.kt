package com.away0x.smartbutler.utils

import android.content.Context
import android.content.SharedPreferences

object ShareUtils {

    const val NAME = "config"

    private fun getSp(context: Context): SharedPreferences {
        return context.getSharedPreferences(NAME, Context.MODE_PRIVATE)
    }

    fun putString(context: Context, key: String, value: String) {
        getSp(context).edit().putString(key, value).apply()
    }

    fun getString(context: Context, key: String, defaultValue: String): String {
        return getSp(context).getString(key, defaultValue) ?: ""
    }

    fun putInt(context: Context, key: String, value: Int) {
        getSp(context).edit().putInt(key, value).apply()
    }

    fun getInt(context: Context, key: String, defaultValue: Int): Int {
        return getSp(context).getInt(key, defaultValue)
    }

    fun putBoolean(context: Context, key: String, value: Boolean) {
        getSp(context).edit().putBoolean(key, value).apply()
    }

    fun getBoolean(context: Context, key: String, defaultValue: Boolean): Boolean {
        return getSp(context).getBoolean(key, defaultValue)
    }

    fun remove(context: Context, key: String) {
        getSp(context).edit().remove(key).apply()
    }

    fun clear(context: Context) {
        getSp(context).edit().clear().apply()
    }

}