package net.away0x.lib_goods_center.presenter

import net.away0x.lib_base.ext.execute
import net.away0x.lib_base.presenter.BasePresenter
import net.away0x.lib_base.rx.BaseException
import net.away0x.lib_goods_center.presenter.view.GoodsListView
import net.away0x.lib_goods_center.service.GoodsService
import javax.inject.Inject

/* 商品列表 Presenter */
class GoodsListPresenter @Inject constructor() : BasePresenter<GoodsListView>() {

    @Inject
    lateinit var goodsService: GoodsService


    /* 获取商品列表 */
    fun getGoodsList(categoryId: Int, pageNo: Int) {
        if (!checkNetWork()) { return }

        mView.showLoading()

        goodsService.getGoodsList(categoryId, pageNo).execute(lifecycleProvider, {
            mView.onGetGoodsListResult(it)
            mView.hideLoading()
        }, {
            if (it is BaseException) {
                mView.onError(it.msg)
            }
            mView.hideLoading()
        })
    }

    /* 根据关键字 搜索商品 */
    fun getGoodsListByKeyword(keyword: String, pageNo: Int) {
        if (!checkNetWork()) { return }

        mView.showLoading()

        goodsService.getGoodsListByKeyword(keyword, pageNo).execute(lifecycleProvider, {
            mView.onGetGoodsListResult(it)
            mView.hideLoading()
        }, {
            if (it is BaseException) {
                mView.onError(it.msg)
            }
            mView.hideLoading()
        })
    }

}