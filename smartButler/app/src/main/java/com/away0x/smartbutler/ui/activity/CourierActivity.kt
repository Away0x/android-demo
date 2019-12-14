package com.away0x.smartbutler.ui.activity

import android.os.Bundle
import android.util.Log
import com.away0x.smartbutler.R
import com.away0x.smartbutler.services.CourierService
import com.away0x.smartbutler.ui.adapter.CourierAdapter
import kotlinx.android.synthetic.main.activity_courier.*
import org.jetbrains.anko.toast

// 快递查询
class CourierActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_courier)

        initView()
    }

    private fun initView() {
        mSearchBtn.setOnClickListener {
            hideKeyBoard(it)

            val name = mNameET.text.toString().trim()
            val num = mNumET.text.toString().trim()

            if (num.isEmpty() || num.isEmpty()) {
                toast(R.string.text_tost_empty)
                return@setOnClickListener
            }

            CourierService.getCourier(name, num) {
                Log.d("CourierActivity", it.toString())

                val adapter = CourierAdapter(this, it)
                mListView.adapter = adapter
            }
        }
    }
}
