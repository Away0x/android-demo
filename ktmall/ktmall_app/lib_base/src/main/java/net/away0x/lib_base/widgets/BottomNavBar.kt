package net.away0x.lib_base.widgets

import android.content.Context
import android.util.AttributeSet
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.ashokvarma.bottomnavigation.ShapeBadgeItem
import com.ashokvarma.bottomnavigation.TextBadgeItem
import net.away0x.lib_base.R

class BottomNavBar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BottomNavigationBar(context, attrs, defStyleAttr) {

    // 购物车 Tab 标签
    private val mCartBadge: TextBadgeItem
    // 消息 Tab 标签
    private val mMsgBadge: ShapeBadgeItem

    init {
        // 首页
        val homeItem = BottomNavigationItem(R.drawable.btn_nav_home_press,resources.getString(R.string.nav_bar_home))
            .setInactiveIconResource(R.drawable.btn_nav_home_normal)
            .setActiveColorResource(R.color.common_blue)
            .setInActiveColorResource(R.color.text_normal)

        // 分类
        val categoryItem = BottomNavigationItem(R.drawable.btn_nav_category_press,resources.getString(R.string.nav_bar_category))
            .setInactiveIconResource(R.drawable.btn_nav_category_normal)
            .setActiveColorResource(R.color.common_blue)
            .setInActiveColorResource(R.color.text_normal)

        // 购物车
        val cartItem = BottomNavigationItem(R.drawable.btn_nav_cart_press,resources.getString(R.string.nav_bar_cart))
            .setInactiveIconResource(R.drawable.btn_nav_cart_normal)
            .setActiveColorResource(R.color.common_blue)
            .setInActiveColorResource(R.color.text_normal)

        mCartBadge = TextBadgeItem()
        mCartBadge.setText("10")
        cartItem.setBadgeItem(mCartBadge)

        // 消息
        val msgItem = BottomNavigationItem(R.drawable.btn_nav_msg_press,resources.getString(R.string.nav_bar_msg))
            .setInactiveIconResource(R.drawable.btn_nav_msg_normal)
            .setActiveColorResource(R.color.common_blue)
            .setInActiveColorResource(R.color.text_normal)

        mMsgBadge = ShapeBadgeItem()
        mMsgBadge.setShape(ShapeBadgeItem.SHAPE_OVAL)
        // msgItem.setBadgeItem(mMsgBadge)
        msgItem.setBadgeItem(mCartBadge)

        // 我的
        val userItem = BottomNavigationItem(R.drawable.btn_nav_user_press,resources.getString(R.string.nav_bar_user))
            .setInactiveIconResource(R.drawable.btn_nav_user_normal)
            .setActiveColorResource(R.color.common_blue)
            .setInActiveColorResource(R.color.text_normal)

        // 设置底部导航模式及样式
        setMode(MODE_FIXED) // 固定在下方
        setBackgroundStyle(BACKGROUND_STYLE_STATIC)
        setBarBackgroundColor(R.color.common_white)
        // 添加 Tab
        addItem(homeItem)
            .addItem(categoryItem)
            .addItem(cartItem)
            .addItem(msgItem)
            .addItem(userItem)
            .setFirstSelectedPosition(0)
            .initialise()
    }

    /* 检查购物车Tab是否显示标签 */
    fun checkCartBadge(count: Int) {
        if (count == 0){
            mCartBadge.hide()
        } else {
            mCartBadge.show()
            mCartBadge.setText("$count")
        }
    }

    /* 检查消息Tab是否显示标签 */
    fun checkMsgBadge(isVisiable: Boolean) {
        if (isVisiable){
            mCartBadge.show()
        } else {
            mCartBadge.hide()
        }
    }

}