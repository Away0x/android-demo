package net.away0x.lib_base.common

import android.app.Application
import android.content.Context
import com.alibaba.android.arouter.launcher.ARouter
import net.away0x.lib_base.injection.component.AppComponent
import net.away0x.lib_base.injection.component.DaggerAppComponent
import net.away0x.lib_base.injection.module.AppModule

open class BaseApplication: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        initAppInjection()

        context = this

        // ARouter 初始化
        ARouter.openLog() // 打印日志
        ARouter.openDebug()
        ARouter.init(this)
    }

    private fun initAppInjection() {
        appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .build()
    }

    companion object {
        lateinit var context: Context
    }

}