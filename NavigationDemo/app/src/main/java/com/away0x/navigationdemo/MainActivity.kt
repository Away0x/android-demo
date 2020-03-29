package com.away0x.navigationdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI

class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = Navigation.findNavController(this, R.id.fragment)
        // 设置导航栏标题 (使用的是 my_nav_graph 各 framge 的 label)
        NavigationUI.setupActionBarWithNavController(this, navController);
    }

    // 导航栏返回按钮 action
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

}
