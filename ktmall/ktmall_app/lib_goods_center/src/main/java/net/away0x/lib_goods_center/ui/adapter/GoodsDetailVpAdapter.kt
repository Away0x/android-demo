package net.away0x.lib_goods_center.ui.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import net.away0x.lib_goods_center.ui.fragment.GoodsDetailTabOneFragment
import net.away0x.lib_goods_center.ui.fragment.GoodsDetailTabTwoFragment

/* 商品详情ViewPager Adapter */
class GoodsDetailVpAdapter(fm: FragmentManager, context: Context) : FragmentPagerAdapter(fm) {

    private val titles = arrayOf("商品", "详情")

    override fun getItem(position: Int): Fragment {
        return if (position == 0){
            GoodsDetailTabOneFragment()
        } else {
            GoodsDetailTabTwoFragment()
        }
    }

    override fun getCount(): Int {
        return titles.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return titles[position]
    }
}
