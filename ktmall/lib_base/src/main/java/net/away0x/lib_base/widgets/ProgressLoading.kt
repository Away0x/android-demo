package net.away0x.lib_base.widgets

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.view.Gravity
import android.widget.ImageView
import net.away0x.lib_base.R
import org.jetbrains.anko.find

class ProgressLoading private constructor(context: Context, theme: Int): Dialog(context, theme) {

    companion object {
        private lateinit var mDialog: ProgressLoading
        private var animDrawble: AnimationDrawable? = null

        fun create(context: Context): ProgressLoading {
            mDialog = ProgressLoading(context, R.style.LightProgressDialog)
            mDialog.setContentView(R.layout.progress_dialog)
            mDialog.setCancelable(true)
            mDialog.setCanceledOnTouchOutside(false) // 点击 dailog 外侧不关闭 dialog
            mDialog.window.attributes.gravity = Gravity.CENTER

            val lp = mDialog.window.attributes
            lp.dimAmount = 0.2f // 灰暗程度
            mDialog.window.attributes = lp

            val loadingView = mDialog.find<ImageView>(R.id.iv_loading)
            animDrawble = loadingView.background as AnimationDrawable

            return mDialog
        }
    }

    fun showLoading() {
        super.show()
        animDrawble?.start()
    }

    fun hideLoading() {
        super.dismiss()
        animDrawble?.stop()
    }

}