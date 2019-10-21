package net.away0x.lib_user_center.presenter

import net.away0x.lib_base.ext.execute
import net.away0x.lib_base.presenter.BasePresenter
import net.away0x.lib_base.rx.BaseException
import net.away0x.lib_user_center.presenter.view.ResetPwdView
import net.away0x.lib_user_center.service.UserService
import javax.inject.Inject

/* 重置密码Presenter */
class ResetPwdPresenter @Inject constructor() : BasePresenter<ResetPwdView>() {

    @Inject
    lateinit var userService: UserService


    /* 重置密码 */
    fun resetPwd(mobile: String, pwd: String) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()

        userService.forgetPwd(mobile, pwd)
            .execute(lifecycleProvider, {
                mView.onResetPwdResult("重置密码成功")
                mView.hideLoading()
            }, {
                if (it is BaseException) {
                    mView.onError(it.msg)
                }
                mView.onResetPwdResult("重置密码失败")
                mView.hideLoading()
            })

    }

}