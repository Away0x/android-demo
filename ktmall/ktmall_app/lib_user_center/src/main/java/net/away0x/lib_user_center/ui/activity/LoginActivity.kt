package net.away0x.lib_user_center.ui.activity

import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import kotlinx.android.synthetic.main.activity_login.*
import net.away0x.lib_base.common.PushProvider
import net.away0x.lib_base.ext.enable
import net.away0x.lib_base.ext.onClick
import net.away0x.lib_base.ui.activity.BaseMvpActivity
import net.away0x.lib_base.common.RouterPath
import net.away0x.lib_user_center.R
import net.away0x.lib_user_center.injection.component.DaggerUserComponet
import net.away0x.lib_user_center.injection.module.UserModule
import net.away0x.lib_user_center.presenter.LoginPresenter
import net.away0x.lib_user_center.presenter.view.LoginView
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/** 登录界面 */
@Route(path = RouterPath.UserCenter.PATH_LOGIN)
class LoginActivity : BaseMvpActivity<LoginPresenter>(), LoginView,
    View.OnClickListener {

    @Autowired(name = RouterPath.MessageCenter.PATH_MESSAGE_PUSH)
    @JvmField
    var mPushProvider: PushProvider? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initView()
    }

    override fun onLoginResult(result: Boolean) {
        toast("登录成功")
        finish()
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

    override fun onClick(view: View?) {
        when (view?.id) {
            // 跳转注册界面
            R.id.mRightTv -> {
                startActivity<RegisterActivity>()
            }

            // 登录
            R.id.mLoginBtn -> {
                mPresenter.login(mMobileEt.text.toString(), mPwdEt.text.toString(), mPushProvider?.getPushId()?:"")
                hideInputBoard()
            }

            // 忘记密码
            R.id.mForgetPwdTv -> {
                startActivity<ForgetPwdActivity>()
            }
        }
    }

    private fun initView() {
        mHeaderBar.getRightView().onClick(this)
        mLoginBtn.onClick(this)
        mForgetPwdTv.onClick(this)
        // 判断按钮是否可用
        mLoginBtn.enable(mMobileEt) { isLoginBtnEnable() }
        mLoginBtn.enable(mPwdEt) { isLoginBtnEnable() }
    }

    /**
     * 登录按钮是否可点击
     */
    private fun isLoginBtnEnable(): Boolean {
        return mMobileEt.text.isNullOrEmpty().not() &&
                mPwdEt.text.isNullOrEmpty().not()
    }
}

