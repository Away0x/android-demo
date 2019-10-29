package net.away0x.ktmall.ui.activity

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_setting.*
import net.away0x.ktmall.R
import net.away0x.lib_base.ext.onClick
import net.away0x.lib_base.ui.activity.BaseActivity
import net.away0x.lib_base.common.AuthManager
import org.jetbrains.anko.toast

class SettingActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        if (!AuthManager.instance.isLogined()) {
            mLogoutBtn.visibility = View.GONE
        }

        mUserProtocolTv.onClick {
            toast("用户协议")
        }
        mFeedBackTv.onClick {
            toast("反馈意见")
        }
        mAboutTv.onClick {
            toast("关于")
        }

        // 退出登录，清空本地用户数据
        mLogoutBtn.onClick {
            AuthManager.instance.logout()
            finish()
        }
    }
}
