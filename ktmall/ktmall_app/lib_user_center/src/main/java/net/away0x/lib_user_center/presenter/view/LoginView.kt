package net.away0x.lib_user_center.presenter.view

import net.away0x.lib_base.presenter.view.BaseView

interface LoginView: BaseView {

    fun onLoginResult(result: Boolean)

}