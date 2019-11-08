package net.away0x.lib_order_center.presenter.view

import net.away0x.lib_base.presenter.view.BaseView
import net.away0x.lib_order_center.data.protocol.Order

/*
    订单列表 视图回调
 */
interface OrderListView : BaseView {

    //获取订单列表回调
    fun onGetOrderListResult(result:MutableList<Order>?)
    //确认订单回调
    fun onConfirmOrderResult(result:Boolean)
    //取消订单回调
    fun onCancelOrderResult(result:Boolean)

}
