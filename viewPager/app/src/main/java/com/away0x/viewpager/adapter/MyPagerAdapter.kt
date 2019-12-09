package com.away0x.viewpager.adapter

import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter

class MyPagerAdapter(
    private val views: MutableList<View>
) : PagerAdapter() {

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view == obj
    }

    override fun getCount(): Int {
        return views.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = views[position]
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(views[position])
    }

}