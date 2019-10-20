package net.away0x.lib_base.common

import android.app.Application
import android.content.Context
import net.away0x.lib_base.injection.component.AppComponent
import net.away0x.lib_base.injection.component.DaggerAppComponent
import net.away0x.lib_base.injection.module.AppModule

class BaseApplication: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        initAppInjection()
        context = this
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