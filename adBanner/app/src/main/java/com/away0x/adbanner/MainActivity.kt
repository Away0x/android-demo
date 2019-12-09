package com.away0x.adbanner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mBanner1Imgs = listOf(R.drawable.banner01, R.drawable.banner02, R.drawable.banner03)
    private val mBanner2Views = mutableListOf<View>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initBanner1()
        initBanner2()
    }

    // ------------------- banner 1 ----------------------

    // 初始化 banner 1
    private fun initBanner1() {
        mBannerVP.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment {
                val index = position % mBanner1Imgs.size
                return BannerFragment(mBanner1Imgs[index])
            }

            override fun getCount(): Int {
                return 10000 // 为了循环滚动，不能写成固定的
            }
        }

        // 自动滚动
        val handler = Handler()
        handler.postDelayed(object : Runnable {
            override fun run() {
                mBannerVP.currentItem = mBannerVP.currentItem + 1
                handler.postDelayed(this, 2000)
            }
        }, 2000)
    }

    // ------------------- banner 2 ----------------------

    // 初始化 banner 2
    private fun initBanner2() {
        for (i in 0..4) {
            val inflate = layoutInflater.inflate(R.layout.pager_item, null)
            val img = inflate.findViewById<ImageView>(R.id.iv)
            
            img.setImageResource(R.mipmap.ic_launcher)
            mBanner2Views.add(inflate)
        }

        mBannerVP2.adapter = object : PagerAdapter() {
            override fun isViewFromObject(view: View, obj: Any): Boolean {
                return view == obj
            }

            override fun getCount(): Int { return 1000 }

            override fun instantiateItem(container: ViewGroup, position: Int): Any {
                val view = mBanner2Views[position % 4]
                container.addView(view)
                return view
            }

            override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
                container.removeView(mBanner2Views[position % 4])
            }
        }

        mBannerVP2.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                mIndicator.setOffset(position, positionOffset)
            }

            override fun onPageSelected(position: Int) {}
        })
    }

}
