package com.away0x.activitymanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        // 获取当前栈顶的 activity
        val topActivity = ActivityManager.instance.topActivity
        if (topActivity != null) {
            textView.text = "topActivity ${topActivity.javaClass.simpleName}"
        }
    }
}