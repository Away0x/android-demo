package com.away0x.lib_network.utils

import android.annotation.SuppressLint
import androidx.arch.core.executor.ArchTaskExecutor
import com.away0x.lib_common.utils.logd
import com.away0x.lib_network.ApiResponse
import com.away0x.lib_network.ApiService
import com.away0x.lib_network.ApiCallback
import com.away0x.lib_network.cache.CacheManager
import com.away0x.lib_network.converter.JsonConverter
import okhttp3.Call
import okhttp3.Response
import java.io.IOException
import java.io.Serializable
import java.lang.reflect.Field
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

enum class CacheStrategy(val value: Int) {
    // 仅访问本地缓存，即使本地缓存不存在也不会发起网络请求
    CACHE_ONLY(1),
    // 先访问缓存，同时发起网络的请求，成功后缓存到本地
    CACHE_FIRST(2),
    // 仅访问服务器，不进行任何存储
    NET_ONLY(3),
    // 先访问网络，成功后缓存到本地
    NET_CACHE(4)
}

abstract class Request<T, R : Request<T, R>> constructor(val url: String) {

    val headers = mutableMapOf<String, String>()
    val params = mutableMapOf<String, Any>()
    var type: Type? = null
    var cacheKey: String? = null
    var cacheStrategy: CacheStrategy = CacheStrategy.NET_ONLY

    fun addHeader(key: String, value: String): R {
        headers[key] = value
        return this as R
    }

    fun addParam(key: String, value: Any): R {
        try {
            if (value.javaClass == String::class.java) {
                params[key] = value
            } else {
                val field: Field = value.javaClass.getField("TYPE")
                val clz = field.get(null) as Class<*>
                if (clz.isPrimitive) {
                    params[key] = value
                }
            }
        } catch (e: NoSuchFieldError) {
            e.printStackTrace()
        } catch (e: IllegalAccessError) {
            e.printStackTrace()
        }

        return this as R
    }

    fun responseType(type: Type): R {
        this.type = type
        return this as R
    }

    fun cacheKey(key: String): R {
        this.cacheKey = key
        return this as R
    }

    fun cacheStrategy(cacheStrategy: CacheStrategy): R {
        this.cacheStrategy = cacheStrategy
        return this as R
    }

    @SuppressLint("RestrictedApi")
    fun execute(apiCallback: ApiCallback<T>) {
        if (cacheStrategy != CacheStrategy.NET_ONLY) {
            ArchTaskExecutor.getIOThreadExecutor().execute(Runnable {
                val resp: ApiResponse<T> = readCache()
                if (resp.body != null) {
                    apiCallback.onCacheSuccess(resp)
                }
            })
        }

        if (cacheStrategy != CacheStrategy.CACHE_ONLY) {
            getCall().enqueue(object : okhttp3.Callback {
                override fun onFailure(call: Call, e: IOException) {
                    val result = ApiResponse<T>(message = e.message)
                    apiCallback.onError(result)
                }

                override fun onResponse(call: Call, response: Response) {
                    val result = parseResponse(response, apiCallback)
                    if (result.success) {
                        apiCallback.onSuccess(result)
                    } else {
                        apiCallback.onError(result)
                    }
                }
            })
        }
    }

    /**
     * 同步获取数据
     */
    fun execute(): ApiResponse<T> {
        if (cacheStrategy == CacheStrategy.CACHE_ONLY) {
            return readCache()
        }

        val result = getCall().execute()
        return parseResponse(result, null)
    }

    private fun parseResponse(response: Response, apiCallback: ApiCallback<T>?): ApiResponse<T> {
        var message: String? = null
        val status = response.code
        val success: Boolean = response.isSuccessful
        val result = ApiResponse<T>()
        val content = response.body?.string() ?: ""

        if (success) {
            // logd("parseResponse url", url)
            // logd("parseResponse content", content ?: "content empty")
            val converter = ApiService.convert ?: JsonConverter<T>()

            when {
                apiCallback != null -> {
                    // 获取 callback 的实际泛型类型
                    val type = apiCallback.javaClass.genericSuperclass as ParameterizedType
                    val argument = type.actualTypeArguments[0]
                    result.body = converter.convert(content, argument) as T
                }
                type != null -> {
                    result.body = converter.convert(content, type!!) as T
                }
                else -> {
                    message = content
                }
            }
        }

        result.success = success
        result.status = status
        result.message = message

        if (cacheStrategy != CacheStrategy.NET_ONLY && result.success && result.body != null && result.body is Serializable) {
            saveCache(result.body!!)
        }

        return result
    }

    private fun readCache(): ApiResponse<T> {
        val cache = run {
            cacheKey ?: generateCacheKey()!!
        }.let {
            CacheManager.get(it)
        }

        return ApiResponse(
            status = 304,
            message = "缓存获取成功",
            success = true,
            body = cache as T
        )
    }

    private fun saveCache(body: T) {
        run {
            cacheKey ?: generateCacheKey()!!
        }.let {
            CacheManager.save(it, body)
        }
    }

    private fun getCall(): Call {
        val builder = okhttp3.Request.Builder()
        for ((k, v) in headers) {
            builder.addHeader(k, v)
        }

        val request = generateRequest(builder)
        return ApiService.okHttpClient.newCall(request)
    }

    private fun generateCacheKey(): String? = UrlCreator.createUrlFromParams(url, params)

    protected abstract fun generateRequest(builder: okhttp3.Request.Builder): okhttp3.Request

}