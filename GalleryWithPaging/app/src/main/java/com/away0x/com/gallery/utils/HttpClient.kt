package com.away0x.com.gallery.utils

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class HttpClient private constructor(context: Context) {

    companion object {
        private var INSTANCE: HttpClient? = null

        fun getInstance(context: Context) =
            INSTANCE ?: synchronized(this) {
                HttpClient(context).also { INSTANCE = it }
            }
    }

    val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(context.applicationContext)
    }

}