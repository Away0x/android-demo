package com.away0x.viewpager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.GridView
import com.away0x.viewpager.adapter.MyGridAdapter
import com.away0x.viewpager.adapter.MyPagerAdapter
import com.away0x.viewpager.entity.HomeIconInfo
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mViews = mutableListOf<View>()
    // gridView 两页的数据
    private val mPagerOneData = mutableListOf<HomeIconInfo>()
    private val mPagerTwoData = mutableListOf<HomeIconInfo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initData()
        initView()
    }

    private fun initData() {
        val iconNames = resources.getStringArray(R.array.home_bar_labels)
        val typedArray = resources.obtainTypedArray(R.array.home_bar_icon)

        iconNames.forEachIndexed { i, s ->
            if (i < 8) {
                mPagerOneData.add(HomeIconInfo(s, typedArray.getResourceId(i, 0)))
            } else {
                mPagerTwoData.add(HomeIconInfo(s, typedArray.getResourceId(i, 0)))
            }
        }
    }

    private fun initView() {
        val pagerOne = layoutInflater.inflate(R.layout.home_gridview, null)
        val gridViewOne = pagerOne.findViewById<GridView>(R.id.gridView)
        gridViewOne.adapter = MyGridAdapter(this, mPagerOneData)
        gridViewOne.setOnItemClickListener { _, _, _, _ -> }

        val pagerTwo = layoutInflater.inflate(R.layout.home_gridview, null)
        val gridViewTwo = pagerTwo.findViewById<GridView>(R.id.gridView)
        gridViewTwo.adapter = MyGridAdapter(this, mPagerTwoData)
        gridViewTwo.setOnItemClickListener { _, _, _, _ -> }

        mViews.add(pagerOne)
        mViews.add(pagerTwo)

        mViewPager.adapter = MyPagerAdapter(mViews)
    }

}
