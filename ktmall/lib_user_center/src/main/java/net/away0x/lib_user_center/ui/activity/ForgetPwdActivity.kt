package net.away0x.lib_user_center.ui.activity

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_forget_pwd.*
import kotlinx.android.synthetic.main.activity_forget_pwd.mMobileEt
import net.away0x.lib_base.ext.enable
import net.away0x.lib_base.ext.onClick
import net.away0x.lib_base.ui.activity.BaseMvpActivity
import net.away0x.lib_user_center.R
import net.away0x.lib_user_center.injection.component.DaggerUserComponet
import net.away0x.lib_user_center.injection.module.UserModule
import net.away0x.lib_user_center.presenter.ForgetPwdPresenter
import net.away0x.lib_user_center.presenter.view.ForgetPwdView
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/* 忘记密码界面 */
class ForgetPwdActivity : BaseMvpActivity<ForgetPwdPresenter>(), ForgetPwdView, View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_pwd)

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

    override fun onForgetPwdResult(result: String) {
        toast(result)
        startActivity<ResetPwdActivity>("mobile" to mMobileEt.text.toString())
    }

    private fun initView() {
        mNextBtn.enable(mMobileEt) { isSubmitBtnEnable() }
        mNextBtn.enable(mVerifyCodeEt) { isSubmitBtnEnable() }

        mVerifyCodeBtn.onClick(this)
        mNextBtn.onClick(this)
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.mVerifyCodeBtn -> {
                mVerifyCodeBtn.requestSendVerifyNumber()
                toast("发送验证成功")
            }

            R.id.mNextBtn -> {
                mPresenter.forgetPwd(mMobileEt.text.toString(), mVerifyCodeEt.text.toString())
            }
        }
    }

    /**
     * submit 按钮是否可点击
     */
    private fun isSubmitBtnEnable(): Boolean {
        return mMobileEt.text.isNullOrEmpty().not() &&
                mVerifyCodeEt.text.isNullOrEmpty().not()
    }

}
