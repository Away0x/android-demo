package com.away0x.smartbutler.utils

import android.util.Log

/// 自定义 log

object L {

    const val DEBUG = true
    const val TAG = "SmartButler"

    fun d(text: String) {
        if (DEBUG) {
            Log.d(TAG, text)
        }
    }

    fun i(text: String) {
        if (DEBUG) {
            Log.i(TAG, text)
        }
    }

    fun w(text: String) {
        if (DEBUG) {
            Log.w(TAG, text)
        }
    }

    fun e(text: String) {
        if (DEBUG) {
            Log.e(TAG, text)
        }
    }

}