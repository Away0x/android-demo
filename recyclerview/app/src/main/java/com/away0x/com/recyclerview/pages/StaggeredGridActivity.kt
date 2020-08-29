package com.away0x.com.recyclerview.pages

import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.away0x.com.recyclerview.R
import com.away0x.com.recyclerview.adapter.ImageAdapter
import kotlinx.android.synthetic.main.activity_base_recycle.*

/**
 * 瀑布流
 */
class StaggeredGridActivity : AppCompatActivity() {

    private val list = mutableListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_recycle)

        initData()
        initView()
    }

    private fun initView() {
        val layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        recycleView.layoutManager = layoutManager
        recycleView.adapter = ImageAdapter(this, list)
        // 设置 item 之间的间距
        recycleView.addItemDecoration(SpacesItemDecoration(10))
    }

    private fun initData() {
        for (i in 0..6) {
            list.add(R.drawable.example1)
            list.add(R.drawable.example2)
            list.add(R.drawable.example3)
        }
    }

    /**
     * 设置 item 间距
     * 1. 继承 RecyclerView.ItemDecoration
     * 2. 重写 getItemOffsets
     * 3. 对参数 outRect 赋值
     */
    private class SpacesItemDecoration(private var space: Int = 0) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)

            outRect.run {
                top = space
                bottom = space
                left = space
                right = space
            }
        }
    }
}