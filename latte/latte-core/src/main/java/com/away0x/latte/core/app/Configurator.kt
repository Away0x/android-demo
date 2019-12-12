package com.away0x.latte.core.app

import android.content.Context
import com.joanzapata.iconify.IconFontDescriptor
import com.joanzapata.iconify.Iconify

class Configurator private constructor(){

    companion object {
        val instance: Configurator by lazy { Configurator() }

        // 配置存储
        private val LATTE_CONFIGS = mutableMapOf<ConfigKeys, Any>()
        // icons
        private val ICONS = mutableListOf<IconFontDescriptor>()
    }

    init {
        setConfig(ConfigKeys.CONFIG_READY, false)
    }

    private fun setConfig(key: ConfigKeys, value: Any) {
        LATTE_CONFIGS[key] = value
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> getConfig(key: ConfigKeys): T {
        return LATTE_CONFIGS[key] as T
    }

    private fun checkConfiguration() {
        val isReady = getConfig<Boolean>(ConfigKeys.CONFIG_READY)

        if (!isReady) {
            throw RuntimeException("Configuration is not ready, call configure")
        }
    }

    fun configure() {
        // 设置 Icon
        if (ICONS.size > 0) {
            val initializer = Iconify.with(ICONS[0])
            ICONS.forEachIndexed { index, item ->
                if (index > 0) initializer.with(item)
            }
        }

        // 表示配置完成
        setConfig(ConfigKeys.CONFIG_READY, true)
    }

    fun getLatteConfigs(): MutableMap<ConfigKeys, Any> {
        return LATTE_CONFIGS
    }

    fun withApiHost(host: String): Configurator {
        setConfig(ConfigKeys.API_HOST, host)
        return this
    }

    fun withApplicationContext(context: Context): Configurator {
        setConfig(ConfigKeys.APPLICATION_CONTEXT, context)
        return this
    }

    fun withIcon(icon: IconFontDescriptor): Configurator {
        ICONS.add(icon)
        return this
    }

    fun <T> getConfiguration(key: ConfigKeys): T {
        checkConfiguration()
        return getConfig<T>(key) ?: throw NullPointerException("$key IS NULL")
    }

    fun getApplicationContext(): Context {
        return getConfiguration(ConfigKeys.APPLICATION_CONTEXT)
    }

}