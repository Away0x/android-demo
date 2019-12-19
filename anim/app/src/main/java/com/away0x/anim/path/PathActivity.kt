package com.away0x.anim.path

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.away0x.anim.R

class PathActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_path)
    }

    // 路径绘制
    fun pathTracing(view: View) {
        startActivity(Intent(this, PathTracingActivity::class.java))
    }

    // 路径绘制-Dash
    fun pathPaint(view: View) {
        startActivity(Intent(this, PathPaintActivity::class.java))
    }

    // Path Pos Tan
    fun pathPosTan(view: View) {
        startActivity(Intent(this, PathPosTanActivity::class.java))
    }

}
