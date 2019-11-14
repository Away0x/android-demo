package net.away0x.lib_order_center.data.api

import net.away0x.lib_order_center.data.protocol.*
import io.reactivex.Observable
import net.away0x.lib_base.data.protocol.BaseResp
import retrofit2.http.*


/*
    订单 接口
 */
interface OrderApi {

    /*
        取消订单
     */
    @POST("order/cancel/{id}")
    fun cancelOrder(@Path("id") id: Int): Observable<BaseResp<String>>

    /*
        确认订单
     */
    @POST("order/confirm/{id}")
    fun confirmOrder(@Path("id") id: Int): Observable<BaseResp<String>>

    /*
        根据ID获取订单
     */
    @GET("order/detail/{id}")
    fun getOrderById(@Path("id") id: Int): Observable<BaseResp<Order>>

    /*
        根据订单状态查询查询订单列表
     */
    @GET("order/list")
    fun getOrderList(@Query("orderStatus") orderStatus: Int): Observable<BaseResp<MutableList<Order>?>>

    /*
        提交订单
     */
    @POST("order/submit")
    fun submitOrder(@Body req: SubmitOrderReq): Observable<BaseResp<String>>

}
