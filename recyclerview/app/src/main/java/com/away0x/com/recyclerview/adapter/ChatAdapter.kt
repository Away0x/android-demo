package com.away0x.com.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.away0x.com.recyclerview.R
import com.away0x.com.recyclerview.model.Chat
import com.away0x.com.recyclerview.model.ItemType

/**
 * 显示多种布局的 recyclerView
 * 1. 在实体类中添加一个类型的字段
 * 2. 复写 adaater 的 getItemViewType 方法
 * 3. 通过类型判断加载不同的 viewHolder
 * 4. 填充数据
 */
class ChatAdapter(
    private val context: Context,
    private val data: List<Chat>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ItemType.SEND -> {
                val itemView = LayoutInflater.from(context).inflate(R.layout.chat_send_item, parent, false)
                SendViewHolder(itemView)
            }
            else -> {
                val itemView = LayoutInflater.from(context).inflate(R.layout.chat_receive_item, parent, false)
                ReceiveViewHolder(itemView)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = data[position]
        val content = item.content

        when (item.type) {
            ItemType.SEND -> {
                (holder as SendViewHolder).tvSend.text = content
            }
            ItemType.RECEIVE -> {
                (holder as ReceiveViewHolder).tvReceive.text = content
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        return data[position].type
    }

    /** 发送方的 ViewHolder */
    class SendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvSend: TextView = itemView.findViewById(R.id.tv_show)
    }

    /** 接收方的 ViewHolder */
    class ReceiveViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvReceive: TextView = itemView.findViewById(R.id.tv_show)
    }

}