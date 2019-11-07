package net.away0x.lib_message_center.service

import io.reactivex.Observable
import net.away0x.lib_message_center.data.protocol.Message

/* 消息业务接口 */
interface MessageService {

    // 获取消息列表
    fun getMessageList(): Observable<MutableList<Message>?>


}
