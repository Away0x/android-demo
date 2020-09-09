package com.away0x.activitymanager

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ActivityManager.instance.addFrontBackCallback(object : ActivityManager.FrontBackCallback {
            override fun onChanged(front: Boolean) {
                Toast.makeText(applicationContext, "当前处于 ${if (front) "前台" else "后台"}", Toast.LENGTH_LONG).show()
            }
        })

        button.setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java))
        }
    }
}