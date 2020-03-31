package com.away0x.lib_common.utils

import android.util.Log

fun loge(title: String, message: String) {
    Log.e(toString(title), message)
}

fun logd(title: String, message: String) {
    Log.d(toString(title), message)
}

fun logi(title: String, message: String) {
    Log.i(toString(title), message)
}

fun logv(title: String, message: String) {
    Log.v(toString(title), message)
}

private fun toString(str: String): String {
    return "[wutong] $str"
}