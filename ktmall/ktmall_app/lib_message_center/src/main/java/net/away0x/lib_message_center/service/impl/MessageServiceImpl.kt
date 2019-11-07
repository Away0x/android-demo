package net.away0x.lib_message_center.service.impl

import io.reactivex.Observable
import net.away0x.lib_base.rx.baseFunc
import net.away0x.lib_message_center.data.protocol.Message
import net.away0x.lib_message_center.data.repository.MessageRepository
import net.away0x.lib_message_center.service.MessageService
import javax.inject.Inject

/* 消息业务层 */
class MessageServiceImpl @Inject constructor(): MessageService {

    @Inject
    lateinit var repository: MessageRepository

    /* 获取消息列表 */
    override fun getMessageList(): Observable<MutableList<Message>?> {
        return repository.getMessageList().flatMap { baseFunc(it) }
    }

}