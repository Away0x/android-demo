package com.away0x.anim.bezier

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.away0x.anim.R

class BezierActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bezier)
    }

    // 二阶Bezier曲线
    fun secondBezierTest(view: View) {
        startActivity(Intent(this, SecondBezierActivity::class.java))
    }

    // 三阶Bezier曲线
    fun thirdBezierTest(view: View) {
        startActivity(Intent(this, ThirdBezierActivity::class.java))
    }

    // 两点连线的圆滑处理
    fun drawPadBezierTest(view: View) {
        startActivity(Intent(this, DrawPadActivity::class.java))
    }

    // 路径变换动画
    fun pathMorthingBezierTest(view: View) {
        startActivity(Intent(this, PathMorthingActivity::class.java))
    }

    // 波浪效果
    fun waveBezierTest(view: View) {
        startActivity(Intent(this, WaveActivity::class.java))
    }

    // 轨迹动画
    fun pathBezierTest(view: View) {
        startActivity(Intent(this, PathBezierActivity::class.java))
    }

}
