package net.away0x.lib_goods_center.data.api

import io.reactivex.Observable
import net.away0x.lib_base.data.protocol.BaseResp
import net.away0x.lib_goods_center.data.protocol.Category
import retrofit2.http.*

/*
    商品分类接口
 */
interface CategoryApi {
    /*
        获取商品分类列表
     */
    @GET("category/list")
    fun getCategory(@Query("parentId") parentId: Int): Observable<BaseResp<MutableList<Category>?>>
}
