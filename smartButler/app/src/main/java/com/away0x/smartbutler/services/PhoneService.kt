package com.away0x.smartbutler.services

import android.util.Log
import com.away0x.smartbutler.common.AppConstants
import com.away0x.smartbutler.entity.CompanyType
import com.away0x.smartbutler.entity.PhoneData
import com.kymjs.rxvolley.RxVolley
import com.kymjs.rxvolley.client.HttpCallback
import org.json.JSONException
import org.json.JSONObject

object PhoneService {

    fun getPhone(phone: String, cb: (PhoneData?) -> Unit) {
        val url = AppConstants.PHONE_API + "&phone=" + phone


        RxVolley.get(url, object : HttpCallback() {
            override fun onSuccess(t: String?) {
                Log.d("PhoneService", url)
                Log.d("PhoneService", t)
                val data = parsePhoneData(t ?: "")

                cb(data)
            }
        })
    }

    private fun parsePhoneData(str: String): PhoneData? {
        try {
            val obj = JSONObject(str)
            val res = obj.getJSONObject("result")
            val province = res.getString("province")
            val city = res.getString("city")
            val areacode = res.getString("areacode")
            val zip = res.getString("zip")
            val company = res.getString("company")
            val card = res.getString("card")
            val type = when (company) {
                "移动" -> CompanyType.Mobile
                "联通" -> CompanyType.Unicom
                "电信" -> CompanyType.Telecom
                else -> CompanyType.Mobile
            }
            val text = "归属地:$province$city\n区号:$areacode\n邮编:$zip\n运营商:$company\n类型:$card"

            return PhoneData(
                type = type,
                data = text
            )

        } catch (e: JSONException) {
            e.printStackTrace()
            Log.d("PhoneService", e.toString())
            return null
        }
    }

}