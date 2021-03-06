package net.away0x.lib_user_center.service.impl

import io.reactivex.Observable
import net.away0x.lib_base.data.protocol.UserInfo
import net.away0x.lib_base.rx.baseFunc
import net.away0x.lib_base.rx.baseFuncBoolean
import net.away0x.lib_user_center.data.protocol.LoginAndRegisterResp
import net.away0x.lib_user_center.data.repository.UserRepository
import net.away0x.lib_user_center.service.UserService
import javax.inject.Inject

class UserServiceImpl @Inject constructor(): UserService {

    @Inject
    lateinit var repository: UserRepository

    // 注册
    override fun register(mobile: String, pwd: String, verifyCode: String): Observable<LoginAndRegisterResp?> {
        return repository.register(mobile, pwd, verifyCode)
            .flatMap{ baseFunc(it) }
    }

    // 登录
    override fun login(mobile: String, pwd: String, pushId: String): Observable<LoginAndRegisterResp?> {
        return repository.login(mobile, pwd, pushId)
            .flatMap { baseFunc(it) }
    }

    // 忘记密码
    override fun forgetPwd(mobile: String, verifyCode: String): Observable<Boolean> {
        return repository.forgetPwd(mobile, verifyCode)
            .flatMap(::baseFuncBoolean)
    }

    // 重置密码
    override fun resetPwd(mobile: String, pwd: String): Observable<Boolean> {
        return repository.resetPwd(mobile, pwd)
            .flatMap(::baseFuncBoolean)
    }

    // 修改用户资料
    override fun editUser(
        userIcon: String,
        userName: String,
        userGender: String,
        userSign: String
    ): Observable<UserInfo> {
        return repository.editUser(userIcon,userName,userGender,userSign)
            .flatMap { baseFunc(it) }
    }

}