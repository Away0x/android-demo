package com.away0x.activitymanager

import android.app.Activity
import android.app.Application
import android.os.Bundle
import java.lang.ref.WeakReference

class ActivityManager private constructor() {

    private val activityRefs = ArrayList<WeakReference<Activity>>()
    private val frontbackCallback = ArrayList<FrontBackCallback>()
    private var activityStartCount = 0 // 当前有多少个 activity 在前台
    private var front = true // 当前是否在前台

    // 栈顶 activity
    val topActivity: Activity?
        get() {
            return if (activityRefs.size <= 0) null
            else activityRefs[activityRefs.size - 1].get()
        }

    fun init(application: Application) {
        application.registerActivityLifecycleCallbacks(InnerActivityLifecycleCallbacks())
    }

    fun addFrontBackCallback(callback: FrontBackCallback) {
        frontbackCallback.add(callback)
    }

    fun removeFrontBackCallback(callback: FrontBackCallback) {
        frontbackCallback.remove(callback)
    }

    fun onFrontBackChanged(front: Boolean) {
        for (callback in frontbackCallback) {
            callback.onChanged(front)
        }
    }

    interface FrontBackCallback {
        fun onChanged(front: Boolean)
    }

    inner class InnerActivityLifecycleCallbacks : Application.ActivityLifecycleCallbacks {
        override fun onActivityCreated(p0: Activity, p1: Bundle?) {
            activityRefs.add(WeakReference(p0))
        }

        override fun onActivityStarted(p0: Activity) {
            activityStartCount++
            // activityStartCount > 0: 说明应用处于可见状态，也就是前台
            // !front: 之前是不是在后台
            if (!front && activityStartCount > 0) {
                front = true
                onFrontBackChanged(front)
            }
        }

        override fun onActivityResumed(p0: Activity) {}

        override fun onActivityPaused(p0: Activity) {}

        override fun onActivityStopped(p0: Activity) {
            activityStartCount--
            if (activityStartCount <= 0 && front) {
                front = false
                onFrontBackChanged(front)
            }
        }

        override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {}

        override fun onActivityDestroyed(p0: Activity) {
            for (activityRef in activityRefs) {
                if (activityRef.get() == p0) {
                    activityRefs.remove(activityRef)
                    break
                }
            }
        }
    }

    companion object {
        @JvmStatic
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { ActivityManager() }
    }

}