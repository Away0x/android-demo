package net.away0x.lib_user_center.presenter

import net.away0x.lib_base.ext.execute
import net.away0x.lib_base.presenter.BasePresenter
import net.away0x.lib_base.rx.BaseException
import net.away0x.lib_user_center.presenter.view.RegisterView
import net.away0x.lib_user_center.service.UserService
import net.away0x.lib_base.common.AuthManager
import javax.inject.Inject

class RegisterPresenter @Inject constructor(): BasePresenter<RegisterView>() {

    @Inject
    lateinit var userService: UserService

    /** 注册 */
    fun register(mobile: String, verifyCode: String, pwd: String) {
        if (!checkNetWork()) {
            return
        }

        mView.showLoading()

        userService.register(mobile, verifyCode, pwd)
            .execute(lifecycleProvider, {
                if (it == null) {
                    mView.hideLoading()
                    return@execute
                }
                AuthManager.instance.saveToken(it.token.token)
                AuthManager.instance.saveUserInfo(it.user)

                mView.onRegisterResult("注册成功")
                mView.hideLoading()
            }, {
                if (it is BaseException) {
                    mView.onError(it.msg)
                    mView.onRegisterResult(it.msg)
                } else {
                    mView.onRegisterResult(it.message ?: "注册失败")
                }

                mView.hideLoading()
            })
    }

}