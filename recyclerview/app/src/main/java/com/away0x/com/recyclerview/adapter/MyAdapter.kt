package com.away0x.com.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.away0x.com.recyclerview.R
import com.bumptech.glide.Glide

/**
编写 Adapter
    1. 创建一个类，继承 RecyclerView.Adapter
    2. 继承三个方法: onCreateViewHolder 创建视图、onBindViewHolder 填充数据、getItemCount 获取数量
    3. 写 Item 的 xml 布局
    4. 填充数据给 Item
*/
class MyAdapter(
    private val context: Context,
    private val list: MutableList<String>
) : RecyclerView.Adapter<MyAdapter.MainViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.simple_item, parent, false)
        return MainViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        Glide.with(context).load(list[position]).into(holder.imageView)
    }

    class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }

}