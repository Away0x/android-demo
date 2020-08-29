package com.away0x.com.recyclerview.pages

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.away0x.com.recyclerview.R
import com.away0x.com.recyclerview.adapter.SplitAdapter
import com.away0x.com.recyclerview.view.RecycleViewDivider
import kotlinx.android.synthetic.main.activity_base_recycle.*

/**
 * 带分隔线的 recycleView
 */
class SplitLineRecyActivity : AppCompatActivity() {

    private val list = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_recycle)

        initData()
        initView()
    }

    private fun initView() {
        recycleView.layoutManager = LinearLayoutManager(this)
        recycleView.adapter = SplitAdapter(this, list)
        // 设置分隔线
        val recyclerViewDivider = RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL, R.drawable.splitline)
        //val recyclerViewDivider = RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL, 5, 127)
        recycleView.addItemDecoration(recyclerViewDivider)
    }

    private fun initData() {
        for (i in 0..30) {
            list.add("😁😁😁😁😁😁😁")
        }
    }
}