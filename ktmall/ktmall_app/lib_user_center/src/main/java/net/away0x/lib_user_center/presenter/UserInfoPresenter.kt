package net.away0x.lib_user_center.presenter

import net.away0x.lib_base.ext.execute
import net.away0x.lib_base.presenter.BasePresenter
import net.away0x.lib_base.rx.BaseException
import net.away0x.lib_user_center.presenter.view.UserInfoView
import net.away0x.lib_user_center.service.UploadService
import net.away0x.lib_user_center.service.UserService
import net.away0x.lib_base.common.AuthManager
import javax.inject.Inject

/* 编辑用户资料 Presenter */
class UserInfoPresenter @Inject constructor() : BasePresenter<UserInfoView>() {

    @Inject
    lateinit var userService: UserService

    @Inject
    lateinit var uploadService: UploadService

    /* 编辑用户资料 */
    fun editUser(userIcon: String, userName: String, userGender: String, userSign: String) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()

        userService.editUser(userIcon, userName, userGender, userSign)
            .execute(lifecycleProvider, {
                AuthManager.instance.saveUserInfo(it)
                mView.onEditUserResult(it)
                mView.hideLoading()
            }, {
                if (it is BaseException) {
                    mView.onError(it.msg)
                }
                mView.onError("修改用户信息失败")
                mView.hideLoading()
            })
    }

    /* 获取七牛云上传凭证 */
    fun getUploadToken(){
        if (!checkNetWork()) {
            return
        }

        mView.showLoading()
        uploadService.getUploadToken()
            .execute(lifecycleProvider, {
                mView.onGetUploadTokenResult(it)
                mView.hideLoading()
            }, {
                if (it is BaseException) {
                    mView.onError(it.msg)
                }
                mView.onError("获取七牛云上传凭证失败")
                mView.hideLoading()
            })
    }

}