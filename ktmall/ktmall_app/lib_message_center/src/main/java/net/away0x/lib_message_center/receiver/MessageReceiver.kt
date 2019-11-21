package net.away0x.lib_message_center.receiver

import android.content.Context
import android.nfc.Tag
import android.util.Log
import android.widget.Toast
import cn.jpush.android.api.CustomMessage
import cn.jpush.android.api.JPushMessage
import cn.jpush.android.api.NotificationMessage
import cn.jpush.android.service.JPushMessageReceiver

/*
    自定义Push 接收器
 */
class MessageReceiver : JPushMessageReceiver() {

    companion object {
        const val TAG = "JIGUANG"
    }

    // 连接极光服务器
    override fun onConnected(p0: Context?, p1: Boolean) {
        super.onConnected(p0, p1)
        Log.e(TAG, "onConnected")
    }

    // 注册极光时的回调
    override fun onRegister(p0: Context?, p1: String?) {
        super.onRegister(p0, p1)
        Log.e(TAG, "onRegister" + p1)
    }

    // 注册及解除注册别名时的回调
    override fun onAliasOperatorResult(p0: Context?, p1: JPushMessage?) {
        super.onAliasOperatorResult(p0, p1)
        Log.e(TAG, p1.toString())
    }

    // 接收到推送下来的通知
    // 可利用字段 p1.notificationExtras 来区别 Notication 指定不同 action，其是 Json 字符串
    // Notication 指在手机通知栏上会显示的一条通知信息
    override fun onNotifyMessageArrived(p0: Context?, p1: NotificationMessage?) {
        super.onNotifyMessageArrived(p0, p1)
        Log.e(TAG, p1.toString())
        Toast.makeText(p0, "推送成功: " + p1?.notificationContent, Toast.LENGTH_LONG).show()
    }

    // 打开了通知
    override fun onNotifyMessageOpened(p0: Context?, p1: NotificationMessage?) {
        super.onNotifyMessageOpened(p0, p1)
        Log.e(TAG, p1?.notificationExtras)
    }

    // 接收到推送下来的自定义消息
    override fun onMessage(p0: Context?, p1: CustomMessage?) {
        super.onMessage(p0, p1)
        Log.e(TAG, "onMessage")
    }

}
