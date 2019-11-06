package net.away0x.lib_goods_center.presenter

import net.away0x.lib_base.ext.execute
import net.away0x.lib_base.presenter.BasePresenter
import net.away0x.lib_base.rx.BaseException
import net.away0x.lib_base.utils.AppPrefsUtils
import net.away0x.lib_goods_center.common.GoodsConstant
import net.away0x.lib_goods_center.presenter.view.GoodsDetailView
import net.away0x.lib_goods_center.service.CartService
import net.away0x.lib_goods_center.service.GoodsService
import javax.inject.Inject

/* 商品详情 Presenter */
class GoodsDetailPresenter @Inject constructor() : BasePresenter<GoodsDetailView>() {

    @Inject
    lateinit var goodsService: GoodsService

    @Inject
    lateinit var cartService: CartService

    /* 获取商品详情 */
    fun getGoodsDetailList(goodsId: Int) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()

        goodsService.getGoodsDetail(goodsId).execute(lifecycleProvider, {
            mView.onGetGoodsDetailResult(it)
            mView.hideLoading()
        }, {
            if (it is BaseException) {
                mView.onError(it.msg)
            }
            mView.hideLoading()
        })
    }

    /* 加入购物车 */
    fun addCart(goodsId: Int, goodsDesc: String, goodsIcon: String, goodsPrice: Long,
                goodsCount: Int, goodsSku: String) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        cartService.addCart(goodsId, goodsDesc, goodsIcon, goodsPrice, goodsCount, goodsSku)
            .execute(lifecycleProvider, {
                mView.hideLoading()
                AppPrefsUtils.putInt(GoodsConstant.SP_CART_SIZE, it)
                mView.onAddCartResult(it)
            }, {
                if (it is BaseException) {
                    mView.onError(it.msg)
                }
                mView.hideLoading()
            })
    }

}
