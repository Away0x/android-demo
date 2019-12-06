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

}