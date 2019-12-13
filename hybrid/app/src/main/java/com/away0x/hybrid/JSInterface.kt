package com.away0x.hybrid

import android.content.Context
import android.webkit.JavascriptInterface
import android.widget.Toast

class JSInterface(val context: Context) {

    @JavascriptInterface
    fun androidFunc(result: String): String {
        Toast.makeText(context, result, Toast.LENGTH_LONG).show()
        return "我是来自 android 的数据"
    }

}