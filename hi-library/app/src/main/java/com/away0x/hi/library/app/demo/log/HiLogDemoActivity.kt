package com.away0x.hi.library.app.demo.log

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.away0x.hi.library.app.R
import com.away0x.hi.library.log.HiLog
import com.away0x.hi.library.log.HiLogConfig
import com.away0x.hi.library.log.HiLogManager
import com.away0x.hi.library.log.HiLogType
import com.away0x.hi.library.log.printer.HiViewPrinter
import kotlinx.android.synthetic.main.activity_hi_log_demo.*

class HiLogDemoActivity : AppCompatActivity() {
    lateinit var viewPrinter: HiViewPrinter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hi_log_demo)

        // 日志可视化面板
        viewPrinter = HiViewPrinter(this)
        viewPrinter.viewProvider.showFloatingView()

        btn_log.setOnClickListener { printLog() }
    }

    private fun printLog() {
        HiLogManager.getInstance().addPrinter(viewPrinter)

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