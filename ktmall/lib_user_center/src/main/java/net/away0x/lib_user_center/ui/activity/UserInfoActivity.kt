package net.away0x.lib_user_center.ui.activity

import android.os.Bundle
import net.away0x.lib_base.ui.activity.BaseMvpActivity
import net.away0x.lib_user_center.R
import net.away0x.lib_user_center.data.protocol.UserInfo
import net.away0x.lib_user_center.injection.component.DaggerUserComponet
import net.away0x.lib_user_center.injection.module.UserModule
import net.away0x.lib_user_center.presenter.UserInfoPresenter
import net.away0x.lib_user_center.presenter.view.UserInfoView
import org.jetbrains.anko.toast

/* 用户信息 */
class UserInfoActivity : BaseMvpActivity<UserInfoPresenter>(), UserInfoView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)

        initView()
    }

    override fun injectComponent() {
        DaggerUserComponet
            .builder()
            .activityComponent(activityComponent)
            .userModule(UserModule())
            .build()
            .inject(this) // 注入

        mPresenter.mView = this
    }

    override fun onEditUserResult(result: UserInfo) {
        toast("修改用户信息成功")
    }

    private fun initView() {

    }

}
