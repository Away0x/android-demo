package net.away0x.lib_user_center.data.protocol

data class RegisterReq(
    val mobile: String,
    val pwd: String,
    val verifyCode: String
)