package net.away0x.lib_user_center.service.impl

import io.reactivex.Observable
import net.away0x.lib_base.rx.baseFunc
import net.away0x.lib_user_center.data.repository.UploadRepository
import net.away0x.lib_user_center.service.UploadService
import javax.inject.Inject

/* 上传业务实现类 */
class UploadServiceImpl @Inject constructor(): UploadService {

    @Inject
    lateinit var repository: UploadRepository

    override fun getUploadToken(): Observable<String> {
        return repository.getUploadToken()
            .flatMap { baseFunc(it) }
    }

}