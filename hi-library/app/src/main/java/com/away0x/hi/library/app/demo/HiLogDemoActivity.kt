package com.away0x.hi.library.app.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.away0x.hi.library.app.R
import com.away0x.hi.library.log.HiLog
import com.away0x.hi.library.log.HiLogConfig
import com.away0x.hi.library.log.HiLogType
import kotlinx.android.synthetic.main.activity_hi_log_demo.*

class HiLogDemoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hi_log_demo)

        btn_log.setOnClickListener { printLog() }
    }

    private fun printLog() {
        // 自定义 Log 配置
        HiLog.log(object : HiLogConfig() {
            override fun includeThread(): Boolean {
                return true;
            }

            override fun stackTraceDepth(): Int {
                return 0;
            }
        }, HiLogType.E, "-----", "5566")

        // 使用默认的配置，MApplication 里面初始化的配置
        HiLog.a("9900")
    }
}