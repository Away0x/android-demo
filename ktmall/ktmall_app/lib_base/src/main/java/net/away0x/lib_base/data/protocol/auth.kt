package net.away0x.lib_base.data.protocol

/** 用户实体类 */
data class UserInfo(
    val id: String,
    val icon: String,
    val name: String,
    val gender: String,
    val mobile: String,
    val sign: String
)

data class TokenInfo(
    val token: String,
    val type: String,
    val expires_in: String
)