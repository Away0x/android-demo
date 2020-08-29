package com.away0x.com.recyclerview.pages

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.away0x.com.recyclerview.R
import com.away0x.com.recyclerview.adapter.SplitAdapter
import kotlinx.android.synthetic.main.activity_refresh_recycle.*

class RefeshRecycleActivity : AppCompatActivity() {

    private val list = mutableListOf<String>()
    private lateinit var adpater: SplitAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_refresh_recycle)

        initData()
        initView()
    }

    private fun initView() {
        recycleView.layoutManager = GridLayoutManager(this, 3)
        adpater = SplitAdapter(this, list)
        recycleView.adapter = adpater

        swipeRefreshLayout.run {
            // 设置下拉出现的小圆圈是否是缩放出现，出现的位置，最大的下拉位置
            // setProgressViewOffset(true, 50, 200)

            // 设置下拉圆圈的大小，LARGE DEFAULT
            // setSize(SwipeRefreshLayout.LARGE)

            // 设置下拉圆圈上的颜色
            // setColorSchemeResources(
            //     android.R.color.holo_blue_bright,
            //     android.R.color.holo_blue_bright,
            //     android.R.color.holo_blue_bright,
            //     android.R.color.holo_blue_bright
            // )

            // 禁用下拉刷新
            // setEnabled(false)

            // 设置下拉圆圈的背景
            // setProgressBackgroundColor(R.color.red)

            // 设置手势下拉刷新的监听
            setOnRefreshListener {
                // 刷新动画开始后回调到此方法
                initData()
                adpater.notifyDataSetChanged()
                swipeRefreshLayout.isRefreshing = false
            }
        }
    }

    private fun initData() {
        for (i in 0 until 30) {
            list.add("😃😃😃${i}")
        }
    }
}