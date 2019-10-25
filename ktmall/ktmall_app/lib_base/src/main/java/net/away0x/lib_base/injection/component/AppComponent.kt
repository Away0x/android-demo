package net.away0x.lib_base.injection.component

import android.content.Context
import dagger.Component
import net.away0x.lib_base.injection.module.AppModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {

    fun context(): Context

}