package net.away0x.lib_order_center.service.impl

import io.reactivex.Observable
import net.away0x.lib_base.rx.baseFunc
import net.away0x.lib_base.rx.baseFuncBoolean
import net.away0x.lib_order_center.data.protocol.ShipAddress
import net.away0x.lib_order_center.data.repository.ShipAddressRepository
import net.away0x.lib_order_center.service.ShipAddressService
import javax.inject.Inject

/*
    收货人信息 业务实现类
 */
class ShipAddressServiceImpl @Inject constructor(): ShipAddressService {

    @Inject
    lateinit var repository: ShipAddressRepository

    /*
        添加收货人信息
     */
    override fun addShipAddress(shipUserName: String, shipUserMobile: String, shipAddress: String): Observable<Boolean> {
        return repository.addShipAddress(shipUserName,shipUserMobile,shipAddress).flatMap(::baseFuncBoolean)

    }

    /*
        获取收货人信息列表
     */
    override fun getShipAddressList(): Observable<MutableList<ShipAddress>?> {
        return repository.getShipAddressList().flatMap { baseFunc(it) }
    }

    /*
        修改收货人信息
     */
    override fun editShipAddress(address: ShipAddress): Observable<Boolean> {
        return  repository.editShipAddress(address).flatMap(::baseFuncBoolean)
    }

    /*
        删除收货人信息
     */
    override fun deleteShipAddress(id: Int): Observable<Boolean> {
        return repository.deleteShipAddress(id).flatMap(::baseFuncBoolean)
    }
}
