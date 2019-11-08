package net.away0x.lib_order_center.presenter.view

import net.away0x.lib_base.presenter.view.BaseView
import net.away0x.lib_order_center.data.protocol.Order

/*
    编辑收货人信息 视图回调
 */
interface EditShipAddressView : BaseView {
    //添加收货人回调
    fun onAddShipAddressResult(result: Boolean)
    //修改收货人回调
    fun onEditShipAddressResult(result: Boolean)
}
