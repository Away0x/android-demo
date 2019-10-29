package net.away0x.lib_user_center.data.protocol

/* 修改用户资料请求体 */
data class EditUserReq(
    val icon: String,
    val name: String,
    val gender: String,
    val sign: String)