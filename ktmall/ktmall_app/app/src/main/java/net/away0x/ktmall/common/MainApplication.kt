package net.away0x.ktmall.common

import cn.jpush.android.api.JPushInterface
import net.away0x.lib_base.common.BaseApplication

/* 主工程 Application */
class MainApplication: BaseApplication() {
    override fun onCreate() {
        super.onCreate()

        // 极光推送初始化
        JPushInterface.setDebugMode(true)
        JPushInterface.init(this)
    }
}

