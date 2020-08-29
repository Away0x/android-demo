package com.away0x.com.recyclerview.pages

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.away0x.com.recyclerview.R
import com.away0x.com.recyclerview.adapter.ItemClickAdapter
import com.away0x.com.recyclerview.model.ImageData
import com.away0x.com.recyclerview.utils.JsonUtil
import com.squareup.okhttp.Callback
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import com.squareup.okhttp.Response
import kotlinx.android.synthetic.main.activity_base_recycle.*
import java.io.IOException

/**
 * recyclerView item 添加点击事件
 */
class ClickGridActivity : AppCompatActivity() {

    private val list = mutableListOf<String>()
    private lateinit var adapter: ItemClickAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_recycle)

        initView()
        initData()
    }

    private fun initView() {
        val layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        recycleView.layoutManager = layoutManager

        adapter = ItemClickAdapter(this, list)
        recycleView.adapter = adapter

        adapter.onItemClickListener = object : ItemClickAdapter.OnItemClickListener {
            override fun onClick(position: Int) {
                Toast.makeText(this@ClickGridActivity, "点击=${position}", Toast.LENGTH_LONG).show()
            }
        }

        adapter.onItemLongClickListener = object : ItemClickAdapter.OnItemLongClickListener {
            override fun onClick(position: Int) {
                Toast.makeText(this@ClickGridActivity, "长按=${position}", Toast.LENGTH_LONG).show()
            }
        }
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