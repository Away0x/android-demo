package com.away0x.com.gallery.viewModel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.away0x.com.gallery.model.PhotoItem
import com.away0x.com.gallery.model.Pixabay
import com.away0x.com.gallery.utils.HttpClient
import com.google.gson.Gson
import kotlin.math.ceil

const val DATA_STATUS_CAN_LOAD_MORE = 0 // 加载更多
const val DATA_STATUS_NO_MORE = 1 // 没有更多数据了
const val DATA_STATUS_NETWORK_ERROR = 2 // 网络错误

class GalleryViewModel(application: Application) : AndroidViewModel(application) {

    // 暴露给外部用的是不可变的 livedata
    // 内部用的是可变的 livedata
    // 列表数据
    private val _photoListLive = MutableLiveData<List<PhotoItem>>()
    val photoListLive: LiveData<List<PhotoItem>> get() = _photoListLive
    // 加载状态
    private val _dataStatusLive = MutableLiveData<Int>()
    val dataStatusLive: LiveData<Int> get() = _dataStatusLive

    private var isNewQuery = true
    private var isLoading = false

    // 分页相关
    private val perPage = 100
    private var currentPage = 1
    private var totalPage = 1

    // 请求 keyword
    private val keyWords = arrayOf("cat", "dog", "car", "beauty", "phone", "computer", "flower", "animal")
    private var currentKey = "cat"

    // 是否需要滚动到顶部
    var needToScrollToTop = true

    init {
        resetQuery()
    }

    /** 发起一次重新请求 */
    fun resetQuery() {
        currentPage = 1
        totalPage = 1
        currentKey = keyWords.random()
        isNewQuery = true
        needToScrollToTop = true
        fetchData()
    }

    fun fetchData() {
        if (isLoading) return
        // 没有数据了
        if (currentPage > totalPage) {
            _dataStatusLive.value = DATA_STATUS_NO_MORE
            return
        }

        isLoading = true
        val req = StringRequest(
            Request.Method.GET,
            getUrl(),
            Response.Listener {
                with(Gson().fromJson(it, Pixabay::class.java)) {
                    totalPage = ceil(totalHits.toDouble() / perPage).toInt()

                    if (isNewQuery) {
                        _photoListLive.value = hits.toList()
                    } else {
                        _photoListLive.value = arrayListOf(_photoListLive.value!!, hits.toList()).flatten() // 追加数据
                    }
                }

                _dataStatusLive.value = DATA_STATUS_CAN_LOAD_MORE
                isLoading = false
                isNewQuery = false
                currentPage++
            },
            Response.ErrorListener {
                Toast.makeText(getApplication(), it.toString(), Toast.LENGTH_SHORT).show()
                Log.e("GalleryViewModel", it.toString())
                _dataStatusLive.value = DATA_STATUS_NETWORK_ERROR
                isLoading = false
            }
        )

        HttpClient.getInstance(getApplication()).requestQueue.add(req)
    }

    private fun getUrl(): String {
        return "https://pixabay.com/api/?key=12472743-874dc01dadd26dc44e0801d61&q=${currentKey}&per_page=${perPage}&page=${currentPage}"
    }

}