package com.away0x.com.recyclerview.pages

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.recyclerview.widget.GridLayoutManager
import com.away0x.com.recyclerview.R
import com.away0x.com.recyclerview.adapter.MyAdapter
import com.away0x.com.recyclerview.model.ImageData
import com.away0x.com.recyclerview.utils.JsonUtil
import com.squareup.okhttp.Callback
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import com.squareup.okhttp.Response
import kotlinx.android.synthetic.main.activity_base_recycle.*
import java.io.IOException

/**
 * 基础 recyclerView
 */
class BaseRecyclerViewActivity : AppCompatActivity() {

    private val list = mutableListOf<String>()
    private lateinit var adapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_recycle)

        initView()
        initData()
    }

    private fun initView() {
        // 设置布局管理器
        val gridLayout = GridLayoutManager(this, 3)
        recycleView.layoutManager = gridLayout

        // 设置适配器
        adapter = MyAdapter(this, list)
        recycleView.adapter = adapter
    }

    private fun initData() {
        val client = OkHttpClient()
        val req = Request.Builder()
            .url("http://www.imooc.com/api/teacher?type=2&page=1")
            .build()
        val mcall = client.newCall(req)
        
        mcall.enqueue(object : Callback {
            override fun onFailure(request: Request?, e: IOException?) {}

            override fun onResponse(response: Response?) {
                if (response == null) return
                val res = response.body().string()
                val imageData = JsonUtil.fromJson(res, ImageData::class.java)
                val data = imageData.data

                data.forEachIndexed { _, dataBean ->
                    list.add(dataBean.picSmall)
                }

                Handler(Looper.getMainLooper()).post {
                    adapter.notifyDataSetChanged()
                }
            }
        })
    }
}