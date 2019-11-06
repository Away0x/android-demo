package net.away0x.lib_goods_center.ui.activity

import android.os.Bundle
import android.view.Gravity
import com.alibaba.android.arouter.launcher.ARouter
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_goods_detail.*
import net.away0x.lib_base.common.AuthManager
import net.away0x.lib_base.ext.onClick
import net.away0x.lib_base.ui.activity.BaseActivity
import net.away0x.lib_base.utils.AppPrefsUtils
import net.away0x.lib_goods_center.R
import net.away0x.lib_goods_center.common.GoodsConstant
import net.away0x.lib_goods_center.event.AddCartEvent
import net.away0x.lib_goods_center.event.UpdateCartSizeEvent
import net.away0x.lib_goods_center.ui.adapter.GoodsDetailVpAdapter
import net.away0x.lib_provider.router.RouterPath
import org.jetbrains.anko.startActivity
import q.rorbin.badgeview.QBadgeView

/* 商品详情 Activity */
class GoodsDetailActivity : BaseActivity() {

    private lateinit var mCartBdage: QBadgeView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goods_detail)

        initView()
        initObserve()
        loadCartSize()
    }

    /* 初始化视图 */
    private fun initView() {
        mGoodsDetailTab.tabMode = TabLayout.MODE_FIXED
        mGoodsDetailVp.adapter = GoodsDetailVpAdapter(supportFragmentManager,this)
        //TabLayout关联ViewPager
        mGoodsDetailTab.setupWithViewPager(mGoodsDetailVp)

        mAddCartBtn.onClick {
            AuthManager.instance.afterLogin {
                Bus.send(AddCartEvent())
            }
        }

        mEnterCartTv.onClick {
            startActivity<CartActivity>()
        }

        mLeftIv.onClick {
            finish()
        }

        mCartBdage = QBadgeView(this)
    }

    /* 加载购物车数量 */
    private fun loadCartSize() {
        setCartBadge()
    }

    /* 监听购物车数量变化 */
    private fun initObserve() {
        Bus.observe<UpdateCartSizeEvent>()
            .subscribe {
                setCartBadge()
            }.registerInBus(this)
    }

    /* 设置购物车标签 */
    private fun setCartBadge() {
        mCartBdage.badgeGravity = Gravity.END or Gravity.TOP
        mCartBdage.setGravityOffset(22f,-2f,true)
        mCartBdage.setBadgeTextSize(6f,true)
        mCartBdage.bindTarget(mEnterCartTv).badgeNumber = AppPrefsUtils.getInt(GoodsConstant.SP_CART_SIZE)
    }

    /*  Bus取消监听 */
    override fun onDestroy() {
        super.onDestroy()
         Bus.unregister(this)
    }

}
