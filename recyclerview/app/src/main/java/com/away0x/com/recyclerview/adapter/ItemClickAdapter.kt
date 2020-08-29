package com.away0x.com.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.away0x.com.recyclerview.R
import com.bumptech.glide.Glide
import kotlin.random.Random

class ItemClickAdapter(
    private val context: Context,
    private val data: MutableList<String>
) : RecyclerView.Adapter<ItemClickAdapter.MainViewHolder>() {

    var onItemClickListener: OnItemClickListener? = null
    var onItemLongClickListener: OnItemLongClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.image_item, parent, false)
        return MainViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        Glide.with(context).load(data[position]).into(holder.imageView)

        val height = Random.nextInt(1000)
        holder.imageView.run {
            layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height)
            setOnClickListener { onItemClickListener?.onClick(position) }
            setOnLongClickListener {
                onItemLongClickListener?.onClick(position)
                return@setOnLongClickListener true
            }
        }
    }

    override fun getItemCount(): Int = data.size

    class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }

    interface OnItemClickListener {
        fun onClick(position: Int)
    }

    interface OnItemLongClickListener {
        fun onClick(position: Int)
    }

}