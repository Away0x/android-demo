package net.away0x.lib_message_center.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_message_item.view.*
import net.away0x.lib_base.ext.loadUrl
import net.away0x.lib_base.ui.adapter.BaseRecyclerViewAdapter
import net.away0x.lib_message_center.R
import net.away0x.lib_message_center.data.protocol.Message

/* 消息数据 */
class MessageAdapter(context: Context) : BaseRecyclerViewAdapter<Message, MessageAdapter.ViewHolder>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext)
            .inflate(R.layout.layout_message_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)

        val model = dataList[position]
        // 消息图标
        holder.itemView.mMsgIconIv.loadUrl(model.icon)
        // 消息标题
        holder.itemView.mMsgTitleTv.text = model.title
        // 消息内容
        holder.itemView.mMsgContentTv.text = model.content
        // 消息时间
        holder.itemView.mMsgTimeTv.text = model.time
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}