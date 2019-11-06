package net.away0x.lib_goods_center.ui.activity

import android.os.Bundle
import net.away0x.lib_base.ui.activity.BaseActivity
import net.away0x.lib_goods_center.R
import net.away0x.lib_goods_center.ui.fragment.CartFragment

/*
    购物车Activity
    只是Fragment一个壳
 */
class CartActivity: BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        val fragment = supportFragmentManager.findFragmentById(R.id.fragment_cart)
        (fragment as CartFragment).setBackVisible(true)

    }
}

