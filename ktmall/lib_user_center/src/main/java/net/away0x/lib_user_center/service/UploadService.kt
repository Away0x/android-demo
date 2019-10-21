package net.away0x.lib_user_center.service

import io.reactivex.Observable

/* 上传业务接口 */
interface UploadService {

    fun getUploadToken(): Observable<String>

}