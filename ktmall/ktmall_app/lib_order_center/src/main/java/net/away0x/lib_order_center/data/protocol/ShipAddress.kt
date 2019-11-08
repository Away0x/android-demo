package net.away0x.lib_order_center.data.protocol

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/*
   收货地址
 */
//@Parcelize
//data class ShipAddress(
//        val id: Int,
//        var shipUserName: String,
//        var shipUserMobile: String,
//        var shipAddress: String,
//        var shipIsDefault: Int
//) : Parcelable

data class ShipAddress(
        val id: Int,
        var shipUserName: String,
        var shipUserMobile: String,
        var shipAddress: String,
        var shipIsDefault: Int
)