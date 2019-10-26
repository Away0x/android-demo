package net.away0x.lib_goods_center.data.api

import io.reactivex.Observable
import net.away0x.lib_base.data.protocol.BaseResp
import net.away0x.lib_goods_center.data.protocol.AddCartReq
import net.away0x.lib_goods_center.data.protocol.CartGoods
import net.away0x.lib_goods_center.data.protocol.DeleteCartReq
import net.away0x.lib_goods_center.data.protocol.SubmitCartReq
import retrofit2.http.Body
import retrofit2.http.POST

/* 购物车 接口 */
interface CartApi {
    /*
        获取购物车列表
     */
    @POST("cart/getList")
    fun getCartList(): Observable<BaseResp<MutableList<CartGoods>?>>

    /*
        添加商品到购物车
     */
    @POST("cart/add")
    fun addCart(@Body req: AddCartReq): Observable<BaseResp<Int>>

    /*
        删除购物车商品
     */
    @POST("cart/delete")
    fun deleteCartList(@Body req: DeleteCartReq): Observable<BaseResp<String>>

    /*
        提交购物车商品
     */
    @POST("cart/submit")
    fun submitCart(@Body req: SubmitCartReq): Observable<BaseResp<Int>>
}
