package com.away0x.com.workmanager

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf

class MyWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    override fun doWork(): Result {
        val name = inputData.getString(INPUT_DATA_KEY)
        Log.d("Tag", "doWork: started$name")
        Thread.sleep(3000)
        val sp = applicationContext.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)

        var number = sp.getInt(name, 0)
        sp.edit().putInt(name, ++number).apply()



        Log.d("Tag", "doWork:  finished$name")
//        return Result.success();
        //返回数据
        return Result.success(workDataOf(OUTPUT_DATA_KEY to "$name output"));
    }
}