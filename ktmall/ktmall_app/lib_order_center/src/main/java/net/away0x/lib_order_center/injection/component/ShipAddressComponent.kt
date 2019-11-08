package net.away0x.lib_order_center.injection.component

import net.away0x.lib_order_center.injection.module.ShipAddressModule
import net.away0x.lib_order_center.ui.activity.ShipAddressActivity
import net.away0x.lib_order_center.ui.activity.ShipAddressEditActivity
import dagger.Component
import net.away0x.lib_base.injection.PerComponentScope
import net.away0x.lib_base.injection.component.ActivityComponent

/*
    收货人信息Component
 */
@PerComponentScope
@Component(dependencies = arrayOf(ActivityComponent::class),modules = arrayOf(ShipAddressModule::class))
interface ShipAddressComponent {
    fun inject(activity: ShipAddressEditActivity)
    fun inject(activity: ShipAddressActivity)
}
