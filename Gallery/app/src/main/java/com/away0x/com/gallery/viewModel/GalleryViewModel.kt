package com.away0x.com.gallery.viewModel

import android.app.Application
import android.util.Log
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

class GalleryViewModel(application: Application) : AndroidViewModel(application) {

    // 暴露给外部用的是不可变的 livedata
    // 内部用的是可变的 livedata
    private val _photoListLive = MutableLiveData<List<PhotoItem>>()
    val photoListLive: LiveData<List<PhotoItem>>
    get() = _photoListLive

    private val keyWords = arrayOf("cat", "dog", "car", "beauty", "phone", "computer", "flower", "animal")

    private fun getUrl():String {
        return "https://pixabay.com/api/?key=12472743-874dc01dadd26dc44e0801d61&q=${keyWords.random()}&per_page=100"
    }

    fun fetchData() {
        val req = StringRequest(
            Request.Method.GET,
            getUrl(),
            Response.Listener {
                _photoListLive.value = Gson().fromJson(it, Pixabay::class.java).hits.toList()
            },
            Response.ErrorListener { Log.d("GalleryViewModel", it.toString()) }
        )

        HttpClient.getInstance(getApplication())
            .requestQueue.add(req)
    }

}