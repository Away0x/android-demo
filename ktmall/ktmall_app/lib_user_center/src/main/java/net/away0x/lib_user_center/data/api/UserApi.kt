package net.away0x.lib_user_center.data.api

import io.reactivex.Observable
import net.away0x.lib_base.data.protocol.BaseResp
import net.away0x.lib_user_center.data.protocol.*
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {

    @POST("user/register")
    fun register(@Body req: RegisterReq): Observable<BaseResp<String>>

    @POST("user/login")
    fun login(@Body req: LoginReq): Observable<BaseResp<UserInfo>>

    /* 忘记密码 */
    @POST("user/forget_pwd")
    fun forgetPwd(@Body req: ForgetPwdReq): Observable<BaseResp<String>>

    /* 重置密码 */
    @POST("user/reset_pwd")
    fun resetPwd(@Body req: ResetPwdReq): Observable<BaseResp<String>>

    /* 编辑用户资料 */
    @POST("user/edit")
    fun editUser(@Body req: EditUserReq): Observable<BaseResp<UserInfo>>

}