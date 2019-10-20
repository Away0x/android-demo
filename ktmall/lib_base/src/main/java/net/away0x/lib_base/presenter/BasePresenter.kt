package net.away0x.lib_base.presenter

import android.content.Context
import com.trello.rxlifecycle3.LifecycleProvider
import net.away0x.lib_base.presenter.view.BaseView
import net.away0x.lib_base.utils.NetWorkUtils
import javax.inject.Inject

open class BasePresenter<T: BaseView> {

    lateinit var mView: T

    @Inject
    lateinit var lifecycleProvider: LifecycleProvider<*>

    @Inject
    lateinit var context: Context

    fun checkNetWork(): Boolean {
        if (NetWorkUtils.isNetWorkAvailable(context)) {
            return true
        }

        mView.onError("网络不可用")
        return false
    }

}