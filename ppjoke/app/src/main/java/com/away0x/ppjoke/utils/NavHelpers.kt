package com.away0x.ppjoke.utils

import android.content.ComponentName
import androidx.fragment.app.FragmentActivity
import androidx.navigation.ActivityNavigator
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavGraphNavigator
import androidx.navigation.fragment.FragmentNavigator
import com.away0x.lib_common.global.AppGlobals
import com.away0x.lib_common.utils.logd
import com.away0x.ppjoke.config.AppConfig

/**
 * navigation 帮助类
 */
object NavHelpers {

    /**
     * 生成 navigation graph
     * navigation 默认的跳转页面时 replace fragment，使用自定义的 graph 保证不会每次都重建 fragment
     */
    fun buildFixFragmentNavGraph(activity: FragmentActivity, navController: NavController, containerId: Int) {
        val provider = navController.navigatorProvider
        // 节点集合
        val navGraph = NavGraph(NavGraphNavigator(provider))
        // 获取已经注册了的 Navigator
        val fragmentNavigator = provider.getNavigator(FragmentNavigator::class.java)
        provider.addNavigator(fragmentNavigator)

        val activityNavigator = provider.getNavigator(ActivityNavigator::class.java)

        // 读取配置生成 graph
        val destConfig = AppConfig.destConfig
        if (destConfig == null || destConfig.isEmpty()) {
            throw Error("navigation destination 配置为空")
        }

        destConfig.forEach {
            if (it.value.isFragment) {
                val destination = fragmentNavigator.createDestination()
                destination.className = it.value.className
                destination.id = it.value.id
                destination.addDeepLink(it.value.pageUrl)
                // 添加节点
                navGraph.addDestination(destination)
            } else {
                val destination = activityNavigator.createDestination()
                destination.id = it.value.id
                destination.addDeepLink(it.value.pageUrl)
                destination.setComponentName(ComponentName(AppGlobals.getApplication().packageName, it.value.className))
                // 添加节点
                navGraph.addDestination(destination)
            }

            if (it.value.asStarter) {
                navGraph.startDestination = it.value.id
            }
        }

        // 将生成好的 graph 设置给 controller
        navController.graph = navGraph
    }

}