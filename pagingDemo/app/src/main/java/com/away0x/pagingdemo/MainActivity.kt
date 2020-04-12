package com.away0x.pagingdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.away0x.pagingdemo.data.room.Student
import com.away0x.pagingdemo.data.room.StudentDao
import com.away0x.pagingdemo.data.room.StudentDatabase
import com.away0x.pagingdemo.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var pagedAdapter: MyPagedAdapter
    private lateinit var studentsDao: StudentDao

    private var allStudentsLivePaged: LiveData<PagedList<Student>> = MutableLiveData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initData()
        initView()
    }

    private fun initData() {
        studentsDao = StudentDatabase.getInstance(this).getStudentDao()

        allStudentsLivePaged = LivePagedListBuilder(studentsDao.getAllStudents(), 10) // 每次读取 10 条
            .build()

        allStudentsLivePaged.observe(this, Observer {
            pagedAdapter.submitList(it)

            // log
            it.addWeakCallback(null, object : PagedList.Callback() {
                override fun onChanged(position: Int, count: Int) {
                    Log.d("my", "OnChanged: $count $it")
                }
                override fun onInserted(position: Int, count: Int) {}
                override fun onRemoved(position: Int, count: Int) {}
            })
        })
    }

    private fun initView() {
        pagedAdapter = MyPagedAdapter()

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL))
            adapter = pagedAdapter
        }

        binding.buttonPopulate.setOnClickListener {
            val students = mutableListOf<Student>()
            for (i in 0..1000) {
                students.add(Student(studentNumber = i))
            }

            GlobalScope.launch {
                studentsDao.insertStudents(*students.toTypedArray())
            }
        }

        binding.buttonClear.setOnClickListener {
            GlobalScope.launch {
                studentsDao.deleteAllStudents()
            }
        }
    }
}
