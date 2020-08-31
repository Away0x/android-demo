package com.away0x.com.gallery.paging

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.away0x.com.gallery.model.PhotoItem
import com.away0x.com.gallery.model.Pixabay
import com.away0x.com.gallery.utils.HttpClient
import com.google.gson.Gson

enum class NetworkStatus {
    INITIAL_LOADING,
    LOADING,
    LOADED,
    FAILED,
    COMPLETED
}

class PixabayDataSource(private val context: Context) : PageKeyedDataSource<Int, PhotoItem>() {

    var retry : (() -> Any)? = null

    private val _networkStatus = MutableLiveData<NetworkStatus>()
    val networkStatus: LiveData<NetworkStatus> get() = _networkStatus

    private val queryKey = arrayOf("cat", "dog", "car", "beauty", "phone", "computer", "flower", "animal").random()

    /**
     * 第一次加载
     */
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, PhotoItem>
    ) {
        retry = null

        // _networkStatus.value = xxx // 线程不安全
        _networkStatus.postValue(NetworkStatus.INITIAL_LOADING) // 线程安全

        val req = StringRequest(
            Request.Method.GET,
            getUrl(),
            Response.Listener {
                val data = Gson().fromJson(it, Pixabay::class.java).hits.toList()
                callback.onResult(data, null, 2) // 下一页 page 为 2
                _networkStatus.postValue(NetworkStatus.LOADED)
            },
            Response.ErrorListener {
                retry = { loadInitial(params, callback) }
                _networkStatus.postValue(NetworkStatus.FAILED)
                Log.d("PixabayDataSource", "loadInitial $it")
            }
        )

        HttpClient.getInstance(context).requestQueue.add(req)
    }

    /**
     * 加载下一页
     */
    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, PhotoItem>) {
        retry = null
        _networkStatus.postValue(NetworkStatus.LOADING)

        val req = StringRequest(
            Request.Method.GET,
            getUrl(params.key),
            Response.Listener {
                val data = Gson().fromJson(it, Pixabay::class.java).hits.toList()
                callback.onResult(data, params.key + 1)
                _networkStatus.postValue(NetworkStatus.LOADED)
            },
            Response.ErrorListener {
                Log.d("PixabayDataSource", "loadAfter $it")

                // 该接口 400 说明没数据了了
                if (it.networkResponse != null && it.networkResponse.statusCode == 400) {
                    _networkStatus.postValue(NetworkStatus.COMPLETED)
                    return@ErrorListener
                }

                if (it.toString() == "com.android.volley.ClientError") { // volley 没有数据了，会报这个错误
                    _networkStatus.postValue(NetworkStatus.COMPLETED)
                } else {
                    retry = { loadAfter(params, callback) }
                    _networkStatus.postValue(NetworkStatus.FAILED)
                }
            }
        )

        HttpClient.getInstance(context).requestQueue.add(req)
    }

    /**
     * 加载前一页
     */
    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, PhotoItem>) {}


    private fun getUrl(page: Int = 1): String {
        return "https://pixabay.com/api/?key=12472743-874dc01dadd26dc44e0801d61&q=${queryKey}&per_page=50&page=${page}"
    }
}