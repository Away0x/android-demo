package com.away0x.smartbutler.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.away0x.smartbutler.R
import com.away0x.smartbutler.entity.CourierData
import org.jetbrains.anko.find

class CourierAdapter(
    val context: Context,
    val data: MutableList<CourierData>
) : BaseAdapter() {

    private var inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var viewHolder: ViewHolder? = null
        var view = convertView

        // 第一次加载
        if (viewHolder == null) {
            view = inflater.inflate(R.layout.layout_courier_item, null)
            viewHolder = ViewHolder(
                datetimeTV = view.find(R.id.tv_datetime),
                remarkTV = view.find(R.id.tv_remark),
                zoneTV = view.find(R.id.tv_zone)
            )

            // 设置缓存
            view.tag = viewHolder
        } else {
            viewHolder = convertView?.tag as ViewHolder
        }

        // 设置数据
        val item = data[position]
        viewHolder.datetimeTV.text = item.datetime
        viewHolder.remarkTV.text = item.remark
        viewHolder.zoneTV.text = item.zone

        return view!!
    }

    override fun getItem(position: Int): Any {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return data.size
    }

    inner class ViewHolder(
        val datetimeTV: TextView,
        val remarkTV: TextView,
        val zoneTV: TextView
    )

}