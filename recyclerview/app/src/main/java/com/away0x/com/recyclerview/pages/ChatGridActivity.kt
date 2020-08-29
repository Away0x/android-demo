package com.away0x.com.recyclerview.pages

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.away0x.com.recyclerview.R
import com.away0x.com.recyclerview.adapter.ChatAdapter
import com.away0x.com.recyclerview.model.Chat
import com.away0x.com.recyclerview.model.ItemType
import kotlinx.android.synthetic.main.activity_chat_grid.*

/**
 * 显示多种布局的 recyclerView
 */
class ChatGridActivity : AppCompatActivity(), View.OnClickListener {

    private val list = mutableListOf<Chat>()
    private lateinit var adapter: ChatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_grid)

        initData()
        initView()
    }

    private fun initData() {
        for (i in 0 until 6) {
            list.add(Chat(type = ItemType.SEND, content = "发送方：你好"))
            list.add(Chat(type = ItemType.RECEIVE, content = "接收方：你好"))
        }
    }

    private fun initView() {
        sendBtn.setOnClickListener(this)
        receBtn.setOnClickListener(this)
        delete.setOnClickListener(this)

        val layoutManager = GridLayoutManager(this, 2)
        recycleView.layoutManager = layoutManager
        adapter = ChatAdapter(this, list)
        recycleView.adapter = adapter
        // 设置 RecyclerView item 动画
        recycleView.itemAnimator = DefaultItemAnimator()
    }

    override fun onClick(v: View?) {
        if (v == null) return

        when (v.id) {
            R.id.sendBtn -> {
                list.add(1, Chat(type = ItemType.SEND, content = "插入：发送"))
                adapter.notifyItemInserted(1)
            }
            R.id.receBtn -> {
                list.add(1, Chat(type = ItemType.SEND, content = "插入：接收"))
                adapter.notifyItemInserted(1) // 插入动画
            }
            R.id.delete -> {
                list.removeAt(list.size - 2)
                adapter.notifyItemRemoved(list.size - 2) // 删除动画
            }
        }
    }
}