package net.away0x.lib_pay_center.injection.component

import net.away0x.lib_pay_center.injection.module.PayModule
import net.away0x.lib_pay_center.ui.activity.CashRegisterActivity
import dagger.Component
import net.away0x.lib_base.injection.PerComponentScope
import net.away0x.lib_base.injection.component.ActivityComponent

/*
    支付Component
 */
@PerComponentScope
@Component(dependencies = arrayOf(ActivityComponent::class), modules = arrayOf(PayModule::class))
interface PayComponent {
    fun inject(activity: CashRegisterActivity)
}
