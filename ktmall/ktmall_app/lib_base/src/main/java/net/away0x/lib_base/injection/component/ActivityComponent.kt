package net.away0x.lib_base.injection.component

import android.app.Activity
import android.content.Context
import com.trello.rxlifecycle3.LifecycleProvider
import dagger.Component
import net.away0x.lib_base.injection.ActivityScope
import net.away0x.lib_base.injection.module.ActivityModule
import net.away0x.lib_base.injection.module.LifecycleProviderModule

@ActivityScope
@Component(
    dependencies = arrayOf(AppComponent::class),
    modules = arrayOf(
        ActivityModule::class,
        LifecycleProviderModule::class
    ))
interface ActivityComponent {

    fun activity(): Activity
    fun context(): Context
    fun lifecycleProvider(): LifecycleProvider<*>

}