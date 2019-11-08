package net.away0x.lib_order_center.presenter.view

import net.away0x.lib_base.presenter.view.BaseView
import net.away0x.lib_order_center.data.protocol.Order

/*
    订单详情页 视图回调
 */
interface OrderDetailView : BaseView {

    fun onGetOrderByIdResult(result: Order)
}
