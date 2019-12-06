package com.away0x.smartbutler.ui.activity

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.away0x.smartbutler.R
import com.away0x.smartbutler.utils.UITools
import kotlinx.android.synthetic.main.activity_guide.*
import org.jetbrains.anko.startActivity

class GuideActivity : BaseActivity() {

    private val mViewList = mutableListOf<View>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide)

        initView()
    }

    private fun initView() {
        // back btn
        mBackIV.setOnClickListener {
            startActivity<LoginActivity>()
            finish()
        }

        // 设置默认图片
        setPointImg(true, false, false)

        // view pager
        val view1 = View.inflate(this, R.layout.guide_pager_item_one, null)
        val view2 = View.inflate(this, R.layout.guide_pager_item_two, null)
        val view3 = View.inflate(this, R.layout.guide_pager_item_three, null)

        view3.findViewById<Button>(R.id.mStartBtn).setOnClickListener {
            startActivity<LoginActivity>()
            finish()
        }

        UITools.setFont(this, view1.findViewById(R.id.mPager1TV))
        UITools.setFont(this, view2.findViewById(R.id.mPager2TV))
        UITools.setFont(this, view3.findViewById(R.id.mPager3TV))

        mViewList.add(view1)
        mViewList.add(view2)
        mViewList.add((view3))

        mViewPager.adapter = GuideViewPagerAdapter()
        mViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                setPointImg(
                    position == 0,
                    position == 1,
                    position == 2
                )

                mBackIV.visibility = if (position == 2) View.GONE else View.VISIBLE
            }
        })
    }

    private fun setPointImg(check1: Boolean, check2: Boolean, check3: Boolean) {
        val cfun = { c: Boolean ->
            if (c) R.drawable.point_on
            else R.drawable.point_off
        }

        mPoint1IV.setBackgroundResource(cfun(check1))
        mPoint2IV.setBackgroundResource(cfun(check2))
        mPoint3IV.setBackgroundResource(cfun(check3))
    }

    inner class GuideViewPagerAdapter : PagerAdapter() {

        override fun isViewFromObject(view: View, obj: Any): Boolean {
            return view == obj
        }

        override fun getCount(): Int {
            return mViewList.size
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            container.addView(mViewList[position])
            return mViewList[position]
        }

        override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
            container.removeView(mViewList[position])
        }

    }

}
