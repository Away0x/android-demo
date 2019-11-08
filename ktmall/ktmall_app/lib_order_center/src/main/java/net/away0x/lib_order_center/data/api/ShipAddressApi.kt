package net.away0x.lib_order_center.data.api

import io.reactivex.Observable
import net.away0x.lib_base.data.protocol.BaseResp
import retrofit2.http.POST
import net.away0x.lib_order_center.data.protocol.AddShipAddressReq
import net.away0x.lib_order_center.data.protocol.DeleteShipAddressReq
import net.away0x.lib_order_center.data.protocol.EditShipAddressReq
import net.away0x.lib_order_center.data.protocol.ShipAddress
import retrofit2.http.Body


/*
    地址管理 接口
 */
interface ShipAddressApi {

    /*
        添加收货地址
     */
    @POST("shipAddress/add")
    fun addShipAddress(@Body req: AddShipAddressReq): Observable<BaseResp<String>>

    /*
        删除收货地址
     */
    @POST("shipAddress/delete")
    fun deleteShipAddress(@Body req: DeleteShipAddressReq): Observable<BaseResp<String>>

    /*
        修改收货地址
     */
    @POST("shipAddress/modify")
    fun editShipAddress(@Body req: EditShipAddressReq): Observable<BaseResp<String>>

    /*
        查询收货地址列表
     */
    @POST("shipAddress/getList")
    fun getShipAddressList(): Observable<BaseResp<MutableList<ShipAddress>?>>

}
