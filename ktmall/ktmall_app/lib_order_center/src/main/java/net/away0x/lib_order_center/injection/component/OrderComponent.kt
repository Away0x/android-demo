package net.away0x.lib_order_center.injection.component

import net.away0x.lib_order_center.injection.module.OrderModule
import net.away0x.lib_order_center.ui.activity.OrderConfirmActivity
import net.away0x.lib_order_center.ui.activity.OrderDetailActivity
import net.away0x.lib_order_center.ui.fragment.OrderFragment
import dagger.Component
import net.away0x.lib_base.injection.PerComponentScope
import net.away0x.lib_base.injection.component.ActivityComponent

/*
    订单Component
 */
@PerComponentScope
@Component(dependencies = arrayOf(ActivityComponent::class),modules = arrayOf(OrderModule::class))
interface OrderComponent {
    fun inject(activity:OrderConfirmActivity)
    fun inject(fragment:OrderFragment)

    fun inject(activity:OrderDetailActivity)
}
