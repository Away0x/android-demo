package com.away0x.com.gallery.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Transformations
import androidx.paging.toLiveData
import com.away0x.com.gallery.paging.PixabayDataSourceFactory

class GalleryViewModel(application: Application) : AndroidViewModel(application) {

    private val pagingFactroy = PixabayDataSourceFactory(application)

    // 这里 toLiveData 里面的 pageSize 并没有起到作用，因为我们是在 PixabayDataSource 中指定 pageSize 的
    val pagedListLiveData = pagingFactroy.toLiveData(1)

    // switchMap: 从一个 liveData 生成另一个 LiveData
    val networkStatus = Transformations.switchMap(pagingFactroy.pixabayDataSource) { it.networkStatus }

    // 是否需要滚动到顶部
    var needToScrollToTop = true

    /**
     * 发起请求，更新数据
     */
    fun resetQuery() {
        // 告知数据源已经失效，则会重新发起请求 (重新生成一个 dataSource 对象)，即会使 pagedListLiveData 发生变化
        pagedListLiveData.value?.dataSource?.invalidate()
        needToScrollToTop = true
    }

    /**
     * 重试请求
     */
    fun retry() {
        pagingFactroy.pixabayDataSource.value?.retry?.invoke()
    }

}

