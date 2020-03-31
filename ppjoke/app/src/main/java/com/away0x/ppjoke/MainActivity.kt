package com.away0x.ppjoke

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.away0x.ppjoke.tools.navigation.NavHelpers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initNav()
        initBottomBar()
    }

    /**
     * 初始化导航 navigation
     */
    private fun initNav() {
        // 添加导航
        navController = nav_host_fragment.findNavController()
        // 代码创建 navigation graph (根据配置生成)
        // 切换页面时 fragment 是 replace 操作
        // navController.graph = NavHelpers.createDefaultNavGraph(navController)
        // 切换页面时 fragment 是 show hide 操作
        navController.graph = NavHelpers.createFixFragmentNavGraph(navController, this, nav_host_fragment.id)
    }

    /**
     * 初始化 navigation bottom bar
     */
    private fun initBottomBar() {
        // bottom bar item 点击时切换 nav 页面
        bottom_bar_view.setOnNavigationItemSelectedListener {
            navController.navigate(it.itemId)
            // 返回 false 代表按钮没有选中，不会着色。如果为 true，就会着色，且有一个上下浮动的效果
            it.title.isNotEmpty()
        }
    }

}
