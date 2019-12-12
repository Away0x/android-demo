package com.away0x.latte.core.app

import android.content.Context

object Latte {

    fun init(context: Context): Configurator {
        return getConfigurator()
            .withApplicationContext(context.applicationContext)
    }

    fun getConfigurator(): Configurator {
        return Configurator.instance
    }

}