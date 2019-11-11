package net.away0x.lib_order_center.ui.activity

import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_order.*
import net.away0x.lib_base.ui.activity.BaseActivity
import net.away0x.lib_order_center.R
import net.away0x.lib_order_center.common.OrderConstant
import net.away0x.lib_order_center.common.OrderStatus
import net.away0x.lib_order_center.ui.adapter.OrderVpAdapter

/*
    订单Activity
    主要包括不同订单状态的Fragment */
class OrderActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        initView()
    }

    /* 初始化视图 */
    private fun initView() {
        mOrderTab.tabMode = TabLayout.MODE_FIXED
        mOrderVp.adapter = OrderVpAdapter(supportFragmentManager,this)
        mOrderTab.setupWithViewPager(mOrderVp)

        //根据订单状态设置当前页面
        mOrderVp.currentItem = intent.getIntExtra(OrderConstant.KEY_ORDER_STATUS, OrderStatus.ORDER_ALL)
    }
}