package com.away0x.wordslog

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = Navigation.findNavController(findViewById(R.id.nav_host_fragment))
        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        navController.navigateUp()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }
}
