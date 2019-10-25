package net.away0x.lib_base.injection.module

import com.trello.rxlifecycle3.LifecycleProvider
import dagger.Module
import dagger.Provides

@Module
class LifecycleProviderModule(private val lifecycleProvider: LifecycleProvider<*>) {

    @Provides
    fun providersLifecycleProvider(): LifecycleProvider<*> {
        return lifecycleProvider
    }

}