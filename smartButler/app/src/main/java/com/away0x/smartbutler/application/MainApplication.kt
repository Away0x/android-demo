package com.away0x.smartbutler.application

import android.app.Application
import cn.bmob.v3.Bmob
import com.away0x.smartbutler.common.AppConstants

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // 初始化 bmob
        Bmob.initialize(this, AppConstants.BMOB_APP_ID)
    }

}