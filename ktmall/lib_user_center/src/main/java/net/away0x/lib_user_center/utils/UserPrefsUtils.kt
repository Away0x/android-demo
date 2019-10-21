package net.away0x.lib_user_center.utils

import net.away0x.lib_base.common.BaseConstant
import net.away0x.lib_base.utils.AppPrefsUtils
import net.away0x.lib_provider.common.ProviderConstant
import net.away0x.lib_user_center.data.protocol.UserInfo

/* 本地存储用户相关信息 */
object UserPrefsUtils {

    /* 退出登录时，传入null,清空存储 */
    fun putUserInfo(userInfo: UserInfo?) {
        AppPrefsUtils.putString(BaseConstant.KEY_SP_TOKEN, userInfo?.id ?: "")
        AppPrefsUtils.putString(ProviderConstant.KEY_SP_USER_ICON, userInfo?.userIcon ?: "")
        AppPrefsUtils.putString(ProviderConstant.KEY_SP_USER_NAME, userInfo?.userName ?: "")
        AppPrefsUtils.putString(ProviderConstant.KEY_SP_USER_MOBILE, userInfo?.userMobile ?: "")
        AppPrefsUtils.putString(ProviderConstant.KEY_SP_USER_GENDER, userInfo?.userGender ?: "")
        AppPrefsUtils.putString(ProviderConstant.KEY_SP_USER_SIGN, userInfo?.userSign ?: "")
    }
}