package com.away0x.motionbottomview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import kotlinx.android.synthetic.main.account_icon_layout.*
import kotlinx.android.synthetic.main.contact_icon_layout.*
import kotlinx.android.synthetic.main.explore_icon_layout.*
import kotlinx.android.synthetic.main.message_icon_layout.*

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = findNavController(R.id.fragment)

        initView()
    }

    private fun initView() {
        val destinationMap = mapOf(
            R.id.messageFragment to messageMotionLayout,
            R.id.contactFragment to contactMotionLayout,
            R.id.exploreFragment to exploreMotionLayout,
            R.id.accountFragment to accountMotionLayout
        )

        // title bar 和 nav 联动
        setupActionBarWithNavController(
            navController,
            AppBarConfiguration(destinationMap.keys)
        )

        // bottom bar 点击跳转页面
        destinationMap.forEach { map ->
            map.value.setOnClickListener { navController.navigate(map.key) }
        }

        // 设置切换动画状态
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            controller.popBackStack()
            destinationMap.values.forEach { it.progress = 0.001f }
            destinationMap[destination.id]?.transitionToEnd()
        }
    }
}