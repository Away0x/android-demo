package com.away0x.hybrid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
    }

    private fun initView() {
        mWebView.loadUrl("file:///android_asset/web/index.html")

        mBtn.setOnClickListener {
            // 调用 web 挂载在 window 上的方法
            mWebView.evaluateJavascript("javascript:webFunc('"+ "我是来自 android 的数据"+ "')") {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        }
    }

}
