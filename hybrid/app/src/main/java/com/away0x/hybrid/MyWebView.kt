package com.away0x.hybrid

import android.app.AlertDialog
import android.webkit.*


import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet

class MyWebView : WebView {

    constructor(context: Context): super(context)
    constructor(context: Context, attr: AttributeSet): super(context, attr)

    init {
        initJSBridge()
        initWebView()
    }

    @SuppressLint("JavascriptInterface")
    private fun initJSBridge() {
        addJavascriptInterface(JSInterface(context), "androidJSBridge")
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        // 允许可运行 js
        settings.javaScriptEnabled = true
        // 不可缩放
        settings.setSupportZoom(false)
        settings.builtInZoomControls = false
        settings.displayZoomControls = true
        // 缓存
        settings.cacheMode = WebSettings.LOAD_DEFAULT
        // 网页在 app 内部打开，而不是使用外部浏览器
        webViewClient = WebViewClient()

        // chrome client 设置
        webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
            }

            // 网页 alert
//            override fun onJsAlert(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {
//                val builder = AlertDialog.Builder(context)
//                builder.setMessage(message)
//                builder.setNegativeButton("确定", null)
//                builder.create().show()
//
//                return true
//            }
        }
    }


}