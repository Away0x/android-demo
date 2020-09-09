package com.away0x.activitymanager

import android.app.Application

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        ActivityManager.instance.init(this)
    }

}