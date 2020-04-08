package com.away0x.room

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.away0x.room.adapters.MyAdapter
import com.away0x.room.databinding.ActivityMainBinding
import com.away0x.room.data.room.Word
import com.away0x.room.viewmodels.WordViewModel

val english = listOf(
    "Hello",
    "World",
    "Android",
    "Google",
    "Studio",
    "Project",
    "Database",
    "Recycler",
    "View",
    "String",
    "Value",
    "Integer"
)

val chinese = listOf(
    "你好",
    "世界",
    "安卓系统",
    "谷歌公司",
    "工作室",
    "项目",
    "数据库",
    "回收站",
    "视图",
    "字符串",
    "价值",
    "整数类型"
)

class MainActivity : AppCompatActivity() {

    private val viewModel: WordViewModel by viewModels {
        Injection.provideWordViewModelFactory(this)
    }
    private lateinit var binding: ActivityMainBinding

    private lateinit var myAdapter1: MyAdapter
    private lateinit var myAdapter2: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initView()

        viewModel.getAllWordsLive().observe(this,  Observer<List<Word>> {
            val size = myAdapter1.itemCount
            myAdapter1.allWords = it
            myAdapter2.allWords = it

            if (size != it.size) {
                myAdapter1.notifyDataSetChanged()
                myAdapter2.notifyDataSetChanged()
            }
        })
    }

    private fun initView() {
        myAdapter1 = MyAdapter(false, viewModel)
        myAdapter2 = MyAdapter(true, viewModel)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = myAdapter1

        binding.switch1.setOnCheckedChangeListener { _, isChecked ->
            binding.recyclerView.adapter = if (isChecked) myAdapter2 else myAdapter1
        }

        binding.buttonInsert.setOnClickListener {
            val words = english.mapIndexed { index, _ ->
                Word(
                    word = english[index],
                    chineseMeaning = chinese[index]
                )
            }.toTypedArray()
            viewModel.insertWords(*words)
        }

        binding.buttonClear.setOnClickListener {
            viewModel.deleteAllWords()
        }
    }
}
