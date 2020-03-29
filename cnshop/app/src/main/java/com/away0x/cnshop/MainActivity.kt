package com.away0x.cnshop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.away0x.cnshop.fragment.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.tab_indicator.view.*

class MainActivity : AppCompatActivity() {

    private lateinit var mInflater: LayoutInflater

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
    }

    private fun initView() {
        mInflater = LayoutInflater.from(this)

        initTab()
    }

    private fun initTab() {
        mTabHost.setup(this, supportFragmentManager, R.id.realtabcontent)

        val titles = listOf(R.string.home, R.string.hot, R.string.catagory, R.string.cart, R.string.mine)
        val icons = listOf(
                R.drawable.selector_icon_home,
                R.drawable.selector_icon_hot,
                R.drawable.selector_icon_category,
                R.drawable.selector_icon_cart,
                R.drawable.selector_icon_mine)


        titles.forEachIndexed { i, e ->
            val tab = mTabHost.newTabSpec(getString(e))
            val view = mInflater.inflate(R.layout.tab_indicator, null)
            view.icon_tab.setBackgroundResource(icons[i])
            view.txt_indicator.setText(e)

            tab.setIndicator(view)
            mTabHost.addTab(tab, HomeFragment::class.java, null)
        }

        mTabHost.tabWidget.showDividers = LinearLayout.SHOW_DIVIDER_NONE
        mTabHost.currentTab = 0
    }
}
