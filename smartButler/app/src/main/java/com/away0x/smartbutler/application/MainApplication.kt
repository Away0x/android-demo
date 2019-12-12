package com.away0x.smartbutler.application

import android.app.Application
import android.os.StrictMode
import cn.bmob.v3.Bmob
import com.away0x.smartbutler.common.AppConstants

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // 初始化 bmob
        Bmob.initialize(this, AppConstants.BMOB_APP_ID)

        // android 7 系统解决拍照问题
        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
        builder.detectFileUriExposure()
    }

}