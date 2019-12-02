package net.away0x.welcome

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_guide.*

class GuideActivity : AppCompatActivity() {

    private val imgRes = arrayListOf(
        R.mipmap.guide_1,
        R.mipmap.guide_2,
        R.mipmap.guide_3,
        R.mipmap.guide_4
    )
    private val mViewList = mutableListOf<View>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide)

        initData()

        mPagerAdapter.adapter = MyPagerAdapter(mViewList)
        mPagerAdapter.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                mBtnStart.visibility = if (position == (imgRes.size - 1))
                    View.VISIBLE
                else
                    View.GONE
            }
        })

        mBtnStart.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun initData() {
        imgRes.forEach {
            val inflate = layoutInflater.inflate(R.layout.guide_item, null)
            val img = inflate.findViewById<ImageView>(R.id.mGuideImgView)
            img.setBackgroundResource(it)
            mViewList.add(inflate)
        }
    }

    class MyPagerAdapter(
        val list: MutableList<View>
    ) : PagerAdapter() {

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val view = list.get(position)
            container.addView(view)
            return view
        }

        override fun isViewFromObject(view: View, obj: Any): Boolean {
            return view == obj
        }

        override fun getCount(): Int {
            return list.size
        }

        override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
            container.removeView(list.get(position))
        }

    }
}
