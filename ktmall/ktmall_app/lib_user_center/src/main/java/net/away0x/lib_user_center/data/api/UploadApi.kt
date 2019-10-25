package net.away0x.lib_user_center.data.api

import io.reactivex.Observable
import net.away0x.lib_base.data.protocol.BaseResp
import retrofit2.http.POST

interface UploadApi {

    /* 获取七牛云上传凭证 */
    @POST("common/getUploadToken")
    fun getUploadToken(): Observable<BaseResp<String>>

}