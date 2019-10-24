package net.away0x.ktmall.ui.adapter

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_home_discount_item.view.*
import net.away0x.ktmall.R
import net.away0x.lib_base.ui.adapter.BaseRecyclerViewAdapter
import net.away0x.lib_base.utils.GlideUtils

/* 首页折扣区域Adapter */
class HomeDiscountAdapter(context: Context):
    BaseRecyclerViewAdapter<String, HomeDiscountAdapter.ViewHolder>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext)
            .inflate(R.layout.layout_home_discount_item,parent,false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)

        // 加载图片
        GlideUtils.loadUrlImage(mContext,dataList[position],holder.itemView.mGoodsIconIv)
        // 静态假数据
        holder.itemView.mDiscountAfterTv.text = "￥123.00"
        holder.itemView.mDiscountBeforeTv.text = "$1000.00"
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        init {
            // 设置TextView样式
            view.mDiscountBeforeTv.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
            view.mDiscountBeforeTv.paint.isAntiAlias = true
        }
    }

}