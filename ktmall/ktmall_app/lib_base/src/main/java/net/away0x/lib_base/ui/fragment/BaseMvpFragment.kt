package net.away0x.lib_base.ui.fragment

import android.os.Bundle
import net.away0x.lib_base.common.BaseApplication
import net.away0x.lib_base.injection.component.ActivityComponent
import net.away0x.lib_base.injection.component.DaggerActivityComponent
import net.away0x.lib_base.injection.module.ActivityModule
import net.away0x.lib_base.injection.module.LifecycleProviderModule
import net.away0x.lib_base.presenter.BasePresenter
import net.away0x.lib_base.presenter.view.BaseView
import net.away0x.lib_base.ui.activity.BaseActivity
import org.jetbrains.anko.support.v4.toast
import javax.inject.Inject

abstract class BaseMvpFragment<T: BasePresenter<*>>: BaseFragment(), BaseView {

    @Inject
    lateinit var mPresenter: T

    lateinit var activityComponent: ActivityComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initActivityInjection()
        injectComponent()
    }

    private fun initActivityInjection() {
        val app = activity?.application as BaseApplication

        activityComponent = DaggerActivityComponent
            .builder()
            .appComponent(app.appComponent)
            .activityModule(ActivityModule(activity as BaseActivity))
            .lifecycleProviderModule(LifecycleProviderModule(this))
            .build()
    }

    abstract fun injectComponent()

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun onError(text: String) {
        toast(text)
    }

}