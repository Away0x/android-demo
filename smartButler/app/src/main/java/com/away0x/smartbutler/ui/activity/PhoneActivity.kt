package com.away0x.smartbutler.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.Button
import com.away0x.smartbutler.R
import com.away0x.smartbutler.entity.CompanyType
import com.away0x.smartbutler.services.PhoneService
import kotlinx.android.synthetic.main.activity_phone.*

// 归属地查询
class PhoneActivity : BaseActivity(), View.OnClickListener {

    // 标记位
    private var flag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone)

        initView()
    }

    private fun initView() {
        btn_0.setOnClickListener(this)
        btn_1.setOnClickListener(this)
        btn_2.setOnClickListener(this)
        btn_3.setOnClickListener(this)
        btn_4.setOnClickListener(this)
        btn_5.setOnClickListener(this)
        btn_6.setOnClickListener(this)
        btn_7.setOnClickListener(this)
        btn_8.setOnClickListener(this)
        btn_9.setOnClickListener(this)
        btn_del.setOnClickListener(this)
        btn_query.setOnClickListener(this)

        // 长按事件
        btn_del.setOnLongClickListener {
            et_number.setText("")
            return@setOnLongClickListener true
        }
    }

    override fun onClick(v: View?) {
        var text = et_number.text.toString().trim()

        when (v?.id) {
            R.id.btn_0,
            R.id.btn_1,
            R.id.btn_2,
            R.id.btn_3,
            R.id.btn_4,
            R.id.btn_5,
            R.id.btn_6,
            R.id.btn_7,
            R.id.btn_8,
            R.id.btn_9 -> {
                if (flag) {
                    flag = false
                    text = ""
                    et_number.setText("")
                }

                // 每次结尾添加1
                val btnText = (v as Button).text
                et_number.setText(text + btnText)
                // 移动光标
                et_number.setSelection(text.length + 1)
            }

            R.id.btn_del -> {
                if (text.isEmpty()) return

                // 每次结尾减去1
                et_number.setText(text.substring(0, text.length - 1))
                // 动光标
                et_number.setSelection(text.length - 1)
            }

            R.id.btn_query -> {
                if (text.isEmpty()) return
                getPhone(text)
            }
        }
    }

    private fun getPhone(phone: String) {
        PhoneService.getPhone(phone) {
            if (it == null) return@getPhone

            val drawable = when (it.type) {
                CompanyType.Mobile -> R.drawable.china_mobile
                CompanyType.Unicom -> R.drawable.china_unicom
                CompanyType.Telecom -> R.drawable.china_telecom
            }

            iv_company.setBackgroundResource(drawable)
            tv_result.text = it.data

            flag = false
        }
    }

}
