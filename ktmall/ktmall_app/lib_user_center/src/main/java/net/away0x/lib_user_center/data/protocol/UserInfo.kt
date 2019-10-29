package net.away0x.lib_user_center.data.protocol

import net.away0x.lib_base.data.protocol.TokenInfo
import net.away0x.lib_base.data.protocol.UserInfo

data class LoginAndRegisterResp(
    val user: UserInfo,
    val token: TokenInfo
)