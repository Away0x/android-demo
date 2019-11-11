package net.away0x.lib_base.common

import com.alibaba.android.arouter.launcher.ARouter
import net.away0x.lib_base.data.protocol.UserInfo
import net.away0x.lib_base.utils.AppPrefsUtils

class AuthManager {

    // token
    private val TOKEN_KEY = "token"
    // 用户 id
    private val USER_ID = "sp_user_id"
    // 用户名称
    private val USER_NAME = "sp_user_name"
    // 用户电话
    private val USER_MOBILE = "sp_user_mobile"
    // 用户头像
    private val USER_ICON = "sp_user_icon"
    // 用户性别
    private val USER_GENDER = "sp_user_gender"
    // 用户签名
    private val USER_SIGN = "sp_user_sign"


    companion object {
        val instance: AuthManager by lazy { AuthManager() }
    }

    /** 判断是否登录 */
    fun isLogined(): Boolean {
        return getToken() != null
    }

    /** 如已登录执行操作，否则跳转到登录界面 */
    fun afterLogin(cb: () -> Unit) {
        if (isLogined()) {
            cb()
        } else {
            ARouter.getInstance().build(RouterPath.UserCenter.PATH_LOGIN).navigation()
        }
    }

    /** 退出 */
    fun logout() {
        saveUserInfo(null)
        AppPrefsUtils.remove(TOKEN_KEY)
    }

    /** 存储用户数据 */
    fun saveUserInfo(user: UserInfo?) {
        AppPrefsUtils.putString(USER_ID, user?.id ?: "")
        AppPrefsUtils.putString(USER_ICON, user?.icon ?: "")
        AppPrefsUtils.putString(USER_NAME, user?.name ?: "")
        AppPrefsUtils.putString(USER_MOBILE, user?.mobile ?: "")
        AppPrefsUtils.putString(USER_GENDER, user?.gender ?: "")
        AppPrefsUtils.putString(USER_SIGN, user?.sign ?: "")
    }

    /** 获取用户数据 */
    fun getUserInfo(): UserInfo? {
        if (!isLogined()) { return null }

        return UserInfo(
            id = AppPrefsUtils.getString(USER_ID),
            icon = AppPrefsUtils.getString(USER_ICON),
            name = AppPrefsUtils.getString(USER_NAME),
            mobile = AppPrefsUtils.getString(USER_MOBILE),
            gender = AppPrefsUtils.getString(USER_GENDER),
            sign = AppPrefsUtils.getString(USER_SIGN)
        )
    }

    /** 存储 token */
    fun saveToken(token: String) {
        AppPrefsUtils.putString(TOKEN_KEY, token)
    }

    /** 获取 token */
    fun getToken(): String? {
        val token = AppPrefsUtils.getString(TOKEN_KEY)
        return if (token.isNotEmpty()) token else null
    }

    /** 跳转到登录界面 */
    fun gotoLogin() {
        ARouter.getInstance().build(RouterPath.UserCenter.PATH_LOGIN).navigation()
    }
}