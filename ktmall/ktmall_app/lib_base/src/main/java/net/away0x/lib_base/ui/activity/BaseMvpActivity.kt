package net.away0x.lib_base.ui.activity

import android.os.Bundle
import net.away0x.lib_base.common.BaseApplication
import net.away0x.lib_base.injection.component.ActivityComponent
import net.away0x.lib_base.injection.component.DaggerActivityComponent
import net.away0x.lib_base.injection.module.ActivityModule
import net.away0x.lib_base.injection.module.LifecycleProviderModule
import net.away0x.lib_base.presenter.BasePresenter
import net.away0x.lib_base.presenter.view.BaseView
import net.away0x.lib_base.widgets.ProgressLoading
import org.jetbrains.anko.toast
import javax.inject.Inject

abstract class BaseMvpActivity<T: BasePresenter<*>>: BaseActivity(), BaseView {

    @Inject
    lateinit var mPresenter: T
    lateinit var activityComponent: ActivityComponent
    private lateinit var mLoadingDialog: ProgressLoading

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initActivityInjection()
        injectComponent()

        mLoadingDialog = ProgressLoading.create(this)
    }

    private fun initActivityInjection() {
        val app = application as BaseApplication

        activityComponent = DaggerActivityComponent
            .builder()
            .appComponent(app.appComponent)
            .activityModule(ActivityModule(this))
            .lifecycleProviderModule(LifecycleProviderModule(this))
            .build()
    }

    abstract fun injectComponent()

    override fun showLoading() {
        mLoadingDialog.showLoading()
    }

    override fun hideLoading() {
        mLoadingDialog.hideLoading()
    }

    override fun onError(text: String) {
        toast(text)
    }

}