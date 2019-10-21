package net.away0x.lib_user_center.ui.activity

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import kotlinx.android.synthetic.main.activity_register.*
import net.away0x.lib_base.common.AppManager
import net.away0x.lib_base.ext.enable
import net.away0x.lib_base.ext.onClick
import net.away0x.lib_base.ui.activity.BaseMvpActivity
import net.away0x.lib_base.widgets.VerifyButton
import net.away0x.lib_user_center.R
import net.away0x.lib_user_center.injection.component.DaggerUserComponet
import net.away0x.lib_user_center.injection.module.UserModule
import net.away0x.lib_user_center.presenter.RegisterPresenter
import net.away0x.lib_user_center.presenter.view.RegisterView
import org.jetbrains.anko.toast


/** 注册界面 */
class RegisterActivity : BaseMvpActivity<RegisterPresenter>(), RegisterView,
   View.OnClickListener {

    private var pressTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        initView()
    }

    override fun onRegisterResult(result: String) {
        toast(result)
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

    override fun onBackPressed() {
        val time = System.currentTimeMillis()
        if (time - pressTime > 2000) {
            toast("再按一次退出程序")
            pressTime = time
        } else {
            AppManager.instance.finishAllActivity()
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            // 发送验证码
            R.id.mVerifyCodeBtn -> {
                mVerifyCodeBtn.requestSendVerifyNumber()
                toast("发送验证码成功")
            }

            // 注册
            R.id.mRegisterBtn -> {
                mPresenter.register(mMobileEt.text.toString(), mVerifyCodeEt.text.toString(), mPwdEt.text.toString())
                // 收起键盘
                hideInputBoard()
            }
        }
    }

    private fun initView() {
        mRegisterBtn.onClick(this)
        mVerifyCodeBtn.onClick(this)
        // 判断按钮是否可用
        mRegisterBtn.enable(mMobileEt) { isRegisterBtnEnable() }
        mRegisterBtn.enable(mVerifyCodeEt) { isRegisterBtnEnable() }
        mRegisterBtn.enable(mPwdEt) { isRegisterBtnEnable() }
        mRegisterBtn.enable(mPwdConfirmEt) { isRegisterBtnEnable() }
    }

    /**
     * 注册按钮是否可点击
     */
    private fun isRegisterBtnEnable(): Boolean {
        return mMobileEt.text.isNullOrEmpty().not() &&
                mVerifyCodeEt.text.isNullOrEmpty().not() &&
                mPwdEt.text.isNullOrEmpty().not() &&
                mPwdConfirmEt.text.isNullOrEmpty().not()
    }
}
