package net.away0x.lib_order_center.presenter

import net.away0x.lib_base.ext.execute
import net.away0x.lib_base.presenter.BasePresenter
import net.away0x.lib_base.rx.BaseException
import net.away0x.lib_order_center.data.protocol.Order
import net.away0x.lib_order_center.presenter.view.OrderListView
import net.away0x.lib_order_center.service.OrderService
import javax.inject.Inject

/*
    订单列表Presenter
 */
class OrderListPresenter @Inject constructor() : BasePresenter<OrderListView>() {

    @Inject
    lateinit var orderService: OrderService

    /*
        根据订单状态获取订单列表
     */
    fun getOrderList(orderStatus: Int) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        orderService.getOrderList(orderStatus).execute(lifecycleProvider, {
            mView.hideLoading()
            mView.onGetOrderListResult(it)
        }, {
            if (it is BaseException) {
                mView.onError(it.msg)
            }
            mView.hideLoading()
        })
    }

    /*
        订单确认收货
     */
    fun confirmOrder(orderId: Int) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        orderService.confirmOrder(orderId).execute(lifecycleProvider, {
            mView.hideLoading()
            mView.onConfirmOrderResult(it)
        }, {
            if (it is BaseException) {
                mView.onError(it.msg)
            }
            mView.hideLoading()
        })
    }

    /*
        取消订单
     */
    fun cancelOrder(orderId: Int) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        orderService.cancelOrder(orderId).execute(lifecycleProvider, {
            mView.hideLoading()
            mView.onCancelOrderResult(it)
        }, {
            if (it is BaseException) {
                mView.onError(it.msg)
            }
            mView.hideLoading()
        })
    }

}
