package com.away0x.com.gallery.paging

import android.content.Context
import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.away0x.com.gallery.model.PhotoItem
import com.away0x.com.gallery.model.Pixabay
import com.away0x.com.gallery.utils.HttpClient
import com.google.gson.Gson

class PixabayDataSource(private val context: Context) : PageKeyedDataSource<Int, PhotoItem>() {

    private val queryKey = arrayOf("cat", "dog", "car", "beauty", "phone", "computer", "flower", "animal").random()

    /**
     * 第一次加载
     */
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, PhotoItem>
    ) {
        val req = StringRequest(
            Request.Method.GET,
            getUrl(),
            Response.Listener {
                val data = Gson().fromJson(it, Pixabay::class.java).hits.toList()
                callback.onResult(data, null, 2) // 下一页 page 为 2
            },
            Response.ErrorListener {
                Log.d("PixabayDataSource", "loadInitial $it")
            }
        )

        HttpClient.getInstance(context).requestQueue.add(req)
    }

    /**
     * 加载下一页
     */
    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, PhotoItem>) {
        val req = StringRequest(
            Request.Method.GET,
            getUrl(params.key),
            Response.Listener {
                val data = Gson().fromJson(it, Pixabay::class.java).hits.toList()
                callback.onResult(data, params.key + 1)
            },
            Response.ErrorListener {
                Log.d("PixabayDataSource", "loadAfter $it")
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