package net.away0x.lib_goods_center.presenter.view

import net.away0x.lib_base.presenter.view.BaseView
import net.away0x.lib_goods_center.data.protocol.Goods

/* 商品列表 视图回调 */
interface GoodsListView : BaseView {

    // 获取商品列表
    fun onGetGoodsListResult(result: MutableList<Goods>?)

}
