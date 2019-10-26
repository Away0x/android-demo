package net.away0x.lib_goods_center.presenter.view

import net.away0x.lib_base.presenter.view.BaseView
import net.away0x.lib_goods_center.data.protocol.Category

/* 商品分类 视图回调 */
interface CategoryView : BaseView {

    //获取商品分类列表
    fun onGetCategoryResult(result: MutableList<Category>?)

}
