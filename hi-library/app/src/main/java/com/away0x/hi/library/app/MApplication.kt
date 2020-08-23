package com.away0x.hi.library.app

import android.app.Application
import com.away0x.hi.library.log.printer.HiConsolePrinter
import com.away0x.hi.library.log.HiLogConfig
import com.away0x.hi.library.log.HiLogManager
import com.google.gson.Gson

class MApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        HiLogManager.init(object : HiLogConfig() {
            // 使用 gson 作为 json 序列化器
            override fun injectJsonParser(): JsonParser {
                return JsonParser { Gson().toJson(it) }
            }

            override fun getGlobalTag(): String {
                return "MApplication"
            }

            override fun enable(): Boolean {
                return true;
            }

            override fun includeThread(): Boolean {
                return true
            }

            override fun stackTraceDepth(): Int {
                return 5
            }
        }, HiConsolePrinter())
    }

}