package net.away0x.lib_base.injection.module

import android.content.Context
import dagger.Module
import dagger.Provides
import net.away0x.lib_base.common.BaseApplication
import javax.inject.Singleton


@Module
class AppModule(private val context: BaseApplication) {

    @Provides
    @Singleton
    fun providersContext(): Context {
        return context
    }

}