package net.away0x.lib_message_center.data.repository

import io.reactivex.Observable
import net.away0x.lib_base.data.net.RetrofitFactory
import net.away0x.lib_base.data.protocol.BaseResp
import net.away0x.lib_message_center.data.api.MessageApi
import net.away0x.lib_message_center.data.protocol.Message
import javax.inject.Inject

/* 消息数据层 */
class MessageRepository @Inject constructor() {

    /* 获取消息列表 */
    fun getMessageList(): Observable<BaseResp<MutableList<Message>?>> {
        return RetrofitFactory.instance.create(MessageApi::class.java).getMessageList()
    }

}