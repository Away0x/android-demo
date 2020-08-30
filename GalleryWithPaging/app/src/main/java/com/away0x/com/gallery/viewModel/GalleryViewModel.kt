package com.away0x.com.gallery.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.paging.toLiveData
import com.away0x.com.gallery.paging.PixabayDataSourceFactory

class GalleryViewModel(application: Application) : AndroidViewModel(application) {

    // 这里 toLiveData 里面的 pageSize 并没有起到作用，因为我们是在 PixabayDataSource 中指定 pageSize 的
    val pagedListLiveData = PixabayDataSourceFactory(application).toLiveData(1)

    /**
     * 重新发起请求
     */
    fun resetQuery() {
        // 告知数据源已经失效，则会重新发起请求 (重新生成一个 dataSource 对象)，即会使 pagedListLiveData 发生变化
        pagedListLiveData.value?.dataSource?.invalidate()
    }

}