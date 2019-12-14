package com.away0x.smartbutler.services

import android.util.Log
import com.away0x.smartbutler.common.AppConstants
import com.away0x.smartbutler.entity.CourierData
import com.kymjs.rxvolley.RxVolley
import com.kymjs.rxvolley.client.HttpCallback
import org.json.JSONException
import org.json.JSONObject

// 快递
object CourierService {

    fun getCourier(name: String, num: String, cb: (MutableList<CourierData>) -> Unit) {
        val url = AppConstants.COURIER_API + "&com=" + name + "&no=" + num


        RxVolley.get(url, object : HttpCallback() {
            override fun onSuccess(t: String?) {
                Log.d("CourierService", url)
                Log.d("CourierService", t)
                val data = parseCourierData(t ?: "")

                cb(data)
            }
        })
    }

    private fun parseCourierData(str: String): MutableList<CourierData> {
        val list = mutableListOf<CourierData>()

        if (str.isEmpty()) {
            return list
        }

        try {
            val obj = JSONObject(str)
            val res = obj.getJSONObject("result")
            val arr = res.getJSONArray("list")

            for (i in 0..arr.length()) {
                val json = arr.get(i) as JSONObject

                val data = CourierData(
                    json.getString("datetime"),
                    json.getString("remark"),
                    json.getString("zone")
                )

                list.add(data)
            }

            list.reverse()
            return list
        } catch (e: JSONException) {
            e.printStackTrace()
            Log.d("CourierService", e.toString())
            return list
        }
    }

}