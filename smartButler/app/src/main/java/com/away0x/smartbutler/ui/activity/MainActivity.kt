package com.away0x.smartbutler.ui.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.away0x.smartbutler.R
import com.away0x.smartbutler.ui.fragment.ButlerFragment
import com.away0x.smartbutler.ui.fragment.PictureFragment
import com.away0x.smartbutler.ui.fragment.UserFragment
import com.away0x.smartbutler.ui.fragment.WechatFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

class MainActivity : BaseActivity() {

    // tab titles
    private val mTitles = listOf(
        "服务管家",
        "微信精选",
        "图片社区",
        "个人中心"
    )
    // fragment list
    private val mFragments = mutableListOf<Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 去掉阴影
        actionBar?.elevation = 0f

        initData()
        initView()
    }

    private fun initData() {
        mFragments.add(ButlerFragment())
        mFragments.add(WechatFragment())
        mFragments.add(PictureFragment())
        mFragments.add(UserFragment())
    }

    private fun initView() {
        // mFloatBtn
        mFloatBtn.hide()
        mFloatBtn.setOnClickListener {
            startActivity<SettingActivity>()
        }

        // mViewPager
        mViewPager.offscreenPageLimit = mFragments.size
        mViewPager.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return mFragments[position]
            }

            override fun getCount(): Int {
                return mFragments.size
            }

            override fun getPageTitle(position: Int): CharSequence? {
                return mTitles[position]
            }
        }

        mViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                if (position == 0) {
                    mFloatBtn.hide()
                } else {
                    mFloatBtn.show()
                }
            }
        })

        // tab layout 绑定 view pager
        mTabLayout.setupWithViewPager(mViewPager)
    }
}
