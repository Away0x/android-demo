package net.away0x.lib_order_center.data.api

import io.reactivex.Observable
import net.away0x.lib_base.data.protocol.BaseResp
import retrofit2.http.POST
import net.away0x.lib_order_center.data.protocol.AddShipAddressReq
import net.away0x.lib_order_center.data.protocol.DeleteShipAddressReq
import net.away0x.lib_order_center.data.protocol.EditShipAddressReq
import net.away0x.lib_order_center.data.protocol.ShipAddress
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Path


/*
    地址管理 接口
 */
interface ShipAddressApi {

    /*
        添加收货地址
     */
    @POST("address/add")
    fun addShipAddress(@Body req: AddShipAddressReq): Observable<BaseResp<ShipAddress>>

    /*
        删除收货地址
     */
    @POST("address/delete/{id}")
    fun deleteShipAddress(@Path("id") id: Int): Observable<BaseResp<String>>

    /*
        修改收货地址
     */
    @POST("address/modify")
    fun editShipAddress(@Body req: EditShipAddressReq): Observable<BaseResp<ShipAddress>>

    /*
        查询收货地址列表
     */
    @GET("address/list")
    fun getShipAddressList(): Observable<BaseResp<MutableList<ShipAddress>?>>

}
