package com.away0x.com.gallery.paging

import android.content.Context
import androidx.paging.DataSource
import com.away0x.com.gallery.model.PhotoItem

class PixabayDataSourceFactory(private val context: Context) : DataSource.Factory<Int, PhotoItem>() {
    override fun create(): DataSource<Int, PhotoItem> {
        return PixabayDataSource(context)
    }
}