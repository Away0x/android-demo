package net.away0x.ktmall.ui.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import kotlinx.android.synthetic.main.activity_main.*
import net.away0x.ktmall.R
import net.away0x.ktmall.ui.fragment.HomeFragment
import net.away0x.ktmall.ui.fragment.MeFragment
import net.away0x.lib_base.common.AppManager
import net.away0x.lib_base.ui.activity.BaseActivity
import net.away0x.lib_base.utils.AppPrefsUtils
import net.away0x.lib_goods_center.common.GoodsConstant
import net.away0x.lib_goods_center.event.UpdateCartSizeEvent
import net.away0x.lib_goods_center.ui.fragment.CartFragment
import net.away0x.lib_goods_center.ui.fragment.CategoryFragment
import net.away0x.lib_message_center.ui.fragment.MessageFragment
import net.away0x.lib_provider.event.MessageBadgeEvent
import org.jetbrains.anko.toast
import java.util.*

class MainActivity : BaseActivity() {

    private var pressTime: Long = 0
    // Fragment 栈管理
    private val mStack = Stack<Fragment>()

    // 首页 Fragment
    private val mHomeFragment by lazy { HomeFragment() }
    // 商品分类 Fragment
    private val mCategoryFragment by lazy { CategoryFragment() }
    // 购物车 Fragment
    private val mCartFragment by lazy { CartFragment() }
    // 消息 Fragment
    private val mMsgFragment by lazy { MessageFragment() }
    // 我的 Fragment
    private val mMeFragment by lazy { MeFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initFragment()
        initBottomNav()
        changeFragment(0) // 显示第一个 fragment (home)
        initObserve()
        loadCartSize()
    }

    override fun onDestroy() {
        super.onDestroy()
        Bus.unregister(this)
    }

    /* 初始化Fragment 栈管理 */
    private fun initFragment() {
        val manager = supportFragmentManager.beginTransaction()
        manager.add(R.id.mContaier, mHomeFragment)
        manager.add(R.id.mContaier, mCategoryFragment)
        manager.add(R.id.mContaier, mCartFragment)
        manager.add(R.id.mContaier, mMsgFragment)
        manager.add(R.id.mContaier, mMeFragment)
        manager.commit()

        mStack.add(mHomeFragment)
        mStack.add(mCategoryFragment)
        mStack.add(mCartFragment)
        mStack.add(mMsgFragment)
        mStack.add(mMeFragment)
    }

    /* 初始化底部导航切换事件 */
    private fun initBottomNav() {
        mBottomNavBar.setTabSelectedListener(object : BottomNavigationBar.OnTabSelectedListener{
            override fun onTabReselected(position: Int) {}

            override fun onTabUnselected(position: Int) {}

            override fun onTabSelected(position: Int) {
                changeFragment(position)
            }
        })

        mBottomNavBar.checkMsgBadge(false)
    }

    /* 切换 Tab，切换对应的 Fragment */
    private fun changeFragment(position: Int) {
        val manager = supportFragmentManager.beginTransaction()
        for (fragment in mStack){
            manager.hide(fragment)
        }

        manager.show(mStack[position])
        manager.commit()
    }

    /* 初始化监听，购物车数量变化及消息标签是否显示 */
    private fun initObserve() {
        Bus.observe<UpdateCartSizeEvent>()
            .subscribe {
                loadCartSize()
            }.registerInBus(this)

        Bus.observe<MessageBadgeEvent>()
            .subscribe {
                    t: MessageBadgeEvent ->
                run {
                    mBottomNavBar.checkMsgBadge(t.isVisible)
                }
            }.registerInBus(this)
    }

    /* 加载购物车数量 */
    private fun loadCartSize() {
        mBottomNavBar.checkCartBadge(AppPrefsUtils.getInt(GoodsConstant.SP_CART_SIZE))
    }

    /* 重写Back事件，双击退出 */
    override fun onBackPressed() {
        val time = System.currentTimeMillis()
        if (time - pressTime > 2000){
            toast("再按一次退出程序")
            pressTime = time
        } else{
            AppManager.instance.exitApp(this)
        }
    }
}
