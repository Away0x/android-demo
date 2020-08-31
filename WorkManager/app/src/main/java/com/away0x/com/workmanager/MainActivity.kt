package com.away0x.com.workmanager

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.work.*
import kotlinx.android.synthetic.main.activity_main.*

const val INPUT_DATA_KEY = "input_data_key"
const val OUTPUT_DATA_KEY = "output_data_key"
const val WORK_A_NAME = "work A"
const val WORK_B_NAME = "work B"
const val SHARED_PREFERENCES = "SP"

class MainActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {

    private val workManager = WorkManager.getInstance(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sp = getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        sp.registerOnSharedPreferenceChangeListener(this)

        button.setOnClickListener {

            val workRequestA = createWork(WORK_A_NAME);
            val workRequestB = createWork(WORK_B_NAME);

            workManager.beginWith(workRequestA)
                    .then(workRequestB)
                    .enqueue()


//            workManager.getWorkInfoByIdLiveData(workRequest.id).observe(this, Observer {
//                // ENQUEUED -> RUNNING -> SUCCEEDED
//                Log.d("Tag", "onCreate: ${it.state}")
//                if(it.state == WorkInfo.State.SUCCEEDED){
//                    Log.d("Tag","onCreate:${it.outputData.getString(OUTPUT_DATA_KEY)}")
//                }
//            })
        }
    }

    private fun createWork(name: String): OneTimeWorkRequest {
        //条件
        val constraints = Constraints.Builder()
                //.setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

        return OneTimeWorkRequestBuilder<MyWorker>()
                //设置条件
                //.setConstraints(constraints)
                //v1 传入数据
                //                .setInputData(workDataOf(Pair(INPUT_DATA_KEY,WORK_A_NAME)))
                //v2 传入数据
                .setInputData(workDataOf(INPUT_DATA_KEY to name))
                .build()
    }


    private fun updateView() {
        val sp = getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        tv_1.text = sp.getInt(WORK_A_NAME, 0).toString()
        tv_2.text = sp.getInt(WORK_B_NAME, 0).toString()
    }

    override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {
        updateView()
    }
}