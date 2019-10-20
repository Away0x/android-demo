package net.away0x.lib_user_center.injection.component

import dagger.Component
import net.away0x.lib_base.injection.PerComponentScope
import net.away0x.lib_base.injection.component.ActivityComponent
import net.away0x.lib_user_center.injection.module.UserModule
import net.away0x.lib_user_center.ui.activity.LoginActivity
import net.away0x.lib_user_center.ui.activity.RegisterActivity

@PerComponentScope
@Component(
    dependencies = arrayOf(ActivityComponent::class),
    modules = arrayOf(UserModule::class))
interface UserComponet {

    fun inject(activity: RegisterActivity)
    fun inject(activity: LoginActivity)

}