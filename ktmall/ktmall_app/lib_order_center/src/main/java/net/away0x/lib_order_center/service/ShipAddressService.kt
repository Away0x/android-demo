package net.away0x.lib_order_center.service

import io.reactivex.Observable
import net.away0x.lib_order_center.data.protocol.ShipAddress

/*
    收货人信息 业务接口
 */
interface ShipAddressService {

    /*
        添加收货地址
     */
    fun addShipAddress(shipUserName: String, shipUserMobile: String, shipAddress: String): Observable<ShipAddress>

    /*
    获取收货地址列表
 */
    fun getShipAddressList(): Observable<MutableList<ShipAddress>?>

    /*
     修改收货地址
  */
    fun editShipAddress(address:ShipAddress): Observable<ShipAddress>

    /*
    删除收货地址
 */
    fun deleteShipAddress(id: Int): Observable<Boolean>

}
