package net.away0x.lib_user_center.presenter.view

import net.away0x.lib_base.presenter.view.BaseView

/** 忘记密码 */
interface ForgetPwdView: BaseView {

    // 忘记密码回调
    fun onForgetPwdResult(result: String)

}