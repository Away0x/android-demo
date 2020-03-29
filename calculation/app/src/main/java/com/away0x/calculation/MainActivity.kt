package com.away0x.calculation

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI

class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 设置导航 title
        navController = Navigation.findNavController(this, R.id.fragment)
        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    // 处理导航 back 按钮事件
    override fun onSupportNavigateUp(): Boolean {
        val navID = navController.currentDestination?.id

        when (navID) {
            // alert
            R.id.questionFragment -> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle(R.string.quit_dialog_title)
                builder.setPositiveButton(R.string.dialog_positive_message) { _: DialogInterface, _: Int ->
                    val action = QuestionFragmentDirections.actionQuestionFragmentToTitleFragment()
                    navController.navigate(action)
                }
                builder.setNegativeButton(R.string.dialog_negative_message) { _: DialogInterface, _: Int ->}
                builder.create().show()
            }
            R.id.titleFragment -> {
                finish()
            }
            else -> {
                navController.navigate(R.id.titleFragment)
            }
        }

        return super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        onSupportNavigateUp()
    }

}
