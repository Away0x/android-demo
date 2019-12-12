package com.away0x.smartbutler.utils

import android.content.Context
import android.content.pm.PackageManager
import com.away0x.smartbutler.R

object Utils {

    // 获取版本号
    fun getVersion(context: Context): String {
        val pm = context.packageManager

        try {
            val info = pm.getPackageInfo(context.packageName, 0)
            return info.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            return context.getString(R.string.text_unknown)
        }
    }

}