package net.away0x.lib_message_center.data.api

import io.reactivex.Observable
import net.away0x.lib_base.data.protocol.BaseResp
import net.away0x.lib_message_center.data.protocol.Message
import retrofit2.http.GET

/* 消息 接口 */
interface MessageApi {

    /* 获取消息列表 */
    @GET("message/list")
    fun getMessageList(): Observable<BaseResp<MutableList<Message>?>>

}
