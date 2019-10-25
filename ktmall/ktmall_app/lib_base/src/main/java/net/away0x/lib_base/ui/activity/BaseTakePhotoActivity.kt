package net.away0x.lib_base.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import com.bigkoo.alertview.AlertView
import com.bigkoo.alertview.OnItemClickListener
import net.away0x.lib_base.common.BaseApplication
import net.away0x.lib_base.injection.component.ActivityComponent
import net.away0x.lib_base.injection.component.DaggerActivityComponent
import net.away0x.lib_base.injection.module.ActivityModule
import net.away0x.lib_base.injection.module.LifecycleProviderModule
import net.away0x.lib_base.presenter.BasePresenter
import net.away0x.lib_base.presenter.view.BaseView
import net.away0x.lib_base.utils.DateUtils
import net.away0x.lib_base.widgets.ProgressLoading
import org.devio.takephoto.app.TakePhoto
import org.devio.takephoto.app.TakePhotoImpl
import org.devio.takephoto.compress.CompressConfig
import org.devio.takephoto.model.TResult
import org.jetbrains.anko.toast
import java.io.File
import javax.inject.Inject

abstract class BaseTakePhotoActivity<T : BasePresenter<*>> :
    BaseActivity(),
    BaseView,
    TakePhoto.TakeResultListener {

    private lateinit var mTakePhoto: TakePhoto
    private lateinit var mTempFile: File

    lateinit var mActivityComponent: ActivityComponent
    private lateinit var mLoadingDialog: ProgressLoading

    @Inject
    lateinit var mPresenter: T

    /* Dagger注册 */
    protected abstract fun injectComponent()

    /* 初始化 Activity Component */
    private fun initActivityInjection() {
        mActivityComponent = DaggerActivityComponent.builder().appComponent((application as BaseApplication).appComponent)
            .activityModule(ActivityModule(this))
            .lifecycleProviderModule(LifecycleProviderModule(this))
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initActivityInjection()
        injectComponent()

        mTakePhoto = TakePhotoImpl(this,this)
        mTakePhoto.onCreate(savedInstanceState)

        mLoadingDialog = ProgressLoading.create(this)
    }

    /* TakePhoto默认实现 */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mTakePhoto.onActivityResult(requestCode, resultCode, data)
    }

    /* 弹出选择框，默认实现，可根据实际情况，自行修改 */
    protected fun showAlertView() {
        AlertView("选择图片", null, "取消", null, arrayOf("拍照", "相册"), this,
            AlertView.Style.ActionSheet, OnItemClickListener { _, position ->
                mTakePhoto.onEnableCompress(CompressConfig.ofDefaultConfig(),false)

                when (position) {
                    0 -> {
                        createTempFile()
                        mTakePhoto.onPickFromCapture(Uri.fromFile(mTempFile))
                    }

                    1 -> mTakePhoto.onPickFromGallery()
                }
            }
        ).show()
    }

    /* 获取图片，成功回调 */
    override fun takeSuccess(result: TResult?) {
        Log.d("TakePhoto", result?.image?.originalPath)
        Log.d("TakePhoto", result?.image?.compressPath)
    }

    /* 获取图片，取消回调 */
    override fun takeCancel() {}

    /* 获取图片，失败回调 */
    override fun takeFail(result: TResult?, msg: String?) {
        Log.e("takePhoto", msg)
    }

    /* 显示加载框，默认实现 */
    override fun showLoading() {
        mLoadingDialog.showLoading()
    }

    /* 隐藏加载框，默认实现 */
    override fun hideLoading() {
        mLoadingDialog.hideLoading()
    }

    /* 错误信息提示，默认实现 */
    override fun onError(text:String) {
        toast(text)
    }

    /* 新建临时文件 */
    fun createTempFile(){
        val tempFileName = "${DateUtils.curTime}.png"
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            this.mTempFile = File(Environment.getExternalStorageDirectory(), tempFileName)
            return
        }

        this.mTempFile = File(filesDir, tempFileName)
    }

}