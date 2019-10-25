package net.away0x.lib_user_center.presenter

import net.away0x.lib_base.ext.execute
import net.away0x.lib_base.presenter.BasePresenter
import net.away0x.lib_base.rx.BaseException
import net.away0x.lib_user_center.presenter.view.ForgetPwdView
import net.away0x.lib_user_center.service.UserService
import javax.inject.Inject

/* 忘记密码Presenter */
class ForgetPwdPresenter @Inject constructor() : BasePresenter<ForgetPwdView>() {

    @Inject
    lateinit var userService: UserService


    fun forgetPwd(mobile: String, verifyCode: String) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()

        userService.forgetPwd(mobile, verifyCode)
            .execute(lifecycleProvider, {
                mView.onForgetPwdResult("验证成功")
                mView.hideLoading()
            }, {
                if (it is BaseException) {
                    mView.onError(it.msg)
                }
                mView.onForgetPwdResult("验证失败")
                mView.hideLoading()
            })

    }

}
