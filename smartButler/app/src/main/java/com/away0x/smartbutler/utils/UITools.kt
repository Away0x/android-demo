package com.away0x.smartbutler.utils

import android.content.Context
import android.graphics.Typeface
import android.widget.TextView

// ui 工具
object UITools {

    // 设置字体
    fun setFont(context: Context, tv: TextView) {
        val fontType = Typeface.createFromAsset(context.assets, "fonts/FONT.TTF")
        tv.typeface = fontType
    }

}