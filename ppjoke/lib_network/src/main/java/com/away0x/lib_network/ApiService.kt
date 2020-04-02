package com.away0x.lib_network

import com.away0x.lib_common.utils.logd
import com.away0x.lib_network.converter.Converter
import com.away0x.lib_network.utils.Request
import com.away0x.lib_network.utils.UrlCreator
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object ApiService {

    val okHttpClient: OkHttpClient
    lateinit var baseUrl: String
    var convert: Converter<*>? = null

    init {
        val interceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message ->
            if (message != null) logd("okhttp", message)
        })
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        okHttpClient = OkHttpClient().newBuilder()
            .readTimeout(5, TimeUnit.SECONDS)
            .writeTimeout(5, TimeUnit.SECONDS)
            .connectTimeout(5, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()

        // https
        val trustManagers = arrayOf<TrustManager>(object : X509TrustManager {
            override fun checkClientTrusted(
                chain: Array<out X509Certificate>?,
                authType: String?
            ) {
            }

            override fun checkServerTrusted(
                chain: Array<out X509Certificate>?,
                authType: String?
            ) {
            }

            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return arrayOf()
            }
        })

        val ssl = SSLContext.getInstance("SSL")
        ssl.init(null, trustManagers, SecureRandom())
        HttpsURLConnection.setDefaultSSLSocketFactory(ssl.socketFactory)
        // 信任所有的域名
        HttpsURLConnection.setDefaultHostnameVerifier { _, _ -> true }
    }

    fun <T> init(baseUrl: String, convert: Converter<T>?) {
        this.baseUrl = baseUrl
        this.convert = convert
    }

    fun <T> getRequest(url: String): GetRequest<T> {
        return GetRequest(baseUrl + url)
    }

    fun <T> postRequest(url: String): PostRequest<T> {
        return PostRequest(baseUrl + url)
    }

}

class GetRequest<T>(private val apiUrl: String) : Request<T, GetRequest<T>>(apiUrl) {

    override fun generateRequest(builder: okhttp3.Request.Builder): okhttp3.Request {
        val url: String = UrlCreator.createUrlFromParams(url, params)
        return builder.get().url(url).build()
    }

}

class PostRequest<T>(private val apiUrl: String) : Request<T, GetRequest<T>>(apiUrl) {

    override fun generateRequest(builder: okhttp3.Request.Builder): okhttp3.Request {
        val bodyBuilder = FormBody.Builder()
        for ((k, v) in params) {
            bodyBuilder.add(k, v.toString())
        }
        return builder.url(url).post(bodyBuilder.build()).build()
    }

}