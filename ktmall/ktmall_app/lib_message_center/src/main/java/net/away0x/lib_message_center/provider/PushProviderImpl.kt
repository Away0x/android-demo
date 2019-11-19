package net.away0x.lib_message_center.provider

import android.content.Context
import android.util.Log
import cn.jpush.android.api.JPushInterface
import com.alibaba.android.arouter.facade.annotation.Route
import net.away0x.lib_base.common.PushProvider
import net.away0x.lib_base.common.RouterPath

/*
    模块间接口调用
    提供PushId的实现
 */
@Route(path = RouterPath.MessageCenter.PATH_MESSAGE_PUSH)
class PushProviderImpl: PushProvider {

    private var mContext: Context? = null

    override fun getPushId(): String {
        val s = JPushInterface.getRegistrationID(mContext)
        Log.d("ssss", s)
        return s
    }

    override fun init(context: Context?) {
        mContext = context
    }
}
