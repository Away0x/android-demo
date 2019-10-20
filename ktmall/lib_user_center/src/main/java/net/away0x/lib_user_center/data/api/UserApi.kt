package net.away0x.lib_user_center.data.api

import io.reactivex.Observable
import net.away0x.lib_base.data.protocol.BaseResp
import net.away0x.lib_user_center.data.protocol.LoginReq
import net.away0x.lib_user_center.data.protocol.RegisterReq
import net.away0x.lib_user_center.data.protocol.UserInfo
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {

    @POST("userCenter/register")
    fun register(@Body req: RegisterReq): Observable<BaseResp<String>>

    @POST("userCenter/login")
    fun login(@Body req: LoginReq): Observable<BaseResp<UserInfo>>

}