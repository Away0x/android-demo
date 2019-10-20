package net.away0x.lib_user_center.data.protocol

data class LoginReq(
    val mobile: String,
    val pwd: String,
    val pushId: String
)