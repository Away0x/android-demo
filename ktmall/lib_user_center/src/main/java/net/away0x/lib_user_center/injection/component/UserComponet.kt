package net.away0x.lib_user_center.injection.component

import dagger.Component
import net.away0x.lib_base.injection.PerComponentScope
import net.away0x.lib_base.injection.component.ActivityComponent
import net.away0x.lib_user_center.injection.module.UploadModule
import net.away0x.lib_user_center.injection.module.UserModule
import net.away0x.lib_user_center.ui.activity.*

@PerComponentScope
@Component(
    dependencies = arrayOf(ActivityComponent::class),
    modules = arrayOf(
        UserModule::class,
        UploadModule::class
    )
)
interface UserComponet {

    fun inject(activity: RegisterActivity)
    fun inject(activity: LoginActivity)
    fun inject(activity: ForgetPwdActivity)
    fun inject(activity: ResetPwdActivity)
    fun inject(activity: UserInfoActivity)

}