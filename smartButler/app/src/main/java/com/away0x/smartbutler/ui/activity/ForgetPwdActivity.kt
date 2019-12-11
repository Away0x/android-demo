package com.away0x.smartbutler.ui.activity

import android.os.Bundle
import android.view.View
import cn.bmob.v3.BmobUser
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.UpdateListener
import com.away0x.smartbutler.R
import kotlinx.android.synthetic.main.activity_forget_pwd.*
import org.jetbrains.anko.toast

class ForgetPwdActivity : BaseActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_pwd)

        initView()
    }

    private fun initView() {
        mEditBtn.setOnClickListener(this)
        mForgetBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.mEditBtn -> {
                val oldPwd = mOldPwdET.text.toString().trim()
                val newPwd = mNewPwdET.text.toString().trim()
                val confirmPwd = mConfirmPwdET.text.toString().trim()

                if (oldPwd.isEmpty() || newPwd.isEmpty()) {
                    toast(R.string.text_tost_empty)
                    return
                }

                if (newPwd != confirmPwd) {
                    toast(R.string.text_two_input_not_consistent)
                    return
                }

                BmobUser.updateCurrentUserPassword(oldPwd, newPwd, object : UpdateListener() {
                    override fun done(e: BmobException?) {
                        if (e == null) {
                            toast(R.string.reset_successfully)
                            finish()
                        } else {
                            toast(R.string.reset_failed)
                        }
                    }
                })
            }

            R.id.mForgetBtn -> {
                val email = mEmailET.text.toString().trim()

                if (email.isEmpty()) {
                    toast(R.string.text_tost_empty)
                    return
                }

                BmobUser.resetPasswordByEmail(email, object : UpdateListener() {
                    override fun done(e: BmobException?) {
                        if (e == null) {
                            toast(getString(R.string.text_email_send_ok) + email)
                            finish()
                        } else {
                            toast(R.string.text_email_send_no)
                        }
                    }
                })
            }
        }
    }

}
