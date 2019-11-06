package net.away0x.lib_base.common

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import java.util.*

/**
 * activity 管理器
 */
class AppManager private constructor(){

    companion object {
        val instance: AppManager by lazy { AppManager() }
    }

    private val activityStack: Stack<Activity> = Stack()

    fun addActivity(activity: Activity) {
        activityStack.add(activity)
    }

    fun finishActivity(activity: Activity) {
        activity.finish()
        activityStack.remove(activity)
    }

    fun currentActivity(): Activity {
        return activityStack.lastElement()
    }

    fun finishAllActivity() {
        for (activity in activityStack) {
            activity.finish()
        }
        activityStack.clear()
    }

    /** 退出 app */
    @SuppressLint("ServiceCast")
    fun exitApp(context: Context) {
        finishAllActivity()

//        val activityManager = context.getSystemService(Context.ACCESSIBILITY_SERVICE) as ActivityManager
//        activityManager.killBackgroundProcesses(context.packageName)
//        System.exit(0)
    }

}