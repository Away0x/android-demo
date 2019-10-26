package net.away0x.lib_goods_center.data.api

import io.reactivex.Observable
import net.away0x.lib_base.data.protocol.BaseResp
import net.away0x.lib_goods_center.data.protocol.GetGoodsDetailReq
import net.away0x.lib_goods_center.data.protocol.GetGoodsListByKeywordReq
import net.away0x.lib_goods_center.data.protocol.GetGoodsListReq
import net.away0x.lib_goods_center.data.protocol.Goods
import retrofit2.http.Body
import retrofit2.http.POST

/* 商品接口 */
interface GoodsApi {
    /*
        获取商品列表
     */
    @POST("goods/getGoodsList")
    fun getGoodsList(@Body req: GetGoodsListReq): Observable<BaseResp<MutableList<Goods>?>>

    /*
        获取商品列表
     */
    @POST("goods/getGoodsListByKeyword")
    fun getGoodsListByKeyword(@Body req: GetGoodsListByKeywordReq): Observable<BaseResp<MutableList<Goods>?>>

    /*
        获取商品详情
     */
    @POST("goods/getGoodsDetail")
    fun getGoodsDetail(@Body req: GetGoodsDetailReq): Observable<BaseResp<Goods>>
}
