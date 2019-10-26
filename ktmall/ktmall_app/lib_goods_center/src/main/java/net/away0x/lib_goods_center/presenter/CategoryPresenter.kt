package net.away0x.lib_goods_center.presenter

import net.away0x.lib_base.ext.execute
import net.away0x.lib_base.presenter.BasePresenter
import net.away0x.lib_base.rx.BaseException
import net.away0x.lib_goods_center.presenter.view.CategoryView
import net.away0x.lib_goods_center.service.CategoryService
import javax.inject.Inject

/* 商品分类 Presenter */
class CategoryPresenter @Inject constructor() : BasePresenter<CategoryView>() {

    @Inject
    lateinit var categoryService: CategoryService


    /* 获取商品分类列表 */
    fun getCategory(parentId:Int) {
        if (!checkNetWork()) { return }

        mView.showLoading()
        categoryService.getCategory(parentId)
            .execute(lifecycleProvider, {
                mView.onGetCategoryResult(it)
                mView.hideLoading()
            }, {
                if (it is BaseException) {
                    mView.onError(it.msg)
                }
                mView.hideLoading()
            })
    }

}