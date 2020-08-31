package com.away0x.com.drawerdemo.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.away0x.com.drawerdemo.R
import kotlinx.android.synthetic.main.view_holder.view.*

class MyListAdapter(private val isPager: Boolean = false) : ListAdapter<Int, RecyclerView.ViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder, parent, false)

        if (isPager) {
            view.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
            view.cellImageView.layoutParams.apply {
                height = 0
                width = 0
            }
        }

        return object : RecyclerView.ViewHolder(view) {}
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.cellImageView.setImageResource(getItem(position))
    }

    object DiffCallback : DiffUtil.ItemCallback<Int>() {
        override fun areItemsTheSame(oldItem: Int, newItem: Int) = oldItem == newItem
        override fun areContentsTheSame(oldItem: Int, newItem: Int) = oldItem == newItem
    }
}


fun iconList(): List<Int> {
    val arr = intArrayOf(
        R.drawable.ic_list_1,
        R.drawable.ic_list_2,
        R.drawable.ic_list_3,
        R.drawable.ic_list_4,
        R.drawable.ic_list_5,
        R.drawable.ic_list_6,
        R.drawable.ic_list_7,
        R.drawable.ic_list_8
    )

    return Array(50) {
        arr.random()
    }.toList()
}