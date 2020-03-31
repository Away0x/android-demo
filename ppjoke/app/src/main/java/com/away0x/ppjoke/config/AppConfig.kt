package com.away0x.ppjoke.config

import com.away0x.lib_common.global.AppGlobals
import com.away0x.ppjoke.models.BottomBar
import com.away0x.ppjoke.models.Destination
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

/**
 * app 配置
 */
object AppConfig {

    /**
     * 底部 tab 信息
     */
    val bottomBar: BottomBar? by lazy {
        parseJson<BottomBar>(
            "main_tabs_config.json",
            BottomBar::class.java
        )
    }

    /**
     * nav destination 注解信息
     */
    val destConfig: Map<String, Destination>? by lazy {
        parseJson<Map<String, Destination>>(
            "destination.json",
            object : TypeToken<Map<String, Destination>>() {}.type
        )
    }


    /**
     * 获取文件内容
     */
    private fun parseFile(fileName: String): String {
        val assets = AppGlobals.getApplication().resources.assets

        assets.open(fileName).use {
            return it.bufferedReader().readText()
        }
    }

    /**
     * 获取 json
     */
    private fun <T> parseJson(fileName: String, typed: Type): T {
        return Gson().fromJson<T>(
            parseFile(
                fileName
            ), typed)
    }
}