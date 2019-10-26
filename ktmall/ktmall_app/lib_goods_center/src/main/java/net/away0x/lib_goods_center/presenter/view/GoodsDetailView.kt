package net.away0x.lib_goods_center.presenter.view

import net.away0x.lib_base.presenter.view.BaseView
import net.away0x.lib_goods_center.data.protocol.Goods

/* 商品详情 视图回调 */
interface GoodsDetailView : BaseView {

    // 获取商品详情
    fun onGetGoodsDetailResult(result: Goods)

    // 加入购物车
    fun onAddCartResult(result: Int)

}
