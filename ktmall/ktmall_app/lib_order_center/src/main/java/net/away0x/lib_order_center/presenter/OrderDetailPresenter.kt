package net.away0x.lib_order_center.presenter

import net.away0x.lib_base.ext.execute
import net.away0x.lib_base.presenter.BasePresenter
import net.away0x.lib_base.rx.BaseException
import net.away0x.lib_order_center.data.protocol.Order
import net.away0x.lib_order_center.presenter.view.OrderDetailView
import net.away0x.lib_order_center.service.OrderService
import javax.inject.Inject

/*
    订单详情页Presenter
 */
class OrderDetailPresenter @Inject constructor() : BasePresenter<OrderDetailView>() {

    @Inject
    lateinit var orderService: OrderService

    /*
        根据ID查询订单
     */
    fun getOrderById(orderId: Int) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        orderService.getOrderById(orderId).execute(lifecycleProvider, {
            mView.hideLoading()
            mView.onGetOrderByIdResult(it)
        }, {
            if (it is BaseException) {
                mView.onError(it.msg)
            }
            mView.hideLoading()
        })
    }

}
