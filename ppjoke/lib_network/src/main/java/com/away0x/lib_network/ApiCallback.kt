package com.away0x.lib_network

abstract class ApiCallback<T> {

    open fun onSuccess(response: ApiResponse<T>) {}

    open fun onError(response: ApiResponse<T>) {}

    open fun onCacheSuccess(response: ApiResponse<T>) {}

}