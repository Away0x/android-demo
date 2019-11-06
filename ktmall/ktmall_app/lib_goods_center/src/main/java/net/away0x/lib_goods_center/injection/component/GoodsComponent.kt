package net.away0x.lib_goods_center.injection.component

import dagger.Component
import net.away0x.lib_base.injection.PerComponentScope
import net.away0x.lib_base.injection.component.ActivityComponent
import net.away0x.lib_goods_center.injection.module.CartModule
import net.away0x.lib_goods_center.injection.module.GoodsModule
import net.away0x.lib_goods_center.ui.activity.GoodsActivity
import net.away0x.lib_goods_center.ui.fragment.GoodsDetailTabOneFragment

/* 商品Component */
@PerComponentScope
@Component(
    dependencies = [ActivityComponent::class],
    modules = [GoodsModule::class, CartModule::class]
)
interface GoodsComponent {

    fun inject(activity: GoodsActivity)

    fun inject(fragment: GoodsDetailTabOneFragment)

}