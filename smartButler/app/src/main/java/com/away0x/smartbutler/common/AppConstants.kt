package com.away0x.smartbutler.common

import android.content.Context
import com.away0x.smartbutler.utils.ShareUtils

object AppConstants {

    // 闪屏延时
    const val SPLASH_DEPLAY: Long = 3000
    // 判断是否是第一次运行
    const val IS_FIRST_LAUNCH = "is_first_launch"

    fun isFirstLaunch(context: Context): Boolean {
        val isFirst = ShareUtils.getBoolean(context, IS_FIRST_LAUNCH, true)

        if (isFirst) {
            ShareUtils.putBoolean(context, IS_FIRST_LAUNCH, false)
        }

        return isFirst
    }

    // bmob key
    const val BMOB_APP_ID = "c7fb775ba77e0a804d6408e0986d9158"

    // 快递查询 key https://www.juhe.cn/docs/api/id/43
    // http://v.juhe.cn/exp/index?key=key&com=sf&no=575677355677
    const val COURIER_KEY = "388bf1388cbaf6700709e97c41211b2e"
    const val COURIER_API = "http://v.juhe.cn/exp/index?key=$COURIER_KEY"

    // 归属地查询  https://www.juhe.cn/docs/api/id/11
    // http://apis.juhe.cn/mobile/get?key=&phone=
    const val PHONE_KEY = "xxx"
    const val PHONE_API = "http://apis.juhe.cn/mobile/get?key=$PHONE_KEY"

}