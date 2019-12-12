package com.away0x.smartbutler.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Typeface
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.util.*

// ui 工具
object UITools {

    // 设置字体
    fun setFont(context: Context, tv: TextView) {
        val fontType = Typeface.createFromAsset(context.assets, "fonts/FONT.TTF")
        tv.typeface = fontType
    }

    // //保存图片到 shareutils
    @RequiresApi(Build.VERSION_CODES.O)
    fun putImageToShare(context: Context, img: ImageView) {
        val drawable = img.drawable as BitmapDrawable
        val bitmap = drawable.bitmap

        // 1. 将 Bitmap 压缩成字节数组输出流
        val byStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byStream)

        // 2. 利用 Base64 将我们的字节数组输出流转换成 String
        val byteArr = byStream.toByteArray()
        val imgStr = Base64.getEncoder().encode(byteArr).toString()

        // 3. 将 String 保存 ShareUtils
        ShareUtils.putString(context, "image_title", imgStr)
    }

    // 读取图片
    @RequiresApi(Build.VERSION_CODES.O)
    fun getImageToShare(context: Context, img: ImageView) {
        val imgStr = ShareUtils.getString(context, "image_title", "")
        if (imgStr.isEmpty()) return

        val byteArr = Base64.getDecoder().decode(imgStr)
        val byStream = ByteArrayInputStream(byteArr)
        val bitmap = BitmapFactory.decodeStream(byStream)
        img.setImageBitmap(bitmap)
    }

}