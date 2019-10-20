package net.away0x.lib_user_center.data.repository

import io.reactivex.Observable
import net.away0x.lib_base.data.net.RetrofitFactory
import net.away0x.lib_base.data.protocol.BaseResp
import net.away0x.lib_user_center.data.api.UserApi
import net.away0x.lib_user_center.data.protocol.LoginReq
import net.away0x.lib_user_center.data.protocol.RegisterReq
import net.away0x.lib_user_center.data.protocol.UserInfo
import javax.inject.Inject

class UserRepository @Inject constructor() {

    fun register(mobile: String, verifyCode: String, pwd: String): Observable<BaseResp<String>> {
        return RetrofitFactory.instance.create(UserApi::class.java)
            .register(RegisterReq(mobile, pwd, verifyCode))
    }

    fun login(mobile: String, pwd: String, pushId: String): Observable<BaseResp<UserInfo>> {
        return RetrofitFactory.instance.create(UserApi::class.java)
            .login(LoginReq(mobile, pwd, pushId))
    }

}