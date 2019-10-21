package net.away0x.lib_user_center.presenter.view

import net.away0x.lib_base.presenter.view.BaseView
import net.away0x.lib_user_center.data.protocol.UserInfo

/* 编辑用户资料 视图回调 */
interface UserInfoView : BaseView {

    /* 获取上传凭证回调 */
//    fun onGetUploadTokenResult(result:String)

    /* 编辑用户资料回调 */
    fun onEditUserResult(result: UserInfo)

}