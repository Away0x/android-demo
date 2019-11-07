package net.away0x.lib_message_center.injection.component

import dagger.Component
import net.away0x.lib_base.injection.PerComponentScope
import net.away0x.lib_base.injection.component.ActivityComponent
import net.away0x.lib_message_center.injection.module.MessageModule
import net.away0x.lib_message_center.ui.fragment.MessageFragment

/* 消息模块注入组件 */
@PerComponentScope
@Component(
    dependencies = arrayOf(ActivityComponent::class),
    modules = arrayOf(MessageModule::class)
)
interface MessageComponent{
    fun inject(fragment: MessageFragment)
}
