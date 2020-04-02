package com.away0x.ppjoke.tools.navigation

import android.content.ComponentName
import androidx.fragment.app.FragmentActivity
import androidx.navigation.*
import androidx.navigation.fragment.FragmentNavigator
import com.away0x.lib_common.global.AppGlobals
import com.away0x.ppjoke.config.AppConfig
import com.away0x.ppjoke.models.Destination

/**
 * navigation 帮助类
 */
object NavHelpers {

    /**
     * 生成 navigation graph
     */
    fun createDefaultNavGraph(navController: NavController): NavGraph {
        val provider = navController.navigatorProvider
        val fragmentNavigator = provider.getNavigator(FragmentNavigator::class.java)
        return createNavGraph(
            provider,
            fragmentNavigator,
            AppConfig.destConfig
        )
    }

    /**
     * 生成 navigation graph (使用自定义的 navigator)
     * navigation 默认的跳转页面是 fragment replace 操作，使用该方法生成的的 graph 不会每次都重建 fragment，而是 fragment show / hide 操作
     */
    fun createFixFragmentNavGraph(
        navController: NavController,
        activity: FragmentActivity,
        containerId: Int
    ): NavGraph {
        val provider = navController.navigatorProvider
        val fragmentNavigator =
            FixFragmentNavigator(activity, activity.supportFragmentManager, containerId)
        return createNavGraph(
            provider,
            fragmentNavigator,
            AppConfig.destConfig
        )
    }

    private fun createNavGraph(
        provider: NavigatorProvider,
        navigator: FragmentNavigator,
        destinationConfig: Map<String, Destination>?
    ): NavGraph {
        if (destinationConfig == null || destinationConfig.isEmpty()) {
            throw Error("navigation destination 配置为空")
        }

        // 节点集合
        val navGraph = NavGraph(NavGraphNavigator(provider))
        // 添加 navigator
        provider.addNavigator(navigator)
        // 读取配置生成 graph
        destinationConfig.forEach {
            if (it.value.isFragment) {
                val destination = navigator.createDestination()
                destination.className = it.value.className
                destination.id = it.value.id
                destination.addDeepLink(it.value.pageUrl)
                // 添加节点
                navGraph.addDestination(destination)
            } else {
                val activityNavigator = provider.getNavigator(ActivityNavigator::class.java)
                val destination = activityNavigator.createDestination()
                destination.id = it.value.id
                destination.addDeepLink(it.value.pageUrl)
                destination.setComponentName(
                    ComponentName(
                        AppGlobals.getApplication().packageName,
                        it.value.className
                    )
                )
                // 添加节点
                navGraph.addDestination(destination)
            }

            if (it.value.asStarter) {
                navGraph.startDestination = it.value.id
            }
        }

        return navGraph
    }

}