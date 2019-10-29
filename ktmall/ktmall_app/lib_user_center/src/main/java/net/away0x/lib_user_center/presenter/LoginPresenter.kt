package net.away0x.lib_user_center.presenter

import net.away0x.lib_base.ext.execute
import net.away0x.lib_base.presenter.BasePresenter
import net.away0x.lib_base.rx.BaseException
import net.away0x.lib_user_center.presenter.view.LoginView
import net.away0x.lib_user_center.service.UserService
import net.away0x.lib_base.common.AuthManager
import javax.inject.Inject

class LoginPresenter @Inject constructor(): BasePresenter<LoginView>() {

    @Inject
    lateinit var userService: UserService

    /** 注册 */
    fun login(mobile: String, pwd: String, pushId: String) {
        if (!checkNetWork()) {
            return
        }

        mView.showLoading()

        userService.login(mobile, pwd, pushId)
            .execute(lifecycleProvider, {
                if (it == null) {
                    mView.hideLoading()
                    return@execute
                }
                AuthManager.instance.saveUserInfo(it.user)
                AuthManager.instance.saveToken(it.token.token)

                mView.onLoginResult(true)
                mView.hideLoading()
            }, {
                if (it is BaseException) {
                    mView.onError(it.msg)
                }
                mView.onError("登录失败")
                mView.hideLoading()
            })
    }

}