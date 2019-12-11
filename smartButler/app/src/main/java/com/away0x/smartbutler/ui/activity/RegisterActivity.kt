package com.away0x.smartbutler.ui.activity

import android.os.Bundle
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.SaveListener
import com.away0x.smartbutler.R
import com.away0x.smartbutler.entity.MyUser
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.toast

class RegisterActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        initView()
    }

    private fun initView() {
        mRegisterBtn.setOnClickListener {
            // 获取到输入框的值
            val name = mUserNameET.text.toString().trim()
            val age = mAgeET.text.toString().trim()
            val pass = mPwdET.text.toString().trim()
            val repass = mPwdConfirmET.text.toString().trim()
            val email = mEmailET.text.toString().trim()

            var desc = mDescET.text.toString().trim()

            // 验证
            if (name.isEmpty() || age.isEmpty() || pass.isEmpty() || repass.isEmpty() || email.isEmpty()) {
                toast(R.string.text_tost_empty)
                return@setOnClickListener
            }

            if (pass != repass) {
                toast(R.string.text_two_input_not_consistent)
                return@setOnClickListener
            }


            if (desc.isEmpty()) {
                desc = getString(R.string.text_nothing)
            }

            val user = MyUser()
            user.age = age.toIntOrNull() ?: 0
            user.sex = mBoyRB.isChecked
            user.desc = desc
            user.username = name
            user.setPassword(pass)
            user.email = email

            user.signUp(object : SaveListener<MyUser>() {
                override fun done(u: MyUser?, e: BmobException?) {
                    if (e == null) {
                        toast(R.string.text_registered_successful)
                        finish()
                    } else {
                        toast(getString(R.string.text_registered_failure) + e.toString())
                    }
                }
            })
        }
    }
}
