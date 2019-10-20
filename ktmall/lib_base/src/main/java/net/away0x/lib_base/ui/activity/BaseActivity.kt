package net.away0x.lib_base.ui.activity

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity
import net.away0x.lib_base.common.AppManager

open class BaseActivity: RxAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppManager.instance.addActivity(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        AppManager.instance.finishActivity(this)
    }

    fun hideInputBoard() {
        // 收起键盘
        val inputMehtodManager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMehtodManager.hideSoftInputFromWindow(this.window.decorView.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }

}