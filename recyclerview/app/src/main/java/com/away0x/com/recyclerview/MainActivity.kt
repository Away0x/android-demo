package com.away0x.com.recyclerview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.away0x.com.recyclerview.pages.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
    }

    private fun initView() {
        button1.setOnClickListener(this)
        button2.setOnClickListener(this)
        button3.setOnClickListener(this)
        button4.setOnClickListener(this)
        button5.setOnClickListener(this)
        button6.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v == null) return

        when (v.id) {
            R.id.button1 -> {
                startActivity(Intent(this, BaseRecyclerViewActivity::class.java))
            }
            R.id.button2 -> {
                startActivity(Intent(this, StaggeredGridActivity::class.java))
            }
            R.id.button3 -> {
                startActivity(Intent(this, SplitLineRecyActivity::class.java))
            }
            R.id.button4 -> {
                startActivity(Intent(this, ChatGridActivity::class.java))
            }
            R.id.button5 -> {
                startActivity(Intent(this, ClickGridActivity::class.java))
            }
            R.id.button6 -> {
                startActivity(Intent(this, RefeshRecycleActivity::class.java))
            }
        }
    }
}