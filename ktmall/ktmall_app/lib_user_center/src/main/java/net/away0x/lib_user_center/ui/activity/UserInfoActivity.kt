package net.away0x.lib_user_center.ui.activity

import android.os.Bundle
import android.util.Log
import com.qiniu.android.http.ResponseInfo
import com.qiniu.android.storage.UpCompletionHandler
import com.qiniu.android.storage.UploadManager
import kotlinx.android.synthetic.main.activity_user_info.*
import net.away0x.lib_base.common.BaseConstant
import net.away0x.lib_base.ext.onClick
import net.away0x.lib_base.ui.activity.BaseTakePhotoActivity
import net.away0x.lib_base.utils.GlideUtils
import net.away0x.lib_user_center.R
import net.away0x.lib_user_center.injection.component.DaggerUserComponet
import net.away0x.lib_user_center.injection.module.UserModule
import net.away0x.lib_user_center.presenter.UserInfoPresenter
import net.away0x.lib_user_center.presenter.view.UserInfoView
import net.away0x.lib_base.common.AuthManager
import net.away0x.lib_base.data.protocol.UserInfo
import org.devio.takephoto.model.TResult
import org.jetbrains.anko.toast
import org.json.JSONObject

/* 用户信息 */
class UserInfoActivity : BaseTakePhotoActivity<UserInfoPresenter>(), UserInfoView {

    private val mUploadManager: UploadManager by lazy { UploadManager() }

    private var mLocalFileUrl: String? = null
    private var mRemoteFileUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)

        initData()
        initView()
    }

    override fun injectComponent() {
        DaggerUserComponet
            .builder()
            .activityComponent(mActivityComponent)
            .userModule(UserModule())
            .build()
            .inject(this) // 注入

        mPresenter.mView = this
    }

    override fun onEditUserResult(result: UserInfo) {
        toast("修改用户信息成功")
        finish()
    }

    /* 获取上传凭证回调 */
    override fun onGetUploadTokenResult(token: String) {
        mUploadManager.put(mLocalFileUrl,null, token, object: UpCompletionHandler {
            override fun complete(key: String?, info: ResponseInfo?, response: JSONObject?) {
                val hash = response?.get("hash")
                if (hash == null) {
                    toast("上传失败")
                    return
                }
                mRemoteFileUrl = BaseConstant.IMAGE_SERVER_ADDRESS + hash

                Log.d("test", mRemoteFileUrl)
                GlideUtils.loadUrlImage(this@UserInfoActivity, mRemoteFileUrl!!, mUserIconIv)
            }

        },null)
    }

    private fun initData() {
        val userInfo = AuthManager.instance.getUserInfo()

        val userIcon = userInfo?.icon ?: ""
        val userName = userInfo?.name ?: ""
        val userMobile = userInfo?.mobile ?: ""
        val userGender = userInfo?.gender ?: ""
        val userSign = userInfo?.sign ?: ""

        mRemoteFileUrl = userIcon
        if (userIcon != ""){
            GlideUtils.loadUrlImage(this, userIcon, mUserIconIv)
        }
        mUserNameEt.setText(userName)
        mUserMobileTv.text = userMobile

        if (userGender == "0") {
            mGenderMaleRb.isChecked = true
        } else {
            mGenderFemaleRb.isChecked = true
        }

        mUserSignEt.setText(userSign)
    }

    private fun initView() {
        mUserIconView.onClick {
            showAlertView()
        }

        mHeaderBar.getRightView().onClick {
            val gender = if (mGenderMaleRb.isChecked) "0" else "1"
            val userName = mUserNameEt.text?.toString() ?: ""
            val userSign = mUserSignEt.text?.toString() ?: ""

            mPresenter.editUser(mRemoteFileUrl ?: "", userName, gender, userSign)
        }
    }

    /* 获取图片成功回调 */
    override fun takeSuccess(result: TResult?) {
        mLocalFileUrl = result?.image?.compressPath
        mPresenter.getUploadToken()
    }

}
