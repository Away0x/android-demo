package com.away0x.smartbutler.ui.activity

import android.os.Bundle
import android.os.Handler
import cn.bmob.v3.BmobUser
import com.away0x.smartbutler.R
import com.away0x.smartbutler.common.AppConstants
import org.jetbrains.anko.startActivity

/**
 * 闪屏页
 */
class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        initView()
    }

    private fun initView() {
        Handler().postDelayed({
            if (AppConstants.isFirstLaunch(this)) {
                startActivity<GuideActivity>()
            } else {
                if (BmobUser.isLogin()) {
                    startActivity<MainActivity>()
                } else {
                    startActivity<LoginActivity>()
                }
            }

            finish()
        }, AppConstants.SPLASH_DEPLAY)
    }


}
