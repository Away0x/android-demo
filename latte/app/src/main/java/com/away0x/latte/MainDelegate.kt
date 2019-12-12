package com.away0x.latte

import android.os.Bundle
import android.view.View
import com.away0x.latte.core.delegates.LatteDelegate
import kotlinx.android.synthetic.main.delegate_main.*
import org.jetbrains.anko.support.v4.toast

class MainDelegate : LatteDelegate() {

    override fun setLayout(): Any {
        return R.layout.delegate_main
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
        mBtn.setOnClickListener {
            toast("点击了按钮")
        }
    }

}