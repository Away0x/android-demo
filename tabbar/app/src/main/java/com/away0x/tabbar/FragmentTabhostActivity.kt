package com.away0x.tabbar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_fragment_tabhost.*

class FragmentTabhostActivity : AppCompatActivity() {

    private val mFragments = listOf(
        MainFragment::class.java,
        AroundFragment::class.java,
        MineFragment::class.java,
        MoreFragment::class.java
    )

    private val mImgs = listOf(
        R.drawable.ic_home_tab_index_selector,
        R.drawable.ic_home_tab_near_selector,
        R.drawable.ic_home_tab_my_selector,
        R.drawable.ic_home_tab_more_selector
    )

    private val mTitles = listOf(
        "首页",
        "周边",
        "我的",
        "更多"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_tabhost)

        initView()
    }

    private fun initView() {
        mTabHost.setup(this, supportFragmentManager, android.R.id.tabcontent)

        mFragments.forEachIndexed { i, f ->
            val inflater = layoutInflater.inflate(R.layout.tab_item, null)
            val img = inflater.findViewById<ImageView>(R.id.iv)
            val title = inflater.findViewById<TextView>(R.id.tv)

            img.setImageResource(mImgs[i])
            title.text = mTitles[i]

            // newTabSpec 需要加上 i 进行标识，不然无法实现切换
            mTabHost.addTab(mTabHost.newTabSpec(i.toString()).setIndicator(inflater), f, null)
        }
    }
}
