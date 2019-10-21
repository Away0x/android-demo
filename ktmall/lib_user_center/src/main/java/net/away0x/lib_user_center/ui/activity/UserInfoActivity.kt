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
import net.away0x.lib_base.utils.AppPrefsUtils
import net.away0x.lib_base.utils.GlideUtils
import net.away0x.lib_provider.common.ProviderConstant
import net.away0x.lib_user_center.R
import net.away0x.lib_user_center.data.protocol.UserInfo
import net.away0x.lib_user_center.injection.component.DaggerUserComponet
import net.away0x.lib_user_center.injection.module.UserModule
import net.away0x.lib_user_center.presenter.UserInfoPresenter
import net.away0x.lib_user_center.presenter.view.UserInfoView
import net.away0x.lib_user_center.utils.UserPrefsUtils
import org.devio.takephoto.model.TResult
import org.jetbrains.anko.toast
import org.json.JSONObject

/* 用户信息 */
class UserInfoActivity : BaseTakePhotoActivity<UserInfoPresenter>(), UserInfoView {

    private val mUploadManager: UploadManager by lazy { UploadManager() }

    private var mLocalFileUrl: String? = null
    private var mRemoteFileUrl: String? = null

    private var mUserIcon: String? = null
    private var mUserName: String? = null
    private var mUserMobile: String? = null
    private var mUserGender: String? = null
    private var mUserSign: String? = null;

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
        UserPrefsUtils.putUserInfo(result)
    }

    /* 获取上传凭证回调 */
    override fun onGetUploadTokenResult(result: String) {
        mUploadManager.put(mLocalFileUrl,null, result, object: UpCompletionHandler {
            override fun complete(key: String?, info: ResponseInfo?, response: JSONObject?) {
                mRemoteFileUrl = BaseConstant.IMAGE_SERVER_ADDRESS + response?.get("hash")

                Log.d("test", mRemoteFileUrl)
                GlideUtils.loadUrlImage(this@UserInfoActivity, mRemoteFileUrl!!, mUserIconIv)
            }

        },null)
    }

    private fun initData() {
        mUserIcon = AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_ICON)
        mUserName = AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_NAME)
        mUserMobile = AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_MOBILE)
        mUserGender = AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_GENDER)
        mUserSign = AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_SIGN)

        mRemoteFileUrl = mUserIcon
        if (mUserIcon != ""){
            GlideUtils.loadUrlImage(this, mUserIcon!!, mUserIconIv)
        }
        mUserNameEt.setText(mUserName)
        mUserMobileTv.text = mUserMobile

        if (mUserGender == "0") {
            mGenderMaleRb.isChecked = true
        } else {
            mGenderFemaleRb.isChecked = true
        }

        mUserSignEt.setText(mUserSign)
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
