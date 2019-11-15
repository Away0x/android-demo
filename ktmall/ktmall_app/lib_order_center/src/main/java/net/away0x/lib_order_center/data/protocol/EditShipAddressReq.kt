package net.away0x.lib_order_center.data.protocol

/*
    修改收货地址
 */
data class EditShipAddressReq(val id:Int,val userName:String,val userMobile:String,val address:String,val isDefault:Int)
