package com.away0x.hi.library.util

import android.annotation.SuppressLint
import android.app.Application

/**
 * 这种方式获取全局的 Application 是一种拓展思路。
 *
 * 对于组件化项目,不可能把项目实际的 Application 下沉到 Base,而且各个 module 也不需要知道 Application 真实名字
 *
 * 这种一次反射就能获取全局 Application 对象的方式相比于在 Application#OnCreate 保存一份的方式显示更加通用了
 */
object AppGlobals {

    private var application: Application? = null

    @SuppressLint("PrivateApi")
    fun get(): Application? {
        if (application == null) {
            try {
                application = Class.forName("android.app.ActivityThread")
                    .getMethod("currentApplication")
                    .invoke(null) as Application
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
        return application
    }

}