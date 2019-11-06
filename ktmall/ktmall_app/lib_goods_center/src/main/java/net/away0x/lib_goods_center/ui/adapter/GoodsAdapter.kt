package net.away0x.lib_goods_center.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_goods_item.view.*
import net.away0x.lib_base.ext.loadUrl
import net.away0x.lib_base.ui.adapter.BaseRecyclerViewAdapter
import net.away0x.lib_base.utils.YuanFenConverter
import net.away0x.lib_goods_center.R
import net.away0x.lib_goods_center.data.protocol.Goods

/* 商品数据适配器 */
class GoodsAdapter(context: Context) : BaseRecyclerViewAdapter<Goods, GoodsAdapter.ViewHolder>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext)
            .inflate(R.layout.layout_goods_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)

        val model = dataList[position]
        // 商品图标
        holder.itemView.mGoodsIconIv.loadUrl(model.defaultIcon)
        // 商品描述
        holder.itemView.mGoodsDescTv.text = model.desc
        // 商品价格
        holder.itemView.mGoodsPriceTv.text = YuanFenConverter.changeF2YWithUnit(model.defaultPrice)
        // 商品销量及库存
        holder.itemView.mGoodsSalesStockTv.text = "销量${model.salesCount}件     库存${model.stockCount}"
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}