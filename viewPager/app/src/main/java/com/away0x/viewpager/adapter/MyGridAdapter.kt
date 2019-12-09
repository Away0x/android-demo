package com.away0x.viewpager.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.away0x.viewpager.R
import com.away0x.viewpager.entity.HomeIconInfo

class MyGridAdapter(
    private val context: Context,
    private val data: MutableList<HomeIconInfo>
) : BaseAdapter() {

    override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View {
        val inflate = LayoutInflater.from(context).inflate(R.layout.grid_item, null)
        val img = inflate.findViewById<ImageView>(R.id.iv)
        val text = inflate.findViewById<TextView>(R.id.tv)

        img.setImageResource(data[position].iconID)
        text.text = data[position].iconName

        return inflate
    }

    override fun getItem(p0: Int): Any? {
        return null
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return data.size
    }

}