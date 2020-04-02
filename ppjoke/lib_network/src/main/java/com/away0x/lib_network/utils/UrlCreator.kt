package com.away0x.lib_network.utils

import java.net.URLEncoder

/**
 * url 处理
 */
object UrlCreator {

    fun createUrlFromParams(url: String, params: Map<String, Any?>): String {
        if (params.isEmpty()) return url

        val builder = StringBuilder()
        builder.append(url)

        if (url.indexOf('?') > 0 || url.indexOf('&') > 0) {
            builder.append('&')
        } else {
            builder.append('?')
        }

        for (map in params) {
            builder
                .append(map.key)
                .append("=")
                .append(URLEncoder.encode(map.value.toString(), "UTF-8"))
                .append('&')
        }

        builder.deleteCharAt(builder.length - 1)
        return builder.toString()
    }

}