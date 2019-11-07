package net.away0x.lib_message_center.presenter.view

import net.away0x.lib_base.presenter.view.BaseView
import net.away0x.lib_message_center.data.protocol.Message

/* 消息列表 视图回调 */
interface MessageView : BaseView {

    // 获取消息列表回调
    fun onGetMessageResult(result:MutableList<Message>?)

}
