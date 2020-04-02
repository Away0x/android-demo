package com.away0x.ppjoke.ui.views

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import com.away0x.ppjoke.R
import com.away0x.ppjoke.config.AppConfig
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode

/**
 * 自定义 tabbar
 */
class MainBottomBar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BottomNavigationView(context, attrs, defStyleAttr) {

    companion object {
        val icons = intArrayOf(
            R.drawable.icon_tab_home,
            R.drawable.icon_tab_sofa,
            R.drawable.icon_tab_publish,
            R.drawable.icon_tab_find,
            R.drawable.icon_tab_mine
        )
    }

    init {
        drawItems()
    }

    @SuppressLint("RestrictedApi")
    private fun drawItems() {
        val bottomBarConfig = AppConfig.bottomBar ?: throw Error("没有找到 main bottom tabs 配置文件")
        val tabs = bottomBarConfig.tabs

        // 定义底部按钮在选中或未选中时状态的颜色
        val states = arrayOf(
            intArrayOf(android.R.attr.state_selected),
            intArrayOf()
        )
        val colors = intArrayOf(
            // 被选中的颜色
            Color.parseColor("#ff0000"),
            // 默认颜色
            Color.parseColor(bottomBarConfig.inActiveColor)
        )
        val colorStateList = ColorStateList(states, colors)

        // 设置颜色
        itemTextColor = colorStateList
        itemIconTintList = colorStateList
        /**
         * LABEL_VISIBILITY_AUTO: 当按钮个数小于3个时一直显示，或者当按钮个数大于3个且小于5个时，被选中的那个按钮文本才会显示
         * int LABEL_VISIBILITY_SELECTED: 只有被选中的那个按钮的文本才会显示
         * LABEL_VISIBILITY_LABELED: 设置按钮的文本为一直显示模式
         * LABEL_VISIBILITY_UNLABELED: 所有的按钮文本都不显示
         */
        labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_LABELED

        // 将按钮添加到 bottomBar 上
        tabs.forEach { tab ->
            if (!tab.enable) return@forEach

            val itemId = getId(tab.pageUrl)
            if (itemId < 0) return@forEach

            val item = menu.add(0, itemId, tab.index, tab.title)
            item?.setIcon(icons[tab.index])
        }

        // 按钮 icon 设置大小
        tabs.forEach { tab ->
            val menuView = getChildAt(0) as BottomNavigationMenuView
            val itemView = menuView.getChildAt(tab.index) as BottomNavigationItemView
            itemView.setIconSize(dp2px(tab.size))

            // 中间按钮设置颜色
            itemView.setShifting(false)
            if (tab.title.isEmpty()) {
                val tintColor = if (tab.tintColor.isNullOrEmpty()) Color.parseColor("#ff678f")
                else Color.parseColor(tab.tintColor)
                itemView.setIconTintList(ColorStateList.valueOf(tintColor))
                // 不会有上下浮动的效果 (该 bottom bar 点击该 item 时需要返回 false 才不会有浮动效果)
                itemView.setShifting(false)
            }
            /**
             * 如果需要禁止掉所有按钮的点击浮动效果
             * 那么还需要给选中和未选中的按钮配置一样大小的字号
             *
             * 在 MainActivity 布局 MainBottomBar 标签中增加如下配置
             * @style/active, @style/inActive 在 style.xml 中
             * app:itemTextAppearanceActive="@style/active
             * app:itemTextAppearanceInactive="@style/inActive"
             */
        }

        // 底部导航栏默认选中
        if (bottomBarConfig.selectTab != 0) {
            val selectedTab = tabs[bottomBarConfig.selectTab]
            if (selectedTab.enable) {
                val itemId = getId(selectedTab.pageUrl)
                // 需要延迟一下再定位到默认选中的 tab
                // 因为需要等待内容区域 NavGraph 解析数据并初始化完成，否则会出现底部按钮切换过去了，但内容区域还没切换过去
                post {
                    // 设置默认选中的按钮
                    selectedItemId = itemId
                }
            }
        }
    }

    private fun dp2px(dpVal: Int): Int {
        val value = context.resources.displayMetrics.density * dpVal + 0.5f
        return value.toInt()
    }

    private fun getId(pageUrl: String): Int {
        val destination = AppConfig.destConfig ?: return -1
        val item = destination[pageUrl] ?: return -1
        return item.id
    }

}