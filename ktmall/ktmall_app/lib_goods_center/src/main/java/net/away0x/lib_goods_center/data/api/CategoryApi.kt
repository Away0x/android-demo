package net.away0x.lib_goods_center.data.api

import io.reactivex.Observable
import net.away0x.lib_base.data.protocol.BaseResp
import net.away0x.lib_goods_center.data.protocol.Category
import net.away0x.lib_goods_center.data.protocol.GetCategoryReq
import retrofit2.http.Body
import retrofit2.http.POST

/*
    商品分类接口
 */
interface CategoryApi {
    /*
        获取商品分类列表
     */
    @POST("category/getCategory")
    fun getCategory(@Body req: GetCategoryReq): Observable<BaseResp<MutableList<Category>?>>
}
