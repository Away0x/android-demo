package com.away0x.latte

import android.app.Application
import com.away0x.latte.core.app.Latte
import com.away0x.latte.ec.icon.FontEcModule
import com.joanzapata.iconify.fonts.FontAwesomeModule
import com.joanzapata.iconify.fonts.IoniconsModule

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // 初始化配置
        Latte.init(this)
            .withIcon(FontAwesomeModule()) // fontawesome.com.cn
            .withIcon(IoniconsModule())
            .withIcon(FontEcModule()) // 自定义字体
            .withApiHost("http://127.0.0.1/")
            .configure()
    }

}