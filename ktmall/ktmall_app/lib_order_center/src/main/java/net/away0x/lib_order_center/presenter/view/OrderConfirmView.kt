package net.away0x.lib_order_center.presenter.view

import net.away0x.lib_base.presenter.view.BaseView
import net.away0x.lib_order_center.data.protocol.Order

/*
    订单确认页 视图回调
 */
interface OrderConfirmView : BaseView {

    //获取订单回调
    fun onGetOrderByIdResult(result:Order)
    //提交订单回调
    fun onSubmitOrderResult(result:Boolean)
}
