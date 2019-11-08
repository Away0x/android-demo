package net.away0x.lib_order_center.presenter.view

import net.away0x.lib_base.presenter.view.BaseView
import net.away0x.lib_order_center.data.protocol.ShipAddress

/*
    收货人列表 视图回调
 */
interface ShipAddressView : BaseView {

    //获取收货人列表回调
    fun onGetShipAddressResult(result: MutableList<ShipAddress>?)
    //设置默认收货人回调
    fun onSetDefaultResult(result: Boolean)
    //删除收货人回调
    fun onDeleteResult(result: Boolean)

}
