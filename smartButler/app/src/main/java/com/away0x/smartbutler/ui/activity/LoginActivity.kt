package com.away0x.smartbutler.ui.activity

import android.os.Bundle
import android.view.View
import com.away0x.smartbutler.R
import com.away0x.smartbutler.utils.ShareUtils
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class LoginActivity : BaseActivity(), View.OnClickListener {

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

        if (isKeep) {
            mNameET.setText(ShareUtils.getString(this, "name", ""))
            mPwdET.setText(ShareUtils.getString(this, "password", ""))
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.mForgetTV -> {

            }

            R.id.mRegisterBtn -> {
                startActivity<RegisterActivity>()
            }

            R.id.mLoginBtn -> {
                toast("登录")
            }
        }
    }
}
