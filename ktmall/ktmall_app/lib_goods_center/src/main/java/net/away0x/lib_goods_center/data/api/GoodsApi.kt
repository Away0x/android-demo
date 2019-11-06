package net.away0x.lib_goods_center.data.api

import io.reactivex.Observable
import net.away0x.lib_base.data.protocol.BaseResp
import net.away0x.lib_goods_center.data.protocol.Goods
import retrofit2.http.*

/* 商品接口 */
interface GoodsApi {

    /* 获取商品列表 */
    @GET("goods/list")
    fun getGoodsList(
        @Query("categoryId") categoryId: Int,
        @Query("pageNo") pageNo: Int
    ): Observable<BaseResp<MutableList<Goods>?>>

    /* 获取商品列表 */
    @GET("goods/list")
    fun getGoodsListByKeyword(
        @Query("keyword") keyword: String,
        @Query("pageNo") pageNo: Int
    ): Observable<BaseResp<MutableList<Goods>?>>

    /* 获取商品详情 */
    @GET("goods/detail/{id}")
    fun getGoodsDetail(@Path("id") id: Int): Observable<BaseResp<Goods>>
}
