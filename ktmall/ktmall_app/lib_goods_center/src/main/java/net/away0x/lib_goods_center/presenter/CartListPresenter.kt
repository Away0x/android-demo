package net.away0x.lib_goods_center.presenter

import net.away0x.lib_base.ext.execute
import net.away0x.lib_base.presenter.BasePresenter
import net.away0x.lib_base.rx.BaseException
import net.away0x.lib_goods_center.data.protocol.CartGoods
import net.away0x.lib_goods_center.presenter.view.CartListView
import net.away0x.lib_goods_center.service.CartService
import javax.inject.Inject

/* 购物车 Presenter */
class CartListPresenter @Inject constructor() : BasePresenter<CartListView>() {

    @Inject
    lateinit var cartService: CartService

    /* 获取购物车列表 */
    fun getCartList() {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()

        cartService.getCartList().execute(lifecycleProvider, {
            mView.hideLoading()
            mView.onGetCartListResult(it)
        }, {
            if (it is BaseException) {
                mView.onError(it.msg)
            }
            mView.hideLoading()
        })
    }

    /* 删除购物车商品 */
    fun deleteCartList(list: List<Int>) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        cartService.deleteCartList(list).execute(lifecycleProvider, {
            mView.hideLoading()
            mView.onDeleteCartListResult(it)
        }, {
            if (it is BaseException) {
                mView.onError(it.msg)
            }
            mView.hideLoading()
        })
    }

    /* 提交购物车商品 */
    fun submitCart(list: MutableList<CartGoods>, totalPrice: Long) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        cartService.submitCart(list, totalPrice).execute(lifecycleProvider, {
            mView.hideLoading()
            mView.onSubmitCartListResult(it)
        }, {
            if (it is BaseException) {
                mView.onError(it.msg)
            }
            mView.hideLoading()
        })
    }

}
