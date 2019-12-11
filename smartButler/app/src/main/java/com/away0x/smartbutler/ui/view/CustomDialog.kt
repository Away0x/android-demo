package com.away0x.smartbutler.ui.view

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.WindowManager
import com.away0x.smartbutler.R

class CustomDialog(context: Context, layout: Int, style: Int) : Dialog(context, style) {

    init {
        setContentView(layout)
        val layoutParams = window!!.attributes
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        layoutParams.gravity = Gravity.CENTER
        window!!.attributes = layoutParams
        window!!.setWindowAnimations(R.style.pop_anim_style)
    }

}