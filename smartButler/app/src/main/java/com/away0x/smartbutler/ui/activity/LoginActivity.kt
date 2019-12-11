package com.away0x.smartbutler.ui.activity

import android.app.Dialog
import android.os.Bundle
import android.view.View
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.SaveListener
import com.away0x.smartbutler.R
import com.away0x.smartbutler.entity.MyUser
import com.away0x.smartbutler.ui.view.CustomDialog
import com.away0x.smartbutler.utils.ShareUtils
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class LoginActivity : BaseActivity(), View.OnClickListener {

    private lateinit var mDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initView()
    }

    private fun initView() {
        // btn
        mForgetTV.setOnClickListener(this)
        mRegisterBtn.setOnClickListener(this)
        mLoginBtn.setOnClickListener(this)

        // checkbox
        val isKeep = ShareUtils.getBoolean(this, "keepPassword", false)
        mKeepPwdCB.isChecked = isKeep

        // dialog
        mDialog = CustomDialog(this, R.layout.dialog_loading, R.style.Theme_dialog)
        mDialog.setCancelable(false)

        if (isKeep) {
            mNameET.setText(ShareUtils.getString(this, "name", ""))
            mPwdET.setText(ShareUtils.getString(this, "password", ""))
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.mForgetTV -> {
                startActivity<ForgetPwdActivity>()
            }

            R.id.mRegisterBtn -> {
                startActivity<RegisterActivity>()
            }

            R.id.mLoginBtn -> {
                val name = mNameET.text.toString().trim()
                val pass = mPwdET.text.toString().trim()

                if (name.isEmpty() || pass.isEmpty()) {
                    toast(R.string.text_tost_empty)
                    return
                }
                mDialog.show()

                val user = MyUser()
                user.username = name
                user.setPassword(pass)
                user.login(object : SaveListener<MyUser>() {
                    override fun done(u: MyUser?, e: BmobException?) {
                        mDialog.dismiss()

                        if (e == null) {
                            toast("登录成功")
                            startActivity<MainActivity>()
                            finish()
                        } else {
                            toast("登录失败: $e")
                        }
                    }
                })
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        ShareUtils.putBoolean(this, "keepPassword", mKeepPwdCB.isChecked)
        if (mKeepPwdCB.isChecked) {
            ShareUtils.putString(this, "name", mNameET.text.toString().trim())
            ShareUtils.putString(this, "password", mPwdET.text.toString().trim())
        } else {
            ShareUtils.remove(this, "name")
            ShareUtils.remove(this, "password")
        }
    }
}
