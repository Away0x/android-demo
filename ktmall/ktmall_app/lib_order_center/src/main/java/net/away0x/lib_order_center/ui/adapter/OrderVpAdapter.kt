package net.away0x.lib_order_center.ui.adapter

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import net.away0x.lib_order_center.common.OrderConstant
import net.away0x.lib_order_center.ui.fragment.OrderFragment

/* 订单 Tab 对应 ViewPager */
class OrderVpAdapter(fm: FragmentManager, context: Context) : FragmentPagerAdapter(fm) {

    private val titles = arrayOf("全部", "待付款", "待收货", "已完成", "已取消")
    private val fragments = arrayOfNulls<OrderFragment>(5)

    override fun getItem(position: Int): Fragment {
        var fragment = fragments[position]
        if (fragment != null) {
            return fragment
        }

        fragment = OrderFragment()
        val bundle = Bundle()
        bundle.putInt(OrderConstant.KEY_ORDER_STATUS, position)
        fragment.arguments = bundle

        fragments[position] = fragment
        return fragment
    }

    override fun getCount(): Int {
        return titles.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return titles[position]
    }
}
