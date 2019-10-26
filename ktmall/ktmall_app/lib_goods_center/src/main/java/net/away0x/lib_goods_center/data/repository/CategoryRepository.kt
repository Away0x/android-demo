package net.away0x.lib_goods_center.data.repository

import io.reactivex.Observable
import net.away0x.lib_base.data.net.RetrofitFactory
import net.away0x.lib_base.data.protocol.BaseResp
import net.away0x.lib_goods_center.data.api.CategoryApi
import net.away0x.lib_goods_center.data.protocol.Category
import net.away0x.lib_goods_center.data.protocol.GetCategoryReq
import javax.inject.Inject

/* 商品分类 数据层 */
class CategoryRepository @Inject constructor() {

    /*
        获取商品分类
     */
    fun getCategory(parentId: Int): Observable<BaseResp<MutableList<Category>?>> {
        return RetrofitFactory.instance.create(CategoryApi::class.java).getCategory(GetCategoryReq(parentId))
    }

}
