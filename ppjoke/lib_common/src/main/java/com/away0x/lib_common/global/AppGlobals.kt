package com.away0x.lib_common.global

import android.annotation.SuppressLint
import android.app.Application

object AppGlobals {

    private var app: Application? = null

    /**
     * 通过反射获取到 Application
     */
    @SuppressLint("DiscouragedPrivateApi", "PrivateApi")
    fun getApplication(): Application {
        if (app == null) {
            val method = Class.forName("android.app.ActivityThread")
                .getDeclaredMethod("currentApplication")
            app = method.invoke(null) as Application
        }

        return app!!
    }

}