package com.away0x.anim

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.away0x.anim.bezier.BezierActivity
import com.away0x.anim.path.PathActivity
import com.away0x.anim.vector.VectorActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun pathPage(view: View) {
        startActivity(Intent(this, PathActivity::class.java))
    }

    fun bezierPage(view: View) {
        startActivity(Intent(this, BezierActivity::class.java))
    }

    fun vectorPage(view: View) {
        startActivity(Intent(this, VectorActivity::class.java))
    }

}
