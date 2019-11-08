package net.away0x.lib_order_center.data.protocol

/*
    添加收货地址
 */
data class AddShipAddressReq(val shipUserName: String, val shipUserMobile: String, val shipAddress: String)
