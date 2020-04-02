package com.away0x.lib_network

/**
 * 请求响应包装
 */
data class ApiResponse<T>(
    var success: Boolean = false,
    var status: Int = 0,
    var message: String? = null,
    var body: T? = null
)