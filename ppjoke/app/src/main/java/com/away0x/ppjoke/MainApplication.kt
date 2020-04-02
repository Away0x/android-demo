package com.away0x.ppjoke

import android.app.Application
import com.away0x.lib_common.utils.logd
import com.away0x.lib_network.ApiResponse
import com.away0x.lib_network.ApiService
import com.away0x.lib_network.utils.CacheStrategy
import com.away0x.lib_network.ApiCallback

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        /**
         * api doc
         * http://123.56.232.18:8080/serverdemo/swagger-ui.html
         */
        ApiService.init<Any>("http://123.56.232.18:8080/serverdemo", null)
    }

}