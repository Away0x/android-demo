package net.away0x.lib_goods_center.injection.component

import dagger.Component
import net.away0x.lib_base.injection.PerComponentScope
import net.away0x.lib_base.injection.component.ActivityComponent
import net.away0x.lib_goods_center.injection.module.CartModule
import net.away0x.lib_goods_center.ui.fragment.CartFragment

/* 购物车 Component */
@PerComponentScope
@Component(
    dependencies = [ActivityComponent::class],
    modules = [CartModule::class]
)
interface CartComponent {

    fun inject(fragment: CartFragment)

}