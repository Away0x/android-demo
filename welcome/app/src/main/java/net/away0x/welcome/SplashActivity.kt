package net.away0x.welcome

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashActivity : AppCompatActivity() {

    private val handler = Handler()
    private var isFirst = false // 判断用户是否是首次登录

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val sp = getPreferences(Context.MODE_PRIVATE)
        isFirst = sp.getBoolean("isFirst", true)

        handler.postDelayed({
            val intent = Intent()
            if (isFirst) {
                sp.edit().putBoolean("isFirst", false).apply()
                // 如果用户第一次安装应用，进入引导页面
                intent.setClass(this, GuideActivity::class.java)
            } else {
                // intent.setClass(this, MainActivity::class.java)
                intent.setClass(this, GuideActivity::class.java)
            }

            startActivity(intent)
            // 设置页面之间的切换动画
            // overridePendingTransition();
            finish()
        }, 3000)
    }
}
