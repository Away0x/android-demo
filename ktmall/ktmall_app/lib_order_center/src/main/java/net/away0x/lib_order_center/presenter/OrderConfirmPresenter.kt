package net.away0x.lib_order_center.presenter

import net.away0x.lib_base.ext.execute
import net.away0x.lib_base.presenter.BasePresenter
import net.away0x.lib_base.rx.BaseException
import net.away0x.lib_order_center.data.protocol.Order
import net.away0x.lib_order_center.presenter.view.OrderConfirmView
import net.away0x.lib_order_center.service.OrderService
import javax.inject.Inject

/*
    订单确认页 Presenter
 */
class OrderConfirmPresenter @Inject constructor() : BasePresenter<OrderConfirmView>() {

    @Inject
    lateinit var orderService:OrderService

    /*
        根据ID获取订单
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

    /*
        提交订单
     */
    fun submitOrder(order: Order) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        orderService.submitOrder(order).execute(lifecycleProvider, {
            mView.hideLoading()
            mView.onSubmitOrderResult(it)
        }, {
            if (it is BaseException) {
                mView.onError(it.msg)
            }
            mView.hideLoading()
        })
    }


}
