package net.away0x.lib_goods_center.injection.component

import dagger.Component
import net.away0x.lib_base.injection.PerComponentScope
import net.away0x.lib_base.injection.component.ActivityComponent
import net.away0x.lib_goods_center.injection.module.CategoryModule
import net.away0x.lib_goods_center.ui.fragment.CategoryFragment

/* 商品分类 Component */
@PerComponentScope
@Component(
    dependencies = [ActivityComponent::class],
    modules = [CategoryModule::class]
)
interface CategoryComponent {

    fun inject(fragment: CategoryFragment)

}
