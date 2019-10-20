package net.away0x.lib_user_center.service

import io.reactivex.Observable
import net.away0x.lib_user_center.data.protocol.UserInfo

interface UserService {

    fun register(mobile: String, pwd: String, verifyCode: String): Observable<Boolean>
    fun login(mobile: String, pwd: String, pushId: String): Observable<UserInfo>

}