package net.away0x.lib_user_center.presenter.view

import net.away0x.lib_base.presenter.view.BaseView
import net.away0x.lib_user_center.data.protocol.UserInfo

interface LoginView: BaseView {

    fun onLoginResult(result: UserInfo)

}