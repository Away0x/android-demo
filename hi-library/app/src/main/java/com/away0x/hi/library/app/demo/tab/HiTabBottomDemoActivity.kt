package com.away0x.hi.library.app.demo.tab

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import com.away0x.hi.library.app.R
import com.away0x.hi.library.util.HiDisplayUtil
import com.away0x.hi.ui.tab.bottom.HiTabBottomInfo
import kotlinx.android.synthetic.main.activity_hi_tab_bottom_demo.*

class HiTabBottomDemoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hi_tab_bottom_demo)

        initTabBottom()
    }

    private fun initTabBottom() {
        hitablayout.setTabAlpha(0.85f)

        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.fire, null)
        val font = "fonts/iconfont.ttf"
        val defaultColor = "#ff656667"
        val tintColor ="#ffd44949"

        val homeTabInfo = HiTabBottomInfo("首页", font, getString(R.string.if_home), null, defaultColor, tintColor)
        val recommendTabInfo = HiTabBottomInfo("收藏", font, getString(R.string.if_favorite), null, defaultColor, tintColor)
        val catTabInfo = HiTabBottomInfo<String>("分类", bitmap, bitmap)
        val chatTabInfo = HiTabBottomInfo("推荐", font, getString(R.string.if_recommend), null, defaultColor, tintColor)
        val profileTabInfo = HiTabBottomInfo("我的", font, getString(R.string.if_profile), null, defaultColor, tintColor)

        val infoList = mutableListOf(homeTabInfo, recommendTabInfo, catTabInfo, chatTabInfo, profileTabInfo)
        // 填充数据生成 tab
        hitablayout.inflateInfo(infoList.toList())
        // 设置默认的 tab
        hitablayout.defaultSelected(homeTabInfo)
        // 改变中间 tab 的高度，使其凸起
        val middleTabItem = hitablayout.findTab(catTabInfo)
        middleTabItem?.apply { resetHeight(HiDisplayUtil.dp2px(66f, resources)) }
        // 监听 tab 切换事件
        hitablayout.addTabSelectedChangeListener { _, _, nextInfo ->
            Toast.makeText(this, nextInfo.name, Toast.LENGTH_SHORT).run {
                setGravity(Gravity.CENTER, 0, 0)
                show()
            }
        }
    }
}