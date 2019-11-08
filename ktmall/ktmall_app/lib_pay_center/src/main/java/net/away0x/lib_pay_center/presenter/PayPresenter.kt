package net.away0x.lib_pay_center.presenter

import net.away0x.lib_base.ext.execute
import net.away0x.lib_base.presenter.BasePresenter
import net.away0x.lib_base.rx.BaseException
import net.away0x.lib_pay_center.presenter.view.PayView
import net.away0x.lib_pay_center.service.PayService
import javax.inject.Inject

/*
    支付Presenter
 */
class PayPresenter @Inject constructor() : BasePresenter<PayView>() {
    @Inject
    lateinit var service: PayService

    /*
        获取支付签名
     */
    fun getPaySign(orderId: Int, totalPrice: Long) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        service.getPaySign(orderId, totalPrice).execute(lifecycleProvider, {
            mView.hideLoading()
            mView.onGetSignResult(it)
        }, {
            if (it is BaseException) {
                mView.onError(it.msg)
            }
            mView.hideLoading()
        })
    }

    /*
        订单支付，同步订单状态
     */
    fun payOrder(orderId: Int) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        service.payOrder(orderId).execute(lifecycleProvider, {
            mView.hideLoading()
            mView.onPayOrderResult(it)
        }, {
            if (it is BaseException) {
                mView.onError(it.msg)
            }
            mView.hideLoading()
        })
    }



}
